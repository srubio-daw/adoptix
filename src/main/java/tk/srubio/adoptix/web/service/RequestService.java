package tk.srubio.adoptix.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tk.srubio.adoptix.model.Pet;
import tk.srubio.adoptix.model.PetRepository;
import tk.srubio.adoptix.model.Request;
import tk.srubio.adoptix.model.RequestRepository;
import tk.srubio.adoptix.model.WebUser;
import tk.srubio.adoptix.model.WebUserRepository;
import tk.srubio.adoptix.web.dto.RequestDTO;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Service
public class RequestService extends DTOService<RequestDTO, Request, Long> {

	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private WebUserRepository webUserRepository;
	@Autowired
	private PetRepository petRepository;

	@Override
	public RequestDTO convertToDTO(Request object) {
		RequestDTO dto = new RequestDTO(object.getId(), object.getCatsAtHome(), object.getComment(),
				object.getDogsAtHome(), object.getKidsAtHome(), object.getPhone(), object.getUser().getId(),
				object.getPet().getId(), object.isAdoptOrHost(), object.getCreationDate(), object.getUser().getMail());
		return dto;
	}

	@Override
	public Request convertToPOJO(RequestDTO dtoObject) {
		WebUser user = webUserRepository.findOneByMailWithRoles(dtoObject.getUserMail());
		Pet pet = petRepository.findOne(dtoObject.getPet());
		Request object = new Request(dtoObject.getId(), dtoObject.getCatsAtHome(), dtoObject.getComment(),
				dtoObject.getDogsAtHome(), dtoObject.getKidsAtHome(), dtoObject.getPhone(), user, pet,
				dtoObject.isAdoptOrHost(), dtoObject.getCreationDate());
		return object;
	}

	@Override
	public String save(Request object) {
		try {
			super.save(object, requestRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String delete(Request object) {
		try {
			super.delete(object, requestRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String saveDTO(RequestDTO object) {
		try {
			super.saveDTO(object, requestRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	public AdoptixResponse getRequestsByPet(Long petId, Pageable pageable) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByPetId(petId);
		if (count > 0) {
			Page<Request> page = requestRepository.findByPetIdOrderByCreationDateDesc(petId, pageable);
			response.setData(page.getContent());
		}
		response.setTotalRecords(count);
		response.setSuccess(true);
		return response;
	}
	
	public AdoptixResponse createRequest(RequestDTO dto) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		String error = saveDTO(dto);
		if (error == null) {
			response.setSuccess(true);
		} else {
			response.setMessage(error);
		}
		return response;
	}
	
	public AdoptixResponse getByUserAndPet(String userMail, Long petId, boolean adoptOrHost) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByUserMailAndPetIdAndAdoptOrHost(userMail, petId, adoptOrHost);
		response.setSuccess(true);
		response.setTotalRecords(count);
		return response;
	}

}
