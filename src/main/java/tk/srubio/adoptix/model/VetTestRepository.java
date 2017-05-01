package tk.srubio.adoptix.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetTestRepository extends JpaRepository<VetTest, Long> {
	Page<VetTest> findByPetIdOrderByAppliedOnDesc(Long petId, Pageable pageable);
	Long countByPetId(Long petId);
}
