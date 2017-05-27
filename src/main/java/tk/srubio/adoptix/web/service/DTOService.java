package tk.srubio.adoptix.web.service;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public abstract class DTOService<DTO, POJO, ID extends Serializable> extends GenericService<POJO, ID> {
	
	public abstract DTO convertToDTO(POJO object);

	public abstract POJO convertToPOJO(DTO dtoObject);
	
	public POJO saveDTO(DTO object, CrudRepository<POJO, ID> repository) throws Exception {
		return super.save(convertToPOJO(object), repository);
	}
	
	public abstract String saveDTO(DTO object);
	
}
