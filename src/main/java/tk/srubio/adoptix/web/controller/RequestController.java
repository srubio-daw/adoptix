package tk.srubio.adoptix.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.web.dto.RequestDTO;
import tk.srubio.adoptix.web.service.RequestService;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Controller
@RequestMapping("/request")
public class RequestController {
	@Autowired
	private RequestService requestService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> createRequest(@RequestBody RequestDTO request) {
		return new ResponseEntity<AdoptixResponse>(requestService.createRequest(request), HttpStatus.OK);
	}

	@RequestMapping(value = "/byUserAndPet", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getRequestByUserAndPet(@RequestParam String userMail,
			@RequestParam Long petId, @RequestParam boolean adoptOrHost) {
		return new ResponseEntity<AdoptixResponse>(requestService.getByUserAndPet(userMail, petId, adoptOrHost),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/pet", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getRequestByPet(@RequestParam Long petId,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<AdoptixResponse>(requestService.getByPet(petId, new PageRequest(page - 1, rows
				)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/pet/managed", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getRequestManagedByPet(@RequestParam Long petId,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<AdoptixResponse>(requestService.getManagedByPet(petId, new PageRequest(page - 1, rows)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getRequestByUser(@RequestParam String mail,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<AdoptixResponse>(requestService.getByUser(mail, new PageRequest(page - 1, rows)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/user/managed", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getRequestManagedByUser(@RequestParam String mail,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<AdoptixResponse>(requestService.getManagedByUser(mail, new PageRequest(page - 1, rows)),
				HttpStatus.OK);
	}
}
