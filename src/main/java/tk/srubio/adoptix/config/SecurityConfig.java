package tk.srubio.adoptix.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT mail, password, 1 FROM web_user WHERE mail = ?")
				.authoritiesByUsernameQuery("SELECT u.mail, r.name FROM user_role ur "
						+ "INNER JOIN web_user u ON u.id = ur.user_id "
						+ "INNER JOIN role r ON r.id = ur.role_id "
						+ "WHERE u.mail = ?");
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	protected AuthenticationManager authenticationManager() throws Exception {
		// This bean is required for using method security annotations
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.antMatcher("/**").requiresChannel().anyRequest().requiresSecure();
		//http.authorizeRequests().antMatchers("/association/**").hasRole("asociacion").antMatchers("/user/**").hasRole("usuario").anyRequest().permitAll();
	}
}