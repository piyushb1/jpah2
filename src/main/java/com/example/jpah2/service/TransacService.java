package com.example.jpah2.service;

import com.example.jpah2.controller.UserNotFoundException;
import com.example.jpah2.entity.Transaction;
import com.example.jpah2.entity.User;
import com.example.jpah2.repo.TransacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransacService {

    @Autowired
    private TransacRepository repo;

    public List<Transaction> listall(){
        List<Transaction> users = (List<Transaction>) repo.findTop50ByOrderByBilldateDesc();
        return users;
    }

	public List<Transaction> getAllTransactions() {
		return repo.findAll();
	}

	public List<Transaction> searchTransactions(String query) {
		if (query == null) {
			return repo.findTop50ByOrderByBilldateDesc();
		}
		List<Transaction> byNameList = repo.findByDescrContainingIgnoreCaseOrderByBilldate(query);

		return byNameList;
	}

	public List<Transaction> searchTransactionsWithDate(String query, LocalDate startDate, LocalDate endDate) {

		Date startDateConverted = null;
		Date endDateConverted = null;

		if (startDate != null && endDate != null) {
			startDateConverted = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			endDateConverted = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
		}
		if (startDate != null && endDate != null && query != null) {
			return repo.findByBilldateBetweenAndDescrContainingOrderByBilldate(startDateConverted,
					endDateConverted, query);
		} else if (startDate != null && endDate != null) {
			return repo.findByBilldateBetween(startDate, endDate);
		} else {
			return searchTransactions(query);
		}
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
