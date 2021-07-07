package com.kronos.live;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/streaming")
public class LiveStreamingController {

	@GetMapping("/live")
	public String goLive() {
		
		return "/streaming/live";
	}
	
	
}
