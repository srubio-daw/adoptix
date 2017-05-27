package tk.srubio.adoptix.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
				object.getPet().getId(), object.getPet().getName(), object.getPet().getPetType(),
				object.isAdoptOrHost(), object.getCreationDate(), object.getUser().getMail(), object.getStatus(),
				object.getRejectComment(), object.getUser().getName());
		return dto;
	}

	@Override
	public Request convertToPOJO(RequestDTO dtoObject) {
		WebUser user = webUserRepository.findOneByMailWithRoles(dtoObject.getUserMail());
		Pet pet = petRepository.findOne(dtoObject.getPet());
		Request object = new Request(dtoObject.getId(), dtoObject.getCatsAtHome(), dtoObject.getComment(),
				dtoObject.getDogsAtHome(), dtoObject.getKidsAtHome(), dtoObject.getPhone(), user, pet,
				dtoObject.isAdoptOrHost(), dtoObject.getCreationDate(), dtoObject.getStatus(),
				dtoObject.getRejectComment());
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

	@Transactional
	public AdoptixResponse createRequest(RequestDTO dto) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		// Si es una aceptación, marcar el animal como adoptado/acogido
		if (dto.getStatus() != null && dto.getStatus()) {
			Pet pet = petRepository.findOne(dto.getPet());
			if (dto.isAdoptOrHost()) {
				pet.setForAdoption(false);
				pet.setAdopter(webUserRepository.findOne(dto.getUser()));
			} else {
				pet.setForHost(false);
				pet.setHost(webUserRepository.findOne(dto.getUser()));
			}
			petRepository.save(pet);
		}
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

	public AdoptixResponse getByPet(Long petId, Pageable pageable) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByPetIdAndStatusIsNull(petId);
		List<RequestDTO> dtos = new ArrayList<>();
		if (count > 0) {
			Page<Request> page = requestRepository.findByPetIdAndStatusIsNullOrderByCreationDateDesc(petId, pageable);
			for (Request r : page.getContent()) {
				dtos.add(convertToDTO(r));
			}
		}
		response.setData(dtos);
		response.setTotalRecords(count);
		response.setSuccess(true);
		return response;
	}

	public AdoptixResponse getCountByPet(Long petId) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByPetIdAndStatusIsNull(petId);
		response.setTotalRecords(count);
		response.setSuccess(true);
		return response;
	}

	public AdoptixResponse getManagedByPet(Long petId, Pageable pageable) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByPetIdAndStatusIsNotNull(petId);
		List<RequestDTO> dtos = new ArrayList<>();
		if (count > 0) {
			Page<Request> page = requestRepository.findByPetIdAndStatusIsNotNullOrderByCreationDateDesc(petId,
					pageable);
			for (Request r : page.getContent()) {
				dtos.add(convertToDTO(r));
			}
		}
		response.setData(dtos);
		response.setTotalRecords(count);
		response.setSuccess(true);
		return response;
	}

	public AdoptixResponse getByUser(String mail, Pageable pageable) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByUserMailAndStatusIsNull(mail);
		List<RequestDTO> dtos = new ArrayList<>();
		if (count > 0) {
			Page<Request> page = requestRepository.findByUserMailAndStatusIsNull(mail, pageable);
			for (Request r : page.getContent()) {
				dtos.add(convertToDTO(r));
			}
		}
		response.setData(dtos);
		response.setTotalRecords(count);
		response.setSuccess(true);
		return response;
	}

	public AdoptixResponse getManagedByUser(String mail, Pageable pageable) {
		AdoptixResponse response = new AdoptixResponse(null, false, null, null);
		Long count = requestRepository.countByUserMailAndStatusIsNotNull(mail);
		List<RequestDTO> dtos = new ArrayList<>();
		if (count > 0) {
			Page<Request> page = requestRepository.findByUserMailAndStatusIsNotNull(mail, pageable);
			for (Request r : page.getContent()) {
				dtos.add(convertToDTO(r));
			}
		}
		response.setData(dtos);
		response.setTotalRecords(count);
		response.setSuccess(true);
		return response;
	}

}
