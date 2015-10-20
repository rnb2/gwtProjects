/**
 * 
 */
package com.rnb2.gwt1.server;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.rnb2.gwt1.server.utils.Constants;

/**
 * @author budukh-rn
 * 
 */
public class HibernateUtil {


	private static SessionFactory sessionFactoryPM;
	private static SessionFactory sessionFactoryIDS;
	private static SessionFactory sessionFactoryIDS_test;
	
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
	    //serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()). buildServiceRegistry();
	    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
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
		//serviceRegistry = new ServiceRegistryBuilder().applySettings(
		//		configuration.getProperties()). buildServiceRegistry();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
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
		//serviceRegistry = new ServiceRegistryBuilder().applySettings(
		//		configuration.getProperties()). buildServiceRegistry();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
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
	    //serviceRegistry = new ServiceRegistryBuilder().applySettings(
	    //        configuration.getProperties()). buildServiceRegistry();
	    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	    sessionFactoryPM_JBoss5 = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactoryPM_JBoss5;
	}
	
	public static SessionFactory getSessionFactoryIDS() {
		return createSessionFactoryIDS();
	}

	/**
	 * 29.09.2015
	 * @return
	 */
	public static SessionFactory createSessionFactoryIDS_test() {
	    Configuration configuration = new Configuration();
	    configuration.configure(Constants.configNameIDS_test);
	   // serviceRegistry = new ServiceRegistryBuilder().applySettings(
	   //         configuration.getProperties()). buildServiceRegistry();
	    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	    sessionFactoryIDS_test = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactoryIDS_test;
	}

}
