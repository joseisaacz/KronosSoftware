package com.kronos.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kronos.service.StateService;
import com.kronos.service.TypeService;
import com.kronos.service.UserService;
import com.kronos.model.State;
import com.kronos.model.Type;
import com.kronos.model.User;

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

	@Autowired
	private UserService userRepo;

	@GetMapping("/")
	public String mostrarHome() {

//		List<State> list= this.stateRepo.findAll();
//		
//		for(State state : list) {
//			System.out.println(state);
//		}

		return "index";
	}

	@GetMapping("/forbidden")
	public String forbidden() {
		return "accessDenied";
	}

	@GetMapping("/index")
	public String showIndex(Authentication auth, HttpSession session) {

		try {
			String username = auth.getName();
			Optional<User> opt = this.userRepo.getUserByEmail(username);
			if (opt.isPresent()) {
				User user = opt.get();
				user.setPassword(null);
				if (session.getAttribute("usuario") == null)
					session.setAttribute("user", user);

				String role = "";
				for (GrantedAuthority rol : auth.getAuthorities()) {
					role = rol.getAuthority();
				}
				session.setAttribute("roleName", role);
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
}
