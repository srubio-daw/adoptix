package tk.srubio.adoptix.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.web.dto.WebUserDTO;
import tk.srubio.adoptix.web.service.WebUserService;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Controller
@RequestMapping(value = "/user")
public class WebUserController {
	
	@Autowired
	private WebUserService webUserService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> register(@RequestBody WebUserDTO userForm) {
		return new ResponseEntity<>(webUserService.registerAndLogin(userForm), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> login(@RequestBody WebUserDTO userForm) {
		return new ResponseEntity<>(webUserService.login(userForm), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> logout() {
		webUserService.logout();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> getUser(@RequestParam("mail") String mail) {
		return new ResponseEntity<>(webUserService.getUser(mail), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> updateUserData(@RequestBody WebUserDTO userForm) {
		return new ResponseEntity<>(webUserService.updateUserData(userForm), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> updateUserData(@RequestParam("mail") String mail, @RequestParam("password") String password) {
		return new ResponseEntity<>(webUserService.updateUserPassword(mail, password), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/normal", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getNormalUsers() {
		return new ResponseEntity<>(webUserService.getNormalUsers(), HttpStatus.OK);
	}
}
