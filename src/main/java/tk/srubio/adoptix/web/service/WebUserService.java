package tk.srubio.adoptix.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tk.srubio.adoptix.model.ProvinceRepository;
import tk.srubio.adoptix.model.Role;
import tk.srubio.adoptix.model.RoleRepository;
import tk.srubio.adoptix.model.WebUser;
import tk.srubio.adoptix.model.WebUserRepository;
import tk.srubio.adoptix.web.dto.RoleEnum;
import tk.srubio.adoptix.web.dto.WebUserDTO;
import tk.srubio.adoptix.web.util.AdoptixResponse;

@Service
public class WebUserService extends DTOService<WebUserDTO, WebUser, Integer> {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private WebUserRepository webUserRepository;
	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public WebUserDTO convertToDTO(WebUser object) {
		WebUserDTO dto = new WebUserDTO(object.getId(), object.getAddress(), object.getMail(), object.getName(),
				object.getNif(), object.getPassword(), object.getSurname(), object.getUsername(),
				object.getProvince() != null ? object.getProvince().getId() : null, false);
		for (Role role : object.getRoles()) {
			if (role.getName().equals(RoleEnum.ASOCIACION.getName())) {
				dto.setAssociation(true);
				break;
			}
		}
		return dto;
	}

	@Override
	public WebUser convertToPOJO(WebUserDTO dtoObject) {
		WebUser object = new WebUser(dtoObject.getId() != null ? dtoObject.getId() : 0, dtoObject.getAddress(),
				dtoObject.getMail(), dtoObject.getName(), dtoObject.getNif(), dtoObject.getPassword(),
				dtoObject.getSurname(), dtoObject.getUsername(), null, null);
		List<Role> roles = new ArrayList<>();
		if (dtoObject.isAssociation()) {
			roles.add(roleRepository.findOneByName(RoleEnum.ASOCIACION.getName()));
		} else {
			roles.add(roleRepository.findOneByName(RoleEnum.USUARIO.getName()));
		}
		object.setRoles(roles);
		object.setProvince(provinceRepository.findOne(dtoObject.getProvince()));
		return object;
	}

	@Override
	public String save(WebUser object) {
		try {
			super.save(object, webUserRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String delete(WebUser object) {
		try {
			super.delete(object, webUserRepository);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	@Override
	public String saveDTO(WebUserDTO object) {
		try {
			super.saveDTO(object, webUserRepository);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause().getCause().getMessage().contains("Duplicate")) {
				return "error.duplicated." + ex.getCause().getCause().getMessage().split("key ")[1].replace("'", "");
			} else{
				return ex.getMessage();
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return null;
	}

	public AdoptixResponse registerAndLogin(WebUserDTO dto) {
		AdoptixResponse response = new AdoptixResponse(null, true, null);
		String result = saveDTO(dto);
		if (result == null) {
			response = login(dto);
		} else {
			response.setMessage(result);
			response.setSuccess(false);
		}
		return response;
	}

	public AdoptixResponse login(WebUserDTO dto) {
		AdoptixResponse response = new AdoptixResponse(null, true, null);
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getMail(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			response.setData(authentication);
		} catch (BadCredentialsException ex) {
			response.setSuccess(false);
			response.setMessage("error.badCredentials");
		} catch (AuthenticationException ex) {
			response.setSuccess(false);
			response.setMessage(ex.getMessage());
		}
		return response;
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	public AdoptixResponse getUser(String mail) {
		AdoptixResponse response = new AdoptixResponse(null, false, null);
		WebUser user = webUserRepository.findOneByMailWithRoles(mail);
		if (user != null) {
			response.setSuccess(true);
			response.setData(convertToDTO(user));
		}
		return response;
	}
	
	public AdoptixResponse updateUserData(WebUserDTO dto) {
		WebUser user = webUserRepository.findOneByMailWithRoles(dto.getMail());
		// Data change
		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		user.setProvince(provinceRepository.findOne(dto.getProvince()));
		user.setNif(dto.getNif());
		user.setUsername(dto.getUsername());
		user.setAddress(dto.getAddress());
		// Role change
		List<Role> roles = new ArrayList<>();
		if (dto.isAssociation() != convertToDTO(user).isAssociation()) {
			if (dto.isAssociation()) {
				roles.add(roleRepository.findOneByName(RoleEnum.ASOCIACION.getName()));
			} else {
				roles.add(roleRepository.findOneByName(RoleEnum.USUARIO.getName()));
			}
			user.setRoles(roles);
		}
		
		AdoptixResponse response = new AdoptixResponse();
		try {
			webUserRepository.save(user);
			response.setSuccess(true);
			response.setData(convertToDTO(user));
		} catch (DataIntegrityViolationException ex) {
			response.setSuccess(false);
			if (ex.getCause().getCause().getMessage().contains("Duplicate")) {
				response.setMessage("error.duplicated." + ex.getCause().getCause().getMessage().split("key ")[1].replace("'", ""));
			} else {
				response.setMessage(ex.getMessage());
			}
		} catch (Exception ex) {
			response.setMessage(ex.getMessage());
			response.setSuccess(false);
		}
		return response;
	}
	
	public AdoptixResponse updateUserPassword(String mail, String password) {
		WebUser user = webUserRepository.findOneByMailWithRoles(mail);
		user.setPassword(password);
		webUserRepository.save(user);
		AdoptixResponse response = new AdoptixResponse();
		response.setSuccess(true);
		return response;
	}
	
	public AdoptixResponse getNormalUsers() {
		AdoptixResponse response = new AdoptixResponse();
		Role userRole = roleRepository.findOneByName(RoleEnum.USUARIO.getName());
		List<WebUser> users = webUserRepository.findAllByRole(userRole);
		List<WebUserDTO> dtoUsers = new ArrayList<>();
		for (WebUser user : users) {
			dtoUsers.add(convertToDTO(user));
		}
		response.setSuccess(true);
		response.setData(dtoUsers);
		return response;
	}
}
