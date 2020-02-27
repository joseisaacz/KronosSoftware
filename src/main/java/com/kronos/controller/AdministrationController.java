package com.kronos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import com.kronos.service.UserRoleService;
import com.kronos.service.UserService;
import com.kronos.model.TempUser;
import com.kronos.model.User;
import com.kronos.model.UserRole;
import com.kronos.model.Department;
import com.kronos.model.Role;

@Controller
@RequestMapping(value = "/administration")
public class AdministrationController {


	@Autowired
	private TempUserService tempUserRepo;
	@Autowired
	private UserService userRepo;
	@Autowired
	private DeparmentService departmentRepo;
	@Autowired
	private RoleService roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRoleService userRoleRepo;

	@GetMapping("/addUser")
	public String addUser(User user, Role role, Model model) {
		Map<String, Object> mapa = new HashMap();
		mapa.put("user", user);
		mapa.put("role", role);
		model.addAllAttributes(mapa);
		return "administration/userForm";
	}

	@PostMapping("/saveUser")
	public String saveUser(User user, Role role,
			@RequestParam(value = "nameDeparment", required = false) String nameDepartment,
			@RequestParam(value = "nameRole", required = false) String nameRole) {
		user.setStatus(true);
		String plainPassword = user.getPassword();
		String encryptPassword = passwordEncoder.encode(plainPassword);
		user.setPassword(encryptPassword);
		Department dp = new Department();
		Role rl = new Role();
		try {
			this.tempUserRepo.insertTempUser(user.getTempUser());
			if (role.getId() == -2) {
				roleRepo.insertRole(nameRole);
				rl.setId(roleRepo.searchRole(nameRole).get().getId());
				System.out.println(rl.getId());
				rl.setName(roleRepo.searchRole(nameRole).get().getName());

			}else {
				rl.setId(role.getId());
				rl.setName(role.getName());
			}
			if (user.getDepartment().getId() == -1) {
				departmentRepo.insertDepartment(nameDepartment);
				dp.setId(departmentRepo.searchDepartment(nameDepartment).get().getId());
				dp.setName(departmentRepo.searchDepartment(nameDepartment).get().getName());
				user.setDepartment(dp);
			} 
			userRepo.insertUser(user);
			userRoleRepo.insertUserRole(user, rl);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

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
