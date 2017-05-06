package tk.srubio.adoptix.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.srubio.adoptix.model.Pet;
import tk.srubio.adoptix.web.dto.PetDTO;
import tk.srubio.adoptix.web.dto.VaccineDTO;
import tk.srubio.adoptix.web.dto.VetTestDTO;
import tk.srubio.adoptix.web.dto.VetVisitDTO;
import tk.srubio.adoptix.web.search.PetFilter;
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
	public @ResponseBody ResponseEntity<AdoptixResponse> getPets(@RequestParam int page, @RequestParam int rows,
			@RequestParam(required = false) Byte petType, @RequestParam(required = false) String gender,
			@RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge,
			@RequestParam(required = false) Byte location, @RequestParam(required = false) Boolean dogsAffinity,
			@RequestParam(required = false) Boolean catsAffinity,
			@RequestParam(required = false) Boolean kidsAffinity) {
		List<Specification<Pet>> specifications = new  ArrayList<>();
		// Filters
		if (petType != null) {
			specifications.add(PetFilter.equal("petType", petType));
		}
		if (gender != null) {
			specifications.add(PetFilter.equal("gender", gender));
		}
		if (minAge != null) {
			specifications.add(PetFilter.greaterOrEqual("age", minAge));
		}
		if (maxAge != null) {
			specifications.add(PetFilter.lessOrEqual("age", maxAge));
		}
		if (location != null) {
			specifications.add(PetFilter.equalToLocationId("location", location));
		}
		if (dogsAffinity != null) {
			specifications.add(PetFilter.isTrue("dogsAffinity"));
		}
		if (catsAffinity != null) {
			specifications.add(PetFilter.isTrue("catsAffinity"));
		}
		if (kidsAffinity != null) {
			specifications.add(PetFilter.isTrue("kidsAffinity"));
		}
		
		// Filter animals for adopt or for host
		specifications.add(PetFilter.or(PetFilter.isTrue("forAdoption"), PetFilter.isTrue("forHost")));
		
		List<Order> orders = new ArrayList<Sort.Order>();
		orders.add(new Order(Direction.DESC, "creationDate"));
		
		return new ResponseEntity<>(petService.getPets(specifications, new PageRequest(page - 1, rows, new Sort(orders))), HttpStatus.OK);
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
