package com.example.jpah2;

import com.example.jpah2.entity.User;
import com.example.jpah2.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired private UserRepository repo;

//    @Test
//    public void testNew(){
//        User user = new User("varu12n@gmail.com","varunbhau","varun","dude");
//
//        User newuser = repo.save(user);
//        Assertions.assertNotNull(newuser);
//        Assertions.assertTrue(newuser.getId()>0);
//    }

    @Test
    public void testAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertNotNull(users);
        for (User user : users) {
            System.out.println(user);
        }
    }


}
