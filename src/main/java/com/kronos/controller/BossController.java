package com.kronos.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kronos.model.Accord;
import com.kronos.model.TempUser;
import com.kronos.pushNotification.FcmClient;
import com.kronos.service.AccordService;
import com.kronos.service.NotificationService;
import com.kronos.service.StateService;
import com.kronos.service.UserRoleService;
import com.kronos.service.UserService;

@Controller
@RequestMapping(value = "/boss")
public class BossController {

	@Autowired
	private UserService usersRepo;
	
	@Autowired
	private StateService statesRepo;
	
	@Autowired
	private AccordService accRepo;
	
	@Autowired
	private UserRoleService usersRolesRepo;
	
	@Autowired
	private NotificationService notiRepo;
	
	@Autowired
	private FcmClient pushNotification;
	
	
	@GetMapping("/home")
	public String goToHome(Model model,
	@SessionAttribute("username") String username) {
		
		try {
		model.addAttribute("listAccords", this.accRepo.getBossAccords(username));
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return "boss/bossList";
	}
	
	@GetMapping("/edit/{accNumber}")
	public String goToEdit(Model model,@PathVariable("accNumber") String accNumber,
			@SessionAttribute("username") String username,
			@SessionAttribute("userDepartment") Integer dep) {

		try {
			Optional<Accord> opt = this.accRepo.getAccord(accNumber);
			if (!opt.isPresent())
				throw new Exception("No se encontro el acuerdo");

			Accord acc = opt.get();
			model.addAttribute("accord", acc);
			model.addAttribute("users", this.usersRolesRepo.getBossUserRolesByDepartment(username, dep));
			return "boss/bossView";
			
		
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return "boss/viewBoss";
	}
	
	@PostMapping("edit/saveEdit")
	public String saveEdit(Accord acc, 
	@SessionAttribute("username") String username,
	@RequestParam(value = "jsonObject", required = true)Optional<String> usersJson,
	RedirectAttributes attributes) {
		
		try {
			if(usersJson.isPresent()) {
				String json=usersJson.get();
				JSONArray jsonArr = new JSONArray(json);
				List<TempUser> users=new ArrayList<>();
				for(int i=0; i<jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					TempUser tmp=new TempUser();
					tmp.setName(jsonObj.getString("Nombre"));
					tmp.setEmail(jsonObj.getString("Correo Electrónico"));
					users.add(tmp);
				}
				String body="Se le notifica que se le ha asignado como responsable del "
						+ "acuerdo "+acc.getAccNumber();
				for(TempUser temp: users) {
					this.notiRepo.insertNotification(acc, temp.getEmail());
					this.pushNotification.send(temp.getEmail(),
							"Responsable del acuerdo "+acc.getAccNumber(), 
							body);
				}
				
				attributes.addFlashAttribute("msg", "Responsables Asignados con éxito");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("msgError", "Error al asignar responsables");
		}
		
		return "redirect:/boss/home";
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	
	@ModelAttribute
	public void setGenericos(Model model) {
	
		model.addAttribute("states", this.statesRepo.findAll());
	}
}
