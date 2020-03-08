package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kronos.model.Act;
import com.kronos.service.ActService;

//is a controller works with the Act's CRUD

@Controller
@RequestMapping(value="/act")
public class ActController {
	
	@Autowired
	private ActService actServiceRepo;
	
	@GetMapping("/addAct")
	public String addAct( Act act, Model model) {
		model.addAttribute("act", act);
		return "/actForm";
	}
	
	@PostMapping("/saveAct")
	public String saveAct(Act act, @RequestParam("act") MultipartFile[] uploadingFiles) {
		
		return "redirect:/accords/list";
	}
	
	
	@GetMapping("/listAct")
	public String listAct() {
		return "/act/listAct";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("sessions", actServiceRepo.findSessionDates());
	}
	

}
