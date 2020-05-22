package com.kronos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.kronos.model.Accord;
import com.kronos.model.Act;
import com.kronos.service.AccordService;
import com.kronos.service.ActService;

@RestController
@RequestMapping("/public")
public class PublicRestController {
	
	@Value("${kronos.path.folder}")
	private String uploadFolder;
	
	@Autowired
	private ActService actRepo;
	
	@Autowired
	private AccordService accRepo;
	
	@GetMapping("/allAccordsPublish")
	public List<Accord> searchAllAccordPublish(){
		try {
			return this.accRepo.searchAllAccordsPublish();
			
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
	}
	
	@GetMapping("/searchAccordsSessionDateP/{date}")
	public List<Accord> searchAccordsSessionDateP(@PathVariable("date")String date){
		
		try {
			// System.out.println(date);
			DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
			Date dateDb = dateformat.parse(date);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date2 = sdf.format(dateDb);
			Date db2 = sdf.parse(date2);
			return this.accRepo.searchAccordsessionDatePublish(db2);

		}

		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
		
	}
	
	@GetMapping("/AllActsPublish")
	public List<Act> searchAllActsPublish(){
		try {
			return this.actRepo.searchAllActsPublish();
			
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
		
	}
	
	@GetMapping("/ActsPublishYear/{year}")
	public List<Act> searchAllActsPublishByYear(@PathVariable("year")String year){
		try {
			// System.out.println(date);
			int value = Integer.parseInt(year);
			return this.actRepo.searchAllActsPublishYear(value);
		}

		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
		
	}
	

	
	
	

}
