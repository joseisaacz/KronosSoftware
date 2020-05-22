package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kronos.model.Act;
import com.kronos.service.ActService;

@Controller
@RequestMapping("/MunicipalidadSanPablo")
public class PublicSectionController {
	
	
	@GetMapping("/ConcejoMunicipal")
	public String searchDocuments() {
		
		return "/public/searchDocuments";
		
	}
	
	
	

}
