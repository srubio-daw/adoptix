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

import tk.srubio.adoptix.web.dto.PetDTO;
import tk.srubio.adoptix.web.service.PetService;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Controller
@RequestMapping(value = "/pet")
public class PetController {
	
	@Autowired
	private PetService petService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> register(@RequestBody PetDTO petForm) {
		return new ResponseEntity<>(petService.create(petForm), HttpStatus.OK);
	}
}
