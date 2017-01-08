package com.leszczynski;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.leszczynski.entity.TestEntity;

import junit.framework.TestCase;

public class testEntityTest extends TestCase {

	public void testApp(){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		TestEntity test = new TestEntity("cos");
		session.save(test);
		session.getTransaction().commit();
		session.close();
	}
}
