package com.kronos.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;

@Configuration
@EnableScheduling
@EnableAsync
public class Scheduler {

	@Autowired 
	private EmailServiceImpl email;
	
	@Scheduled(cron="00 15 12 * * SAT")
	public void weeklyNotification() {
		email.sendSimpleMail("jjestradav@gmail.com", "Prueba", "Que siente bb?");
		System.out.println("this is a fucking test");
	}
}
