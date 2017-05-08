package tk.srubio.adoptix.web.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import tk.srubio.adoptix.model.Pet;
import tk.srubio.adoptix.model.PetRepository;
import tk.srubio.adoptix.model.ProvinceRepository;
import tk.srubio.adoptix.model.RequestRepository;
import tk.srubio.adoptix.model.WebUser;
import tk.srubio.adoptix.model.WebUserRepository;
import tk.srubio.adoptix.web.dto.PetDTO;
import tk.srubio.adoptix.web.search.PetFilter;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Service
public class PetService extends DTOService<PetDTO, Pet, Long> {

	@Autowired
	private PetRepository petRepository;
	@Autowired
	private WebUserRepository webUserRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private String imagesFolder;

	@Override
	public PetDTO convertToDTO(Pet object) {
		Long pendingRequests = 0L;
		if (object.getId() != null) {
			pendingRequests = requestRepository.countByPetIdAndStatusIsNull(object.getId());
		}
		PetDTO dto = new PetDTO(object.getId(), object.getAge(), object.getBreed(), object.getCatsAffinity(),
				object.getDescription(), object.getDogsAffinity(), object.getForAdoption(), object.getForHost(),
				object.getGender(), object.getKidsAffinity(), object.getName(), object.getPetType(),
				object.getLocation().getId(), object.getLocation().getName(),
				object.getAdopter() != null ? object.getAdopter().getId() : null,
				object.getAdopter() != null ? object.getAdopter().getName() : null,
				object.getHost() != null ? object.getHost().getId() : null,
				object.getHost() != null ? object.getHost().getName() : null, object.getAssociation().getId(),
				object.getAssociation().getMail(), object.getCreationDate(), pendingRequests);
		return dto;
	}

	@Override
	public Pet convertToPOJO(PetDTO dtoObject) {
		WebUser adopter = null;
		WebUser host = null;
		WebUser association = null;
		if (dtoObject.getAdopter() != null) {
			adopter = webUserRepository.findOne(dtoObject.getAdopter());
		}
		if (dtoObject.getHost() != null) {
			host = webUserRepository.findOne(dtoObject.getHost());
		}
		if (dtoObject.getAssociation() != null) {
			association = webUserRepository.findOne(dtoObject.getAssociation());
		}

		Pet object = new Pet(dtoObject.getId(), dtoObject.getAge(), dtoObject.getBreed(), dtoObject.getCatsAffinity(),
				dtoObject.getDescription(), dtoObject.getDogsAffinity(), dtoObject.getForAdoption(),
				dtoObject.getForHost(), dtoObject.getGender(), dtoObject.getKidsAffinity(), dtoObject.getName(),
				dtoObject.getPetType(), provinceRepository.findOne(dtoObject.getLocationId()), adopter, host,
				association, dtoObject.getCreationDate());
		return object;
	}

	@Override
	public String save(Pet object) {
		try {
			super.save(object, petRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String delete(Pet object) {
		try {
			super.delete(object, petRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String saveDTO(PetDTO object) {
		try {
			Pet saved = super.saveDTO(object, petRepository);
			return "SAVED: " + saved.getId();
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause().getCause().getMessage().contains("Duplicate")) {
				return "error.duplicated." + ex.getCause().getCause().getMessage().split("key ")[1].replace("'", "");
			} else {
				return ex.getMessage();
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	@Transactional
	public AdoptixResponse save(PetDTO dto, MultipartFile file) {
		AdoptixResponse response = new AdoptixResponse();
		WebUser association = webUserRepository.findOneByMailWithRoles(dto.getUserMail());
		dto.setAssociation(association.getId());
		String result = saveDTO(dto);
		if (result.startsWith("SAVED: ")) {
			Long petId = Long.valueOf(result.split("SAVED: ")[1]);
			// Save image in folder
			if (file != null) {
				String folderPath = imagesFolder + "/" + petId;
				String filePath = folderPath + "/" + file.getOriginalFilename();
				try {
					// Check if folder exists to remove it
					if (Files.isDirectory(Paths.get(folderPath))) {
						FileUtils.deleteDirectory(new File(folderPath));
					}
					// Create directory
					Files.createDirectory(Paths.get(folderPath));
					// Save image
					File imageFile = new File(filePath);
					file.transferTo(imageFile);
					BufferedImage image = ImageIO.read(imageFile);
					ImageIO.write(image, filePath.split("\\.")[filePath.split("\\.").length - 1], imageFile);
					
				} catch (IOException e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					response.setSuccess(false);
					response.setMessage(e.getMessage());
					return response;
				}
			}
			response.setSuccess(true);
			response.setData(petId);
		} else {
			response.setSuccess(false);
			response.setMessage(result);
		}
		return response;
	}

	public AdoptixResponse getMyPets(String mail, Pageable pageable) {
		Long totalRecords = petRepository.countByAssociationMail(mail);
		List<PetDTO> petsDTO = new ArrayList<>();
		if (totalRecords > 0) {
			Page<Pet> pets = petRepository.findByAssociationMail(mail, pageable);
			for (Pet pet : pets) {
				petsDTO.add(convertToDTO(pet));
			}
		}
		AdoptixResponse response = new AdoptixResponse(null, true, petsDTO, totalRecords);
		return response;
	}

	public AdoptixResponse getPets(List<Specification<Pet>> specifications, Pageable pageable) {
		Long totalRecords = petRepository.count(PetFilter.getSpecification(specifications));
		List<PetDTO> petsDTO = new ArrayList<>();
		if (totalRecords > 0) {
			Page<Pet> pets = petRepository.findAll(PetFilter.getSpecification(specifications), pageable);
			for (Pet pet : pets) {
				petsDTO.add(convertToDTO(pet));
			}
		}
		return new AdoptixResponse(null, true, petsDTO, totalRecords);
	}

	public AdoptixResponse getPet(Long petId) {
		Pet pet = petRepository.findOne(petId);
		return new AdoptixResponse(null, true, convertToDTO(pet), null);
	}

	public AdoptixResponse delete(Long petId) {
		AdoptixResponse response = new AdoptixResponse(null, true, null, null);
		try {
			petRepository.delete(petId);
		} catch (Exception ex) {
			response.setMessage(ex.getMessage());
			response.setSuccess(false);
		}
		return response;
	}

}
