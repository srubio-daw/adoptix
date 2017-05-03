package tk.srubio.adoptix.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaSpecificationExecutor<Pet>, PagingAndSortingRepository<Pet, Long> {

	public Page<Pet> findByAssociationMail(String mail, Pageable pageable);

	public Long countByAssociationMail(String mail);
	
	public Page<Pet> findByOrderByCreationDateDesc(Pageable pageable);
}
