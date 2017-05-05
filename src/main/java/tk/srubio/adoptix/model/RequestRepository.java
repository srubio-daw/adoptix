package tk.srubio.adoptix.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

	public Page<Request> findByPetIdOrderByCreationDateDesc(Long petId, Pageable pageable);

	public Long countByPetId(Long petId);

	@Query(value = "SELECT count(r) FROM Request r WHERE user.mail = :mail AND pet.id = :petId AND adoptOrHost = :adoptOrHost")
	public Long countByUserMailAndPetIdAndAdoptOrHost(@Param(value = "mail") String mail,
			@Param(value = "petId") Long petId, @Param(value = "adoptOrHost") boolean adoptOrHost);
}
