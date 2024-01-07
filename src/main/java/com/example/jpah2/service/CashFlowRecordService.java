package com.example.jpah2.service;

import org.springframework.stereotype.Service;

import com.example.jpah2.entity.CashFlowRecord;
import com.example.jpah2.repo.CashFlowRecordRepository;

import java.util.List;

@Service
public class CashFlowRecordService {

    private final CashFlowRecordRepository cashFlowRecordRepository;

    public CashFlowRecordService(CashFlowRecordRepository cashFlowRecordRepository) {
        this.cashFlowRecordRepository = cashFlowRecordRepository;
    }

    public List<CashFlowRecord> getAllCashFlowRecords() {
        return cashFlowRecordRepository.findTop50ByOrderByTransactionDateDesc();
    }

    public void saveCashFlowRecord(CashFlowRecord cashFlowRecord) {
        cashFlowRecordRepository.save(cashFlowRecord);
    }

    // You can add more methods based on your business logic
}
