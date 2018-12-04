package com.leszczynski.dao;

import com.leszczynski.converter.UserConverter;
import com.leszczynski.dto.User;
import com.leszczynski.entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDao {

    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public User saveUser(User user){
        UserEntity userEntity = UserConverter.convert(user);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Long id = (Long) session.save(userEntity);
        session.getTransaction().commit();
        session.close();
        user.setId(id);
        return user;
    }

    public void updateUser(User user) {
        UserEntity userEntity = UserConverter.convert(user);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
        session.close();
    }

    public User getUser(Long id) {
        UserEntity userEntity;
        Session session = sessionFactory.openSession();
        userEntity = (UserEntity) session.get(UserEntity.class, id);
        session.close();
        if (userEntity != null){
            User user = UserConverter.convert(userEntity);
            return user;
        }
        return null;
    }

    public List<String> getAllNicks() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "select login FROM users";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;

    }

    public List<String> getAllMails() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "select email FROM users";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }
}
