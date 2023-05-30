package com.example.jpah2.service;

import com.example.jpah2.controller.UserNotFoundException;
import com.example.jpah2.entity.Transaction;
import com.example.jpah2.entity.User;
import com.example.jpah2.repo.TransacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransacService {

    @Autowired
    TransacRepository repo;

    public List<Transaction> listall(){
        List<Transaction> users = (List<Transaction>) repo.findAll();
        return users;
    }

    public List<Transaction> filterList(Date startDate, Date endDate, String keyword){
        List<Transaction> users = (List<Transaction>) repo.findByBilldateBetweenAndDescrContaining(startDate, endDate, keyword);
        return users;
    }

    public void save(Transaction user) {
        repo.save(user);
    }

    public Transaction get(Integer id) throws UserNotFoundException {
        Optional<Transaction> user = repo.findById(id);
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
