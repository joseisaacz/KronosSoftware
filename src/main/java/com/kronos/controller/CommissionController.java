package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kronos.service.TypeService;
import com.kronos.model.Type;

@Controller
@RequestMapping(value = "/commission")
public class CommissionController {

	@Autowired
	private TypeService typeServiceRepo;

	@GetMapping("/addCommission")
	public String addCommision(Type type, Model model) {
		model.addAttribute("type", type);
		return "commission/commissionForm";
	}

	@PostMapping("/saveCommission")
	public String saveAct(Type type) {
		if(type.getDescription().isEmpty()) {return "commission/commissionForm";}
		try {
			String tp = type.getDescription();
			Type nt = new Type();
			nt.setDescription(tp);
			typeServiceRepo.addType(nt);	
		} catch (Exception e) {
			return "commission/addCommission";
		}
		return "redirect:/accords/list";
	}

}
