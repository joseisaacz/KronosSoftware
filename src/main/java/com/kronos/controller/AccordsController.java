package com.kronos.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kronos.model.Accord;
import com.kronos.model.Pdf;
import com.kronos.model.TempUser;
import com.kronos.service.AccordService;
import com.kronos.service.ActService;
import com.kronos.service.StateService;
import com.kronos.service.TempUserService;
import com.kronos.service.TypeService;

@Controller
@RequestMapping(value = "/accords")
public class AccordsController {

	private final String uploadFolder="/home/jonathan/uploads/";
	@Autowired
	private TypeService typesRepo;
	
	@Autowired
	private StateService statesRepo;
	
	@Autowired
	private ActService actRepo;
	
	@Autowired
	private TempUserService tempUserRepo;
	
	@Autowired
	private AccordService accordRepo;
	
	
	
	@PostMapping("/saveAccord")
	public String saveAccord(Accord accord, @RequestParam(value="username",required=false) String username,
			
	@RequestParam(value="email",required=false) String email,
	
	@RequestParam("accord") MultipartFile[] uploadingFiles	) {
		
		try {
	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			
				accord.setIncorporatedDate(new Date());
				accord.setIncorporatedTime(LocalTime.now());
				
				
			if(!this.actRepo.isActInDB(accord.getSessionDate())) 
				this.actRepo.insertAct(accord.getSessionDate());
			
			TempUser tmp=null;
			if(accord.getType().getId() != Accord.ADMIN_TYPE) {
				
				if(email.isEmpty() || username.isEmpty())
					throw new Exception();
				
				Optional<TempUser> opt = this.tempUserRepo.findByEmail(email);
				if(opt.isPresent()) 
					tmp=opt.get();
				
				else {
					tmp=new TempUser(username,email);
					this.tempUserRepo.insertTempUser(tmp);
				}
				
				
				
				
			}
			
			
			
		
		for(MultipartFile uploadFile : uploadingFiles) {
			String url= uploadFolder+uploadFile.getOriginalFilename();
			File file= new File(url);
			uploadFile.transferTo(file);
			accord.getURL().add(new Pdf(url));
			
		}
			
			this.accordRepo.insertAccord(accord);
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
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
