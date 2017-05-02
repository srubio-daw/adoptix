package tk.srubio.adoptix.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.web.dto.PetDTO;
import tk.srubio.adoptix.web.dto.VaccineDTO;
import tk.srubio.adoptix.web.dto.VetTestDTO;
import tk.srubio.adoptix.web.dto.VetVisitDTO;
import tk.srubio.adoptix.web.service.PetService;
import tk.srubio.adoptix.web.service.VaccineService;
import tk.srubio.adoptix.web.service.VetTestService;
import tk.srubio.adoptix.web.service.VetVisitService;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Controller
@RequestMapping(value = "/pet")
public class PetController {

	@Autowired
	private PetService petService;

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private VetVisitService vetVisitService;

	@Autowired
	private VetTestService vetTestService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> save(@RequestBody PetDTO petForm) {
		return new ResponseEntity<>(petService.save(petForm), HttpStatus.OK);
	}

	@RequestMapping(value = "/myPets", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getMyPets(@RequestParam String mail, @RequestParam int page,
			@RequestParam int rows) {
		return new ResponseEntity<>(petService.getMyPets(mail, new PageRequest(page - 1, rows)), HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getPets(@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<>(petService.getPets(new PageRequest(page - 1, rows)), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getPet(@PathVariable(name = "petId") Long petId) {
		return new ResponseEntity<>(petService.getPet(petId), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/delete", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> deletePet(@PathVariable(name = "petId") Long petId) {
		return new ResponseEntity<>(petService.delete(petId), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/vaccines", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getVaccines(@PathVariable(name = "petId") Long petId,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<>(vaccineService.getPetVaccines(petId, new PageRequest(page - 1, rows)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteVaccine/{vaccineId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> deleteVaccine(
			@PathVariable(name = "vaccineId") Long vaccineId) {
		return new ResponseEntity<>(vaccineService.delete(vaccineId), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/vaccine", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> saveVaccine(@PathVariable(name = "petId") Long petId,
			@RequestBody VaccineDTO vaccineForm) {
		return new ResponseEntity<>(vaccineService.save(petId, vaccineForm), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/medicalTests", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getVetTests(@PathVariable(name = "petId") Long petId,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<>(vetTestService.getPetVetTests(petId, new PageRequest(page - 1, rows)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteMedicalTest/{medicalTestId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> deleteMedicalTest(
			@PathVariable(name = "medicalTestId") Long medicalTestId) {
		return new ResponseEntity<>(vetTestService.delete(medicalTestId), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/medicalTest", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> saveVetTest(@PathVariable(name = "petId") Long petId,
			@RequestBody VetTestDTO vetTestForm) {
		return new ResponseEntity<>(vetTestService.save(petId, vetTestForm), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/vetVisits", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> getVetVisits(@PathVariable(name = "petId") Long petId,
			@RequestParam int page, @RequestParam int rows) {
		return new ResponseEntity<>(vetVisitService.getPetVetVisits(petId, new PageRequest(page - 1, rows)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteVetVisit/{vetVisitId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AdoptixResponse> deleteVetVisit(
			@PathVariable(name = "vetVisitId") Long vetVisitId) {
		return new ResponseEntity<>(vetVisitService.delete(vetVisitId), HttpStatus.OK);
	}

	@RequestMapping(value = "/{petId}/vetVisit", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<AdoptixResponse> saveVetVisit(@PathVariable(name = "petId") Long petId,
			@RequestBody VetVisitDTO vetVisitForm) {
		return new ResponseEntity<>(vetVisitService.save(petId, vetVisitForm), HttpStatus.OK);
	}
}
