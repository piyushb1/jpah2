package com.example.jpah2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpah2.entity.CashFlowRecord;

public interface CashFlowRecordRepository extends JpaRepository<CashFlowRecord, Long> {

	List<CashFlowRecord> findTop50ByOrderByTransactionDateDesc();
    // You can add custom query methods if needed
}
