package com.xxl.hibernatetest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.xxl.entity.User;
import com.xxl.utils.HibernateUtils;

public class HibernateDemo {

	@Test
	public void testAdd() {
//		1. 第一步：加载核心配置文件
		//在src下找到名称为hibernate.cfg.xml
		//在hibernate里面封装对象
		/*Configuration cfg=new Configuration();
		cfg.configure();
		*/
//		2. 第二步：创建SessionFactory对象
		//读取hibernate核心配置，创建sessionfactory
		//该过程根据配置，在数据库中创建表。
//		SessionFactory sessionFactory = cfg.buildSessionFactory();
		SessionFactory sessionFactory=HibernateUtils.getSessionFactory();
//		3. 第三步：使用SessionFactory创建session对象
		Session session = sessionFactory.openSession();
//		4. 第四步：开启事务
		Transaction beginTransaction = session.beginTransaction();
//		5. 第五步：写具体逻辑crud
		//添加功能
		User user=new User();
		user.setUsername("杨旭");
		user.setPassword("123");
		user.setAddress("甘肃天水");
		//调用session方法添加
		session.save(user);
		
//		6. 第六步：提交事务
		beginTransaction.commit();
//		beginTransaction.rollback();
//		7. 第七步：关闭资源
		session.close();
		sessionFactory.close();
		
	}
}
