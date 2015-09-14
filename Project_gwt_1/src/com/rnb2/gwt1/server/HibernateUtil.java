/**
 * 
 */
package com.rnb2.gwt1.server;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.rnb2.gwt1.server.utils.Constants;

/**
 * @author budukh-rn
 * 
 */
public class HibernateUtil {


	private static SessionFactory sessionFactoryPM;
	private static SessionFactory sessionFactoryIDS;
	
	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactoryPM_JBoss01;
	private static SessionFactory sessionFactoryPM_JBoss5;
	

	/**
	 * 11.09.2015
	 * @return
	 */
	public static SessionFactory createSessionFactoryIDS() {
	    Configuration configuration = new Configuration();
	    configuration.configure(Constants.configNameIDS);
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). buildServiceRegistry();
	    sessionFactoryIDS = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactoryIDS;
	}
	
	/**
	 * 11.09.2015
	 * @return
	 */
	public static SessionFactory createSessionFactoryJboss() {
		Configuration configuration = new Configuration();
		configuration.configure(Constants.configNamePM);
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				configuration.getProperties()). buildServiceRegistry();
		sessionFactoryPM = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactoryPM;
	}
	
	/**
	 * 11.09.2015
	 * @return
	 */
	public static SessionFactory createSessionFactoryJboss01() {
		Configuration configuration = new Configuration();
		configuration.configure(Constants.configNamePM_01);
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				configuration.getProperties()). buildServiceRegistry();
		sessionFactoryPM_JBoss01 = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactoryPM_JBoss01;
	}
	
	/**
	 * 11.09.2015
	 * @return
	 */
	public static SessionFactory createSessionFactoryJboss5() {
	    Configuration configuration = new Configuration();
	    configuration.configure(Constants.configNamePM_5);
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). buildServiceRegistry();
	    sessionFactoryPM_JBoss5 = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactoryPM_JBoss5;
	}
	
//	static {
//		try {
//			sessionFactoryPM = new Configuration().configure(
//					Constants.configNamePM).buildSessionFactory();
//			System.out.println("sessionFactoryPM: " + sessionFactoryPM.hashCode());
//		} catch (Throwable ex) {
//			System.err.println("Initial sessionFactoryPM creation failed." + ex);
//			throw new ExceptionInInitializerError(ex);
//		}
//		try {
//			sessionFactoryIDS = new Configuration().configure(
//					Constants.configNameIDS).buildSessionFactory();
//			System.out.println("sessionFactoryIDS: " + sessionFactoryIDS.hashCode());
//		} catch (Throwable ex) {
//			System.err.println("Initial sessionFactoryIDS creation failed." + ex);
//			throw new ExceptionInInitializerError(ex);
//		}
//	}


	@Deprecated
	public static SessionFactory getSessionFactoryPM() {
		return createSessionFactoryJboss();
	}
	public static SessionFactory getSessionFactoryIDS() {
		return createSessionFactoryIDS();
	}

}
