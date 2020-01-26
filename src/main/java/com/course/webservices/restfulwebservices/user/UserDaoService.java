package com.course.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "Fernando", new Date()));
        users.add(new User(2, "Mayara", new Date()));
        users.add(new User(3, "Gatolino", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId()== null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for (User user : users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteUser(int id){
        User one = findOne(id);
        if(one == null){
            throw new UserNotFoundException("id: "+id);
        }
        users.remove(one);
        return one;
    }
}
