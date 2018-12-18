package com.prg.store.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	public static final Configuration cfg;
	public static final SessionFactory sf;
	
	static {
		cfg = new Configuration().configure();
		sf = cfg.buildSessionFactory();
	}
	
	public static Session getSession() {
		return sf.openSession();
	}
	
	public static Session getCurrentSession() {
		return sf.getCurrentSession();
	}
}
 