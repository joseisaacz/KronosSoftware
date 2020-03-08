package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.google.cloud.storage.Acl.User;
import com.kronos.service.ActService;

//is a controller works with the Act's CRUD

@Controller
@RequestMapping(value="/act")
public class ActController {
	
	
	@GetMapping("/addAct")
	public String addAct( User user, Model model) {

		//model.addAttribute("act", act);
		return "/act/actForm";
	}
	
	
	@GetMapping("/listAct")
	public String listAct() {
		return "/act/listAct";
		
	}
	

}
