package com.kronos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kronos.model.Accord;
import com.kronos.service.StateService;
import com.kronos.service.TypeService;

@Controller
@RequestMapping(value = "/accords")
public class AccordsController {

	@Autowired
	private TypeService typesRepo;
	
	@Autowired
	private StateService statesRepo;
	
	
	@PostMapping("/saveAccord")
	public String saveAccord(Accord accord) {
		
		System.out.println(accord);
		
		return "accord/accordForm";
	}
	
	
	@GetMapping("/addAccord")
	public String createAccord(Accord accord, Model model) {
		
		model.addAttribute("accord", accord);
		return "accord/accordForm";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("states", this.statesRepo.findAll());
		model.addAttribute("types", this.typesRepo.findAll());
	}
	
	
}
