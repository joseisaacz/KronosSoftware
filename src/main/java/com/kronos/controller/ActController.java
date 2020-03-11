package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kronos.model.Act;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kronos.service.ActService;

//is a controller works with the Act's CRUD

@Controller
@RequestMapping(value="/act")
public class ActController {
	
	@Value("${kronos.path.folder}")
	private String uploadFolder;
	
	@Autowired
	private ActService actServiceRepo;
	
	@GetMapping("/addAct")
	public String addAct( Act act, Model model) {
		model.addAttribute("act", act);
		return "act/actForm";
	}
	
	@PostMapping("/saveAct")
	public String saveAct(Act act, @RequestParam("pdf") MultipartFile[] uploadingFiles) {
		act.setActive(true);
		act.setPublc(true);
		try {
		for (MultipartFile uploadFile : uploadingFiles) {
			String url = uploadFolder + uploadFile.getOriginalFilename();
			File file = new File(url);
			uploadFile.transferTo(file);
			act.setUrl(url);
			
		}
		actServiceRepo.addAct(act);
		}catch(Exception e) {
			
		}
		return "redirect:/accords/list";
	}
	
	
	@GetMapping("/listAct")
	public String listAct() {
		return "/act/listAct";
	}
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("sessions", actServiceRepo.findSessionDates());
	}
	

}
