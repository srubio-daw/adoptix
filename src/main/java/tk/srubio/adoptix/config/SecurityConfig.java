package tk.srubio.adoptix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").requiresChannel().anyRequest().requiresSecure();
		http.authorizeRequests().antMatchers("/asociacion/**").hasRole("asociacion").antMatchers("/usuario/**")
				.hasRole("usuario").anyRequest().permitAll();
	}
}