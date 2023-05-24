package com.example.jpah2.repo;

import com.example.jpah2.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    public long countById(Integer id);
    List<User> findByCreationDateBetween(Date startDate, Date endDate);
    List<User> findByCreationDateBetweenAndFirstNameContaining(Date startDate, Date endDate,String keyword);
}
