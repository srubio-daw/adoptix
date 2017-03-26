package tk.srubio.adoptix.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.model.Role;
import tk.srubio.adoptix.model.RoleRepository;
import tk.srubio.adoptix.model.WebUser;
import tk.srubio.adoptix.model.WebUserRepository;
import tk.srubio.adoptix.security.SecurityService;

@Controller
@RequestMapping(value = "/user")
public class WebUserController {
	@Autowired
	private WebUserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String registerUser(@RequestBody WebUser userForm) {
		List<Role> roles = new ArrayList<>();
		if (userForm.isAssociation()) {
			roles = roleRepository.findByNameIn(new String[]{"asociacion"});
		} else {
			roles = roleRepository.findByNameIn(new String[]{"usuario"});
		}
		userForm.setRoles(roles);
		userRepository.save(userForm);
		
		//securityService.autologin(userForm.getMail(), userForm.getPassword());
		return null;
	}
}
