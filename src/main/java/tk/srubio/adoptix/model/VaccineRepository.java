package tk.srubio.adoptix.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends PagingAndSortingRepository<Vaccine, Long> {
	Page<Vaccine> findByPetIdOrderByAppliedOnDesc(Long petId, Pageable pageable);
	Long countByPetId(Long petId);
}
