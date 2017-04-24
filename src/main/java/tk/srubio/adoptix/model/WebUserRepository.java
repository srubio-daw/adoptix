package tk.srubio.adoptix.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Integer> {
	
	@Query("SELECT u FROM WebUser u INNER JOIN FETCH u.roles WHERE u.mail = :mail")
	public WebUser findOneByMailWithRoles(@Param(value = "mail") String mail);
	
}
