package com.example.jpah2.controller;

import com.example.jpah2.entity.Transaction;
import com.example.jpah2.excel.TransactionExporter;
import com.example.jpah2.service.TransacService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TransacController {

    @Autowired
    TransacService transacService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/records")
    public String showUsers(Model model){
        List<Transaction> list = transacService.listall();
        model.addAttribute("transactions",list);
        System.out.println(list);
        return "records";
    }

    @PostMapping("/records/filter")
	public String showTransactions(@RequestParam(name = "query", required = false, defaultValue = "") String query,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			Model model) {
		List<Transaction> transactions;

		if (query.isEmpty() && startDate == null && endDate == null) {
			// If no filters are provided, get all transactions
			transactions = transacService.getAllTransactions();
		} else if (startDate == null && endDate == null) {
			transactions = transacService.searchTransactions(query);
		} else {
			// Use the provided filters to search for transactions
			transactions = transacService.searchTransactionsWithDate(query, startDate, endDate);
		}

	    // Add the list of transaction IDs to the model
//	    List<Long> transactionIds = transactions.stream()
//	            .map(Transaction::getId)
//	            .collect(Collectors.toList());
//	    model.addAttribute("transactionIds", transactionIds);

		model.addAttribute("transactions", transactions);
	    model.addAttribute("query", query);
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);
		return "records";
	}

    @GetMapping("/records/new")
    public String newuser(Model model){
        model.addAttribute("user", new Transaction());
        return "records_form";
    }

    @PostMapping("/records/save")
    public String saveUser(Transaction user, RedirectAttributes ra){
        transacService.save(user);
        ra.addFlashAttribute("message","Record added");
        return "redirect:/records";
    }

    @GetMapping("/records/edit/{id}")
    public String showEditform(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try{
            Transaction user = transacService.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User" + id);
            return "records_form";
        }catch (UserNotFoundException e){
            ra.addFlashAttribute("message","User added");
            return "redirect:/records";
        }
    }

    @GetMapping("/records/delete/{id}")
    public String deleteuser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try{
            transacService.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        }catch (UserNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/records";
    }

    @GetMapping("/records/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=records_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Transaction> listUsers = transacService.listall();

        TransactionExporter excelExporter = new TransactionExporter(listUsers);

        excelExporter.export(response);
    }
    
    @GetMapping("/records/export/")
    public void exportSearchToExcel(@RequestParam(name = "query", required = false, defaultValue = "") String query,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=records_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Transaction> transactions;
        if (query.isEmpty() && startDate == null && endDate == null) {
			// If no filters are provided, get all transactions
			transactions = transacService.getAllTransactions();
		} else if (startDate == null && endDate == null) {
			transactions = transacService.searchTransactions(query);
		} else {
			// Use the provided filters to search for transactions
			transactions = transacService.searchTransactionsWithDate(query, startDate, endDate);
		}

        TransactionExporter excelExporter = new TransactionExporter(transactions);

        excelExporter.export(response);
    }
}
