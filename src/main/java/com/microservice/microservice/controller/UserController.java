package com.microservice.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.microservice.microservice.dao.UserDao;

import com.microservice.microservice.model.AuthResponse;
import com.microservice.microservice.model.UserOutput;
import com.microservice.microservice.service.UserService;
import com.microservice.microservice.service.serviceImpl.AuthServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class UserController {
    
    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @GetMapping("/userlogin")
    public String userloginPage() {
        return "loginUser";
    }

    //USER LOGIN CONTROLLER
    @PostMapping("/loginUser")
    public String userlogin(@RequestParam String username, @RequestParam String password, Model model) {
        UserOutput user = userDao.getByUsername(username);
        AuthResponse response = authService.authUser(username, password);

        if (response.isSuccess() && user != null) {
            // Successful login
            model.addAttribute("success", "Login Successfully");
            model.addAttribute("user", userService.getUserById(user.getEmp_id()));
            return "redirect:/user";

        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password");
            return "loginUser";
        }
    }

    
    @GetMapping("/projlogin")
    public String projectloginPage() {
        return "loginProj";
    }
    
    //PROJECT LOGIN CONTROLLER
    @PostMapping("/loginProj")
    public String projlogin(@RequestParam String username, @RequestParam String password, Model model) {
        UserOutput user = userDao.getByUsername(username);
        AuthResponse response = authService.authUser(username, password);

        if (response.isSuccess() && user != null) {
            // Successful login
            model.addAttribute("success", "Login Successfully");
            return "redirect:/project";

        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password");
            return "loginProj";
        }
    }


    // Logout remove httpsession
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}