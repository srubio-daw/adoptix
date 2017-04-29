package tk.srubio.adoptix.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {

	public Page<Pet> findByAssociationMail(String mail, Pageable pageable);

	public Long countByAssociationMail(String mail);
}
