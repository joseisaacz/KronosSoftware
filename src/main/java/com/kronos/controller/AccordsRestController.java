package com.kronos.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.kronos.model.Accord;
import com.kronos.model.Pdf;
import com.kronos.service.AccordService;

@RestController
@RequestMapping("/api/accords")
public class AccordsRestController {
	
	private final String uploadFolder="/home/jonathan/uploads/";
	
	@Autowired
	private AccordService accordRepo;
	
	@GetMapping("/allAccords")
	public List<Accord> searchAll(){
		try  {
			
		return this.accordRepo.searchAllAccords();
		
		 }
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	@GetMapping("/sessionDate/{date}")
	public List<Accord> searchBySessionDate(@PathVariable("date") String date){
		try  {
			//System.out.println(date);
			DateFormat dateformat= new SimpleDateFormat("dd-MM-yyyy");
			Date dateDb = dateformat.parse(date);
			DateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String date2 = sdf.format(dateDb);
			Date db2= sdf.parse(date2);
			System.out.println(db2);
			return this.accordRepo.searchBySessionDate(db2);
		
			
	
		 }
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	
	@GetMapping("/incorDate/{date}")
	public List<Accord> searchByIncorDate(@PathVariable("date") String date){
		try  {
			//System.out.println(date);
			DateFormat dateformat= new SimpleDateFormat("dd-MM-yyyy");
			Date dateDb = dateformat.parse(date);
			DateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String date2 = sdf.format(dateDb);
			Date db2= sdf.parse(date2);
			System.out.println(db2);
			return this.accordRepo.searchByIncorDate(db2);
		
			
	
		 }
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	
	@GetMapping("/type/{type}")
	public List<Accord> searchByType(@PathVariable("type") char type){
		try  {
			
	
			return this.accordRepo.searchByType(type);
		
			
	
		 }
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	@GetMapping("/accNumber/{number}")
	public List<Accord> searchByAccNumber(@PathVariable("number") String accNumber){
		try  {
			
	
			return this.accordRepo.searchByAccNumber(accNumber);
		
			
	
		 }
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	
	@GetMapping("/get/allAccords")
	public List<Accord> searchAllAccordsApi(){
		try  {
			
	
			return this.accordRepo.searchAllAccords();
		
			
	
		 }
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	@GetMapping("/get/{accNumber}")
	public Accord getAccord(@PathVariable("accNumber") String accNumber) {
		
		try {
			Optional<Accord> opt = this.accordRepo.getAccord(accNumber);
			if(!opt.isPresent())
				throw new Exception();
			return opt.get();
			
		}
		
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
	}
	
	
	@GetMapping("/getPdf")
	 public ResponseEntity<Resource> getPdf(@RequestParam("path") String filepath){
		Path path= Paths.get(filepath);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
		
	}
	
	@PostMapping("/uploadPdf/{accNumber}")
	public ResponseEntity uploadPdf(@PathVariable("accNumber")String accNumber,
			@RequestParam("accord") MultipartFile[] uploadingFiles) {
		
		try {
			for(MultipartFile uploadFile : uploadingFiles) {
				String url= uploadFolder+uploadFile.getOriginalFilename();
				File file= new File(url);
				uploadFile.transferTo(file);
				/*
				 * TO DO
				 * upload path to DB
				 * 
				 */
				
			}
			
			return ResponseEntity.ok().build();
		}
		catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}
		
	}
}
