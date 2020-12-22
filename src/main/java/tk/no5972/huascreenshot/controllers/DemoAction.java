package tk.no5972.huascreenshot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoAction")
public class DemoAction {
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(String arg) {
		return "{ \"result\": \"Hello, World\"}";
	}
	

}
