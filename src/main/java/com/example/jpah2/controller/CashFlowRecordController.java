package com.example.jpah2.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jpah2.entity.CashFlowRecord;
import com.example.jpah2.service.CashFlowRecordService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CashFlowRecordController {

    private final CashFlowRecordService cashFlowRecordService;

    public CashFlowRecordController(CashFlowRecordService cashFlowRecordService) {
        this.cashFlowRecordService = cashFlowRecordService;
    }

    @GetMapping("/cash-flow-records")
    public String showCashFlowRecords(Model model) {
        List<CashFlowRecord> cashFlowRecords = cashFlowRecordService.getAllCashFlowRecords();
        model.addAttribute("cashFlowRecords", cashFlowRecords);
        model.addAttribute("cashFlowRecord", new CashFlowRecord()); // For form submission
        return "cashFlowRecords";
    }

    @PostMapping("/cash-flow-records")
    public String saveCashFlowRecord(@ModelAttribute CashFlowRecord cashFlowRecord,
                                     @RequestParam("transactionDate")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate) {
        cashFlowRecord.setTransactionDate(transactionDate);
        cashFlowRecordService.saveCashFlowRecord(cashFlowRecord);
        return "redirect:/cash-flow-records";
    }
}
