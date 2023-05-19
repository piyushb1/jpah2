package com.example.jpah2.repo;

import com.example.jpah2.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    public long countById(Integer id);
}
