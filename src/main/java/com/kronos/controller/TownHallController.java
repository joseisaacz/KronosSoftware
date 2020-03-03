package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.cloud.storage.Acl.User;
import com.kronos.service.AccordService;
import com.kronos.service.TempUserService;
import com.kronos.service.UserService;

//is a controller works with the Town Hall secretariat
@Controller
@RequestMapping(value="/townHall")
public class TownHallController {
	
	@Autowired
	private AccordService accRepo;
	
	@GetMapping("/redirectAccord")
	public String redirectAccord(User user, Model model) {
		
		return "townHallsecretariat/redirectAccord";
	}
	
	
	@GetMapping("/homeTownHall")
	public String homeTownHall(User user, Model model) {
		
		return "townHallSecretariat/homeTownHall";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
	
		try {
		model.addAttribute("listAccords", this.accRepo.getAccordsSecretary());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	

}
