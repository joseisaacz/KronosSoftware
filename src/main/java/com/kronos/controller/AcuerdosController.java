package com.kronos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/accords")
public class AcuerdosController {

	@GetMapping("/addAccord")
	public String createAccord() {
		
		return "accord/accordForm";
	}
	
}
