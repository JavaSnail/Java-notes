package com.xxl.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	static Configuration cfg=null;
	static SessionFactory session=null;
	//静态代码块
	static {
		cfg=new Configuration();
		cfg.configure();
		session=cfg.buildSessionFactory();
	}
	//提供返回sessionFactory对象的方法。
	public static SessionFactory getSessionFactory() {
		return session;
	}
}
