package tk.srubio.adoptix.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tk.srubio.adoptix.model.Pet;
import tk.srubio.adoptix.model.PetRepository;
import tk.srubio.adoptix.model.VetTest;
import tk.srubio.adoptix.model.VetTestRepository;
import tk.srubio.adoptix.web.dto.VetTestDTO;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Service
public class VetTestService extends DTOService<VetTestDTO, VetTest, Long> {

	@Autowired
	private PetRepository petRepository;
	@Autowired
	private VetTestRepository vetTestRepository;

	@Override
	public VetTestDTO convertToDTO(VetTest object) {
		VetTestDTO dto = new VetTestDTO(object.getId(), object.getAppliedOn(), object.getDescription(),
				object.getName(), object.getPet().getId());
		return dto;
	}

	@Override
	public VetTest convertToPOJO(VetTestDTO dtoObject) {
		Pet pet = petRepository.findOne(dtoObject.getPetId());
		VetTest object = new VetTest(dtoObject.getId(), dtoObject.getAppliedOn(), dtoObject.getDescription(),
				dtoObject.getName(), pet);
		return object;
	}

	@Override
	public String save(VetTest object) {
		try {
			super.save(object, vetTestRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String delete(VetTest object) {
		try {
			super.delete(object, vetTestRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String saveDTO(VetTestDTO object) {
		try {
			super.saveDTO(object, vetTestRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	public AdoptixResponse getPetVetTests(Long petId, Pageable pageable) {
		Long totalRecords = vetTestRepository.countByPetId(petId);
		List<VetTestDTO> dtos = new ArrayList<>();
		if (totalRecords > 0) {
			Page<VetTest> page = vetTestRepository.findByPetIdOrderByAppliedOnDesc(petId, pageable);
			for (VetTest vetTest : page.getContent()) {
				dtos.add(convertToDTO(vetTest));
			}
		}
		AdoptixResponse response = new AdoptixResponse(null, true, dtos, totalRecords);
		return response;
	}

	public AdoptixResponse save(Long petId, VetTestDTO form) {
		form.setPetId(petId);
		String result = saveDTO(form);
		if (result == null) {
			return new AdoptixResponse(null, true, null, null);
		} else {
			return new AdoptixResponse(result, false, null, null);
		}
	}
	
	public AdoptixResponse delete(Long vetTestId) {
		AdoptixResponse response = new AdoptixResponse(null, true, null, null);
		try {
			vetTestRepository.delete(vetTestId);
		} catch(Exception ex) {
			response.setMessage(ex.getMessage());
			response.setSuccess(false);
		}
		return response;
	}
}
