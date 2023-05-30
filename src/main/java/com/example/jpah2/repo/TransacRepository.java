package com.example.jpah2.repo;

import com.example.jpah2.entity.Transaction;
import com.example.jpah2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransacRepository extends JpaRepository<Transaction,Integer> {
    public long countById(Integer id);
    List<Transaction> findByBilldateBetweenAndDescrContaining(Date startDate, Date endDate,String keyword);
}
