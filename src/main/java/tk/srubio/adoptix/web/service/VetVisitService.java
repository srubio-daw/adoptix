package tk.srubio.adoptix.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tk.srubio.adoptix.model.Pet;
import tk.srubio.adoptix.model.PetRepository;
import tk.srubio.adoptix.model.VetVisit;
import tk.srubio.adoptix.model.VetVisitRepository;
import tk.srubio.adoptix.web.dto.VetVisitDTO;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Service
public class VetVisitService extends DTOService<VetVisitDTO, VetVisit, Long> {

	@Autowired
	private PetRepository petRepository;
	@Autowired
	private VetVisitRepository vetVisitRepository;

	@Override
	public VetVisitDTO convertToDTO(VetVisit object) {
		VetVisitDTO dto = new VetVisitDTO(object.getId(), object.getCost(), object.getDescription(),
				object.getVisitDate(), object.getPet().getId());
		return dto;
	}

	@Override
	public VetVisit convertToPOJO(VetVisitDTO dtoObject) {
		Pet pet = petRepository.findOne(dtoObject.getPetId());
		VetVisit object = new VetVisit(dtoObject.getId(), dtoObject.getCost(), dtoObject.getDescription(),
				dtoObject.getVisitDate(), pet);
		return object;
	}

	@Override
	public String save(VetVisit object) {
		try {
			super.save(object, vetVisitRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String delete(VetVisit object) {
		try {
			super.delete(object, vetVisitRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String saveDTO(VetVisitDTO object) {
		try {
			super.saveDTO(object, vetVisitRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}
	
	public AdoptixResponse getPetVetVisits(Long petId, Pageable pageable) {
		Long totalRecords = vetVisitRepository.countByPetId(petId);
		List<VetVisitDTO> dtos = new ArrayList<>();
		if (totalRecords > 0) {
			Page<VetVisit> page = vetVisitRepository.findByPetIdOrderByVisitDateDesc(petId, pageable);
			for (VetVisit vetVisit : page.getContent()) {
				dtos.add(convertToDTO(vetVisit));
			}
		}
		AdoptixResponse response = new AdoptixResponse(null, true, dtos, totalRecords);
		return response;
	}
	
	public AdoptixResponse save(Long petId, VetVisitDTO form) {
		form.setPetId(petId);
		String result = saveDTO(form);
		if (result == null) {
			return new AdoptixResponse(null, true, null, null);
		} else {
			return new AdoptixResponse(result, false, null, null);
		}
	}
	
	public AdoptixResponse delete(Long vetVisitId) {
		AdoptixResponse response = new AdoptixResponse(null, true, null, null);
		try {
			vetVisitRepository.delete(vetVisitId);
		} catch(Exception ex) {
			response.setMessage(ex.getMessage());
			response.setSuccess(false);
		}
		return response;
	}
}
