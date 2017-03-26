package tk.srubio.adoptix.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Integer> {
	
	public WebUser findOneByMail(String mail);
	
}
