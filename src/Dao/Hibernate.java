package Dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class Hibernate {
	 private static SessionFactory sessionFactory = buildSessionFactory();
	 
	 private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	        	sessionFactory = new Configuration().configure().buildSessionFactory();
				return sessionFactory;
			}
	        catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
	
}
