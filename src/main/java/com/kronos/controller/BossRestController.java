package com.kronos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.kronos.model.Accord;
import com.kronos.service.AccordService;

@RestController
@RequestMapping("/api/boss")
public class BossRestController {
	/*
	 * Rest Controller
	 * Some actions are easier with restful services
	 * And get better user experience avoiding the server to write the html code 
	 * */
	
	@Autowired
	private AccordService accRepo;
	
	@GetMapping("/allAccords")
	public List<Accord> getAllAccords(@SessionAttribute("username") String username){
		try {
			return this.accRepo.getAllBossAccords(username);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.valueOf(500), "Server Error");
		}
	}
	
	
	
	@GetMapping("/inTransit")
	public List<Accord> getInTransitAccords(@SessionAttribute("username") String username){
		try {
			return this.accRepo.getBossAccords(username);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.valueOf(500), "Server Error");
		}
	}
	
	
	
	@GetMapping("/finished")
	public List<Accord> getFinishedAccords(@SessionAttribute("username") String username){
		try {
			return this.accRepo.getFinishedBossAccords(username);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.valueOf(500), "Server Error");
		}
	}
}
