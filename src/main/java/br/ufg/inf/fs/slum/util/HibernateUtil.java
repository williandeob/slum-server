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

import java.util.List;

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

    public static <T> List<T> getFieldEq(final Class<T> type, final String propertyName, final Object value){
        List<T> objs = null;
        try{
            startOperation();
            final Criteria crit = session.createCriteria(type);
            crit.add(Restrictions.eq(propertyName, value));
            objs = crit.list();
        }catch (HibernateException e){
            handleException(e);
        }finally {
            HibernateUtil.close(session);
        }

        return objs;
    }


	public static void shutdown() {
		getSessionFactory().close();
	}
}
