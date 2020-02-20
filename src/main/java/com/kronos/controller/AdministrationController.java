package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kronos.service.TempUserService;
import com.kronos.service.UserService;
import com.kronos.model.User;

@Controller
@RequestMapping(value= "/administration")
public class AdministrationController {
	
	@Autowired
	private TempUserService tmp;
	@Autowired 
	private UserService user;
	
	@GetMapping("/addUser")
	public String addUser(User user, Model model) {
		model.addAttribute("user", user);
		return "administration/userForm"; 
	}
	
	@PostMapping("/saveUser")
	public String saveUser(User user) {
		
		return "administration/userForm";
	}
	
}
