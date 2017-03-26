package tk.srubio.adoptix.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Byte> {
	public List<Role> findByNameIn(String [] roleNames);
}
