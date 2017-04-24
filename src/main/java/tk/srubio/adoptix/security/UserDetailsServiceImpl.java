package tk.srubio.adoptix.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.srubio.adoptix.model.Role;
import tk.srubio.adoptix.model.WebUser;
import tk.srubio.adoptix.model.WebUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private WebUserRepository webUserRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		WebUser user = webUserRepository.findOneByMailWithRoles(mail);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new User(user.getMail(), user.getPassword(), grantedAuthorities);
	}
}