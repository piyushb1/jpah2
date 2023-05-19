package com.example.jpah2.service;

import com.example.jpah2.controller.UserNotFoundException;
import com.example.jpah2.entity.User;
import com.example.jpah2.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public List<User> listall(){
        List<User> users = (List<User>) repo.findAll();
        return users;
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> user = repo.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("User not found");
    }

    public void delete(Integer id) throws UserNotFoundException {
        if(repo.countById(id)!=1){
            throw new UserNotFoundException("User doesnt exist");
        }else{
            repo.deleteById(id);
        }
    }
}
