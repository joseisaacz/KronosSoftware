package com.kronos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kronos.model.Department;
import com.kronos.model.User;
import com.kronos.service.TempUserService;
import com.kronos.service.UserService;

@RestController
@RequestMapping("/api/administration")
public class AdministrationRestController {
	
	@Autowired
	private UserService usersRepo;
	
	@Autowired
	private TempUserService tempUserRepo;
	
	
	@GetMapping("/allUsers")
	public List<User> getAllUsers(){
		try {
			return this.usersRepo.getAllUsers();
		}
		
		catch(Exception e) {
			
			throw new ResponseStatusException(HttpStatus.valueOf(500), 
					"Server Error");
		}
		
	}
	
	@GetMapping("/userByEmail/{email}")
	public List<User> getUserByEmail(@PathVariable("email") String email){
		try {
			return this.usersRepo.getUsersByEmail(email);
		}
		
		catch(Exception e) {
			
			throw new ResponseStatusException(HttpStatus.valueOf(500),
					"Server Error");
		}
		
	}
	
	@GetMapping("/usersByStatus/{status}")
	public List<User> getUsersByStatus(@PathVariable("status") boolean status){
		try {
			return this.usersRepo.getUsersStatus(status);
		}
		
		catch(Exception e) {
			
			throw new ResponseStatusException(HttpStatus.valueOf(500), 
					"Server Error");
		}
		
		
	}
	
	@GetMapping("/usersByDepartment/{department}")
	public List<User> getUsersByDepartment(@PathVariable("department") int department){
		try {
			Department dep= new Department(department);
			return this.usersRepo.getUsersByDepartment(dep);
		} 
		catch(Exception e) {
			
			throw new ResponseStatusException(HttpStatus.valueOf(500), 
					"Server Error");
			
		}
		
	}
	
	@PutMapping("/updateName")
	public ResponseEntity updateName(@RequestBody User user) {
		
		try {
			System.out.println(user);
			this.tempUserRepo.updateTempUserName(user.getTempUser().getEmail(), user.getTempUser().getName());
			return ResponseEntity.ok().build();
		}
		
		catch(Exception e){
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.valueOf(500), 
					"Server Error");
		}
	}

}
