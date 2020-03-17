package com.kronos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.cloud.storage.Acl.User;
import com.kronos.model.Accord;
import com.kronos.model.DepUserDTO;
import com.kronos.pushNotification.FcmClient;
import com.kronos.service.AccordService;
import com.kronos.service.NotificationService;
import com.kronos.service.TempUserService;
import com.kronos.service.UserService;

//is a controller works with the Town Hall secretariat
@Controller
@RequestMapping(value="/townHall")
public class TownHallController {
	
	@Autowired
	private AccordService accRepo;
	@Autowired
	private NotificationService notiRepo;
	@Autowired
	private FcmClient push;
	
	@GetMapping("/redirectAccord")
	public String redirectAccord(User user, Model model) {
		
		return "townHallsecretariat/redirectAccord";
	}
	
	
	@GetMapping("/homeTownHall")
	public String homeTownHall(User user, Model model) {
		
		return "townHallSecretariat/homeTownHall";
	}
	
	@GetMapping("/list")
	public String listTownHall() {
		
		return "townHallSecretariat/listSecretary";
	}
	
	@GetMapping("edit/newResponsables")
	public String editResponsables(Accord acc,  
	@RequestParam(value = "jsonObject", required = false) Optional<String> department) {
		
		try {
			
			if(department.isPresent()) {
				String json= department.get(); 
				JSONArray jsonArr = new JSONArray(json);
				String bodyInsert="Se le notifica que ha sido nombrado como resposable del"+
						" acuerdo "+acc.getAccNumber()+". Por favor darle seguimiento al acuerdo";
				
			    List<DepUserDTO> dataList = new ArrayList<DepUserDTO>();
			    for (int i = 0; i < jsonArr.length(); i++) {
			        JSONObject jsonObj = jsonArr.getJSONObject(i);
			        DepUserDTO data = new DepUserDTO();
			        data.setNombre((jsonObj.getString("Usuario")));
			        data.setDepartamento(jsonObj.getString("Departamento"));
			        dataList.add(data);
			    }
			    List<String> newUserNames=new ArrayList<>();
			    List<String> oldUsernames=this.notiRepo.getResponsablesUserName(acc.getAccNumber());
			    for(DepUserDTO data: dataList) {
			    	if(!oldUsernames.contains(data.getNombre())) 
			    		newUserNames.add(data.getNombre());
			    	else
			    		oldUsernames.remove(data.getNombre());
			    	
			    }
			    
			    for(String data: newUserNames) {
			    	this.notiRepo.insertNotification(acc, data);
			    	this.push.send(data, "Se le ha asignado el Acuerdo "+acc.getAccNumber(), bodyInsert);
			    }
			    
			    for(String data: oldUsernames) {
			    	this.notiRepo.deleteNotification(acc, data);
			    	this.push.send(data,"Ya no es responsable del Acuerdo "+acc.getAccNumber(),"Ha sido removido como responsable del Acuerdo "+acc.getAccNumber());
			    }
			    
			    
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		return "townHallSecretariat/listSecretary";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
	
		try {
		model.addAttribute("listAccords", this.accRepo.getAccordsSecretary());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	

}
