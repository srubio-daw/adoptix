package tk.srubio.adoptix.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetVisitRepository extends JpaRepository<VetVisit, Long> {
	Page<VetVisit> findByPetIdOrderByVisitDateDesc(Long petId, Pageable pageable);
	Long countByPetId(Long petId);
}
