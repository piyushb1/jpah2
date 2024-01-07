package com.example.jpah2.repo;

import com.example.jpah2.entity.Transaction;
import com.example.jpah2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransacRepository extends JpaRepository<Transaction,Integer> {
    public long countById(Integer id);
    List<Transaction> findByBilldateBetweenAndDescrContainingOrderByBilldate(Date startDate, Date endDate,String keyword);
	List<Transaction> findByDescrContainingIgnoreCaseOrderByBilldate(String query);
	List<Transaction> findByBilldateBetweenAndDescrContainingIgnoreCaseOrderByBilldate(Date startDateConverted, Date endDateConverted,
			String query);
	List<Transaction> findByBilldateBetween(LocalDate startDate, LocalDate endDate);
	List<Transaction> findTop50ByOrderByBilldateDesc();
}
