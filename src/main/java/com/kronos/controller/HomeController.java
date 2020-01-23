package com.kronos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kronos.repository.TypeRepository;
import com.kronos.service.TypeService;
import com.kronos.model.Type;

@Controller
public class HomeController {
//
//	 @Autowired
//	    JdbcTemplate jdbcTemplate;
//	 
	 @Autowired
	 private TypeService typeRepo;
	
	@GetMapping("/")
	public String mostrarHome() {
		
		List<Type> list= this.typeRepo.findAll();
		
		for(Type type : list) {
			System.out.println(type);
		}

		
		
		
		
		return "index";
	}
	
}
