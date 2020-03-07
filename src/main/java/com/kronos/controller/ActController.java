package com.kronos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.google.cloud.storage.Acl.User;

//is a controller works with the Act's CRUD

@Controller
@RequestMapping(value="/act")

public class ActController {
	
	
	
	@GetMapping("/addAct")
	public String createAct( User user, Model model) {

		//model.addAttribute("act", act);
		return "act/actForm";
	}
	
	

}
