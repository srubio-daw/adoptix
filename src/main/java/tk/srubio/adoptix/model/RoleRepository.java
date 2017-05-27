package tk.srubio.adoptix.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Byte> {
	public Role findOneByName(String role);
}
