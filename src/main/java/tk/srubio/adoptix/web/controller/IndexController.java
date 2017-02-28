package tk.srubio.adoptix.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	@RequestMapping("/holaMundo")
	public @ResponseBody String holaMundo() {
		return "Hola mundo";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex() {
		return "redirect:/index.html";
	}
}