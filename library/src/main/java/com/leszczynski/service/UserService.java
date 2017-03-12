package com.leszczynski.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.leszczynski.entity.BookEntity;
import com.leszczynski.entity.TestEntity;
import com.leszczynski.entity.UserEntity;

public class UserService {

	public void testApp() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		UserEntity usr = new UserEntity("name");
		TestEntity test = new TestEntity("cos");
		BookEntity book = new BookEntity("hahaha");
		session.save(test);
		session.save(usr);
		session.getTransaction().commit();
		session.close();
	}
}
