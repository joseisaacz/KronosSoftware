package com.kronos.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kronos.pushNotification.FcmClient;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping(value = "/push")
public class RegistryController {


  @Autowired
  private FcmClient fcmClient;

 
  @GetMapping("/register/{token}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> register(@PathVariable("token")String token) {
	  System.out.println(token);
	  Mono<String> mono= Mono.just(token);
	  System.out.println(mono);
    return mono.doOnNext(t -> this.fcmClient.subscribe("fire", t)).then();
  }
  
  @GetMapping("/sendPush")
  public void sendPush() {
	  Map<String,String> data= new HashMap<>();
	  data.put("id","1");
	  data.put("joke","Hola Mundo");
	  try {
	  this.fcmClient.send(data,"fire");
	  }
	  catch(Exception e) {
		  System.out.println(e.getMessage());
	  }
	  
  }

}