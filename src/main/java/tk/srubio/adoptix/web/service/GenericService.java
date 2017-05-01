package tk.srubio.adoptix.web.service;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public abstract class GenericService<T, ID extends Serializable> {
	public T save(T object, CrudRepository<T, ID> repository) throws Exception{
		return repository.save(object);
	}
	
	public void delete(T object, CrudRepository<T, ID> repository) throws Exception {
		repository.delete(object);
	}
	
	public abstract String save(T object);
	
	public abstract String delete(T object);

}
