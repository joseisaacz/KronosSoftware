package com.kronos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kronos.service.DeparmentService;
import com.kronos.service.RoleService;
import com.kronos.service.TempUserService;
import com.kronos.service.UserService;
import com.kronos.model.TempUser;
import com.kronos.model.User;
import com.kronos.model.Department;
import com.kronos.model.Role;

@Controller
@RequestMapping(value= "/administration")
public class AdministrationController {
	
	@Autowired
	private TempUserService tempUserRepo;
	@Autowired 
	private UserService user;
	@Autowired
	private DeparmentService departmentRepo;
	@Autowired 
	private RoleService roleRepo;
	/*@Autowired
	private PasswordEncoder passwordEncoder;*/
	
	@GetMapping("/addUser")
	public String addUser(User user,Role role,Model model) {
		Map<String,Object> mapa= new HashMap();
		mapa.put("user", user);
		mapa.put("role", role);
		model.addAllAttributes(mapa);
		return "administration/userForm"; 
	}
	
	@PostMapping("/saveUser")
	public String saveUser(User user, TempUser temp, Role role, 
			@RequestParam(value = "nameDeparment", required = false) String nameDepartment,
			@RequestParam(value = "nameRole", required = false) String nameRole) {
		String plainPassword=user.getPassword();
		/*String encryptPassword= passwordEncoder.encode(plainPassword);
		user.setPassword(encryptPassword);*/
		return "administration/userForm";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("departments", this.departmentRepo.findAll());
		model.addAttribute("roles", this.roleRepo.findAll());
	}
}
