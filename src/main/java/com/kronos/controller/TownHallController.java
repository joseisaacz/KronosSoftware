package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.cloud.storage.Acl.User;
import com.kronos.service.TempUserService;
import com.kronos.service.UserService;

//is a controller works with the Town Hall secretariat
@Controller
@RequestMapping(value="/townHall")
public class TownHallController {
	
	@Autowired
	private TempUserService temp;
	@Autowired
	private UserService user;
	
	@GetMapping("/redirectAccord")
	public String redirectAccord(User user, Model model) {
		
		return "townHallsecretariat/redirectAccord";
	}

	
	

}
