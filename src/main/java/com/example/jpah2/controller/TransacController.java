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
        model.addAttribute("listUsers",list);
        System.out.println(list);
        return "records";
    }

    @PostMapping("/records/filter")
    public String filterUsersByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                         @RequestParam("keyword") String keyword,
                                         Model model) {
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Transaction> list = transacService.filterList(start, end, keyword);
        model.addAttribute("listUsers",list);
        System.out.println(list);
        System.out.println(keyword+" "+start+" "+end);
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
}
