package com.leszczynski.converter;

import com.leszczynski.dto.User;
import com.leszczynski.entity.UserEntity;

/**
 * Created by Krystian L on 2018-07-15.
 */
public class UserConverter {
    public static User convert (UserEntity userEntity){
        User user = new User();
        user.setLogin(userEntity.getLogin());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setName(userEntity.getName());
        user.setSurname(userEntity.getSurname());
        user.setId(userEntity.getId());
        user.setActive(userEntity.isActive());
        user.setIsAdmin(userEntity.getIsAdmin());
        user.setActivationLink(userEntity.getActivationLink());
        return user;
    }

    public static UserEntity convert (User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(user.getLogin());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setId(user.getId());
        userEntity.setActive(user.isActive());
        userEntity.setIsAdmin(user.getIsAdmin());
        userEntity.setActivationLink(user.getActivationLink());

        return userEntity;
    }
}
