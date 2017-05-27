package tk.srubio.adoptix.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tk.srubio.adoptix.model.Pet;
import tk.srubio.adoptix.model.PetRepository;
import tk.srubio.adoptix.model.Vaccine;
import tk.srubio.adoptix.model.VaccineRepository;
import tk.srubio.adoptix.web.dto.VaccineDTO;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Service
public class VaccineService extends DTOService<VaccineDTO, Vaccine, Long> {

	@Autowired
	private PetRepository petRepository;
	@Autowired
	private VaccineRepository vaccineRepository;

	@Override
	public VaccineDTO convertToDTO(Vaccine object) {
		VaccineDTO dto = new VaccineDTO(object.getId(), object.getAppliedOn(), object.getDescription(),
				object.getName(), object.getPet().getId());
		return dto;
	}

	@Override
	public Vaccine convertToPOJO(VaccineDTO dtoObject) {
		Pet pet = petRepository.findOne(dtoObject.getPetId());
		Vaccine object = new Vaccine(dtoObject.getId(), dtoObject.getAppliedOn(), dtoObject.getDescription(),
				dtoObject.getName(), pet);
		return object;
	}

	@Override
	public String save(Vaccine object) {
		try {
			super.save(object, vaccineRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String delete(Vaccine object) {
		try {
			super.delete(object, vaccineRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String saveDTO(VaccineDTO object) {
		try {
			super.saveDTO(object, vaccineRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}
	
	public AdoptixResponse getPetVaccines(Long petId, Pageable pageable) {
		Long totalRecords = vaccineRepository.countByPetId(petId);
		List<VaccineDTO> dtos = new ArrayList<>();
		if (totalRecords > 0) {
			Page<Vaccine> page = vaccineRepository.findByPetIdOrderByAppliedOnDesc(petId, pageable);
			for (Vaccine vaccine : page.getContent()) {
				dtos.add(convertToDTO(vaccine));
			}
		}
		AdoptixResponse response = new AdoptixResponse(null, true, dtos, totalRecords);
		return response;
	}
	
	public AdoptixResponse save(Long petId, VaccineDTO form) {
		form.setPetId(petId);
		String result = saveDTO(form);
		if (result == null) {
			return new AdoptixResponse(null, true, null, null);
		} else {
			return new AdoptixResponse(result, false, null, null);
		}
	}
	
	public AdoptixResponse delete(Long vaccineId) {
		AdoptixResponse response = new AdoptixResponse(null, true, null, null);
		try {
			vaccineRepository.delete(vaccineId);
		} catch(Exception ex) {
			response.setMessage(ex.getMessage());
			response.setSuccess(false);
		}
		return response;
	}
}
