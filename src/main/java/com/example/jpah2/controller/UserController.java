package com.example.jpah2.controller;

import com.example.jpah2.entity.User;
import com.example.jpah2.excel.UserExcelExporter;
import com.example.jpah2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String showUsers(Model model){
        List<User> list = userService.listall();
        model.addAttribute("listUsers",list);
        System.out.println(list);
        return "users";
    }

    @PostMapping("/users/filter")
    public String filterUsersByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                         @RequestParam("keyword") String keyword,
                                         Model model) {
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<User> list = userService.filterList(start, end, keyword);
        model.addAttribute("listUsers",list);
        System.out.println(list);
        System.out.println(keyword+" "+start+" "+end);
        return "users";
    }

    @GetMapping("/users/new")
    public String newuser(Model model){
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra){
        userService.save(user);
        ra.addFlashAttribute("message","User added");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditform(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try{
            User user = userService.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User" + id);
            return "user_form";
        }catch (UserNotFoundException e){
            ra.addFlashAttribute("message","User added");
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteuser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try{
            userService.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        }catch (UserNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.listall();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }
}
