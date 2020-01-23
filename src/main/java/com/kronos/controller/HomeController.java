package com.kronos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kronos.service.StateService;
import com.kronos.service.TypeService;
import com.kronos.model.State;
import com.kronos.model.Type;

@Controller
public class HomeController {
//
//	 @Autowired
//	    JdbcTemplate jdbcTemplate;
//	 
	 @Autowired
	 private TypeService typeRepo;
	 
	 @Autowired
	 private StateService stateRepo;
	 
	
	@GetMapping("/")
	public String mostrarHome() {
		
//		List<State> list= this.stateRepo.findAll();
//		
//		for(State state : list) {
//			System.out.println(state);
//		}

		
		
		
		
		return "index";
	}
	
}
