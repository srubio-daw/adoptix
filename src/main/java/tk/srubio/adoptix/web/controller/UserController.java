package tk.srubio.adoptix.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.model.User;
import tk.srubio.adoptix.model.UserRepository;

@Controller
@RequestMapping(value = "user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public @ResponseBody String registerUser(@RequestBody User user) {
		return null;
	}
}
