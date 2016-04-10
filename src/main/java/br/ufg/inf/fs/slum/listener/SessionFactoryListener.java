/**
 * 
 */
package br.ufg.inf.fs.slum.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.ufg.inf.fs.slum.util.HibernateUtil;

/**
 * @author Ademar
 *
 */
public class SessionFactoryListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateUtil.shutdown();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.getSessionFactory();
	}

}
