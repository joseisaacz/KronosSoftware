package com.kronos.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;

import com.kronos.model.Accord;
import com.kronos.service.AccordService;

@Configuration
@EnableScheduling
@EnableAsync
public class Scheduler {

	@Autowired 
	private EmailServiceImpl email;
	@Autowired
	private AccordService acc;
	
	@Scheduled(cron="00 36 16 * * SAT")
	public void weeklyNotification() throws Exception {
		Calendar limit= Calendar.getInstance();
	    limit.set(Calendar.DAY_OF_MONTH,limit.get(Calendar.DAY_OF_MONTH)-8);
	    Date lmt=limit.getTime();
	    Date today = Calendar.getInstance().getTime();
	    List<Accord> list = acc.emailInfo(today,lmt);
	    String message=" ";
	    for(Accord a : list){
	     message= message +"\n"+ a.toString();
	    }
	    String message1= "Los acuerdos notificados esta semana son los siguientes \n"+message;
		email.sendSimpleMail("jjestradav@gmail.com", "Acuerdos de la semana", message1);
		System.out.println(message1);
	}
}
