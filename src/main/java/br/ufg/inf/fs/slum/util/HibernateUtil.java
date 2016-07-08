/**
 * 
 */
package br.ufg.inf.fs.slum.util;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ademar
 *
 */
public class HibernateUtil {

    private static Session session;
    private static Transaction tx;

    final static Logger logger = Logger.getLogger(HibernateUtil.class);

	private static final SessionFactory sessionFactory = buildSessionFactory();

    public HibernateUtil() {
        buildIfNeeded();
    }

	private static SessionFactory buildSessionFactory() {
		try {
            logger.debug("Iniciando SessionFactory");
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
            logger.error("Erro ao criar SessionFactory " +ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

    public static SessionFactory buildIfNeeded() throws DataAccessLayerException{
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            return buildSessionFactory();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public static Session openSession() throws HibernateException {
        buildIfNeeded();
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                logger.error("Couldn't close SessionFactory", ignored);
            }
        }
    }

    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                logger.error("Couldn't close Session", ignored);
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            logger.error("Couldn't rollback Transaction", ignored);
        }
    }

    public static void saveOrUpdate(Persistivel obj) {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateUtil.close(session);
        }
    }

    public static void delete(Persistivel obj) {
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateUtil.close(session);
        }
    }

    public static Object find(Class clazz, Long id) {
        Object obj = null;
        try {
            startOperation();
            obj = session.load(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateUtil.close(session);
        }
        return obj;
    }

    public static List findAll(Class clazz) {
        List objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateUtil.close(session);
        }
        return objects;
    }

    public static void handleException(HibernateException e) throws DataAccessLayerException {
        HibernateUtil.rollback(tx);
        throw new DataAccessLayerException(e);
    }

    public static  void startOperation() throws HibernateException {
        session = HibernateUtil.openSession();
        tx = session.beginTransaction();
    }

    public static <T> List<T> getFieldEq(final Class<T> type, HashMap<String,Object> propertyNameValue){
        return getFieldEq(type, propertyNameValue, new HashMap<String,Object>());
    }

    public static <T> List<T> getFieldEq(final Class<T> type, HashMap<String,Object> propertyNameValue, HashMap<String,Object> notPropertyNameValue){
        List<T> objs = null;
        try{
            startOperation();
            final Criteria crit = session.createCriteria(type);

            for (Map.Entry<String, Object> entry : propertyNameValue.entrySet()) {
                String propertyName = entry.getKey();
                Object propertyValue = entry.getValue();

                crit.add(Restrictions.eq(propertyName, propertyValue));

            }

            for (Map.Entry<String, Object> entry : notPropertyNameValue.entrySet()) {
                String notPropertyName = entry.getKey();
                Object propertyValue = entry.getValue();

                crit.add(Restrictions.neOrIsNotNull(notPropertyName, propertyValue));

            }

            objs = crit.list();
        }catch (HibernateException e){
            handleException(e);
        }finally {
            HibernateUtil.close(session);
        }

        return objs;
    }

    public static <T> Object getFieldEqUnique(final Class<T> type, HashMap<String, Object> propertyNameValue){
        Object obj = null;
        try{
            startOperation();
            final Criteria crit = session.createCriteria(type);

            for (Map.Entry<String, Object> entry : propertyNameValue.entrySet()) {
                String propertyName = entry.getKey();
                Object propertyValue = entry.getValue();

                crit.add(Restrictions.eq(propertyName, propertyValue));
            }

            obj = crit.uniqueResult();
        }catch (HibernateException e){
            handleException(e);
        }finally {
            HibernateUtil.close(session);
        }

        return obj;
    }

	public static void shutdown() {
		getSessionFactory().close();
	}
}