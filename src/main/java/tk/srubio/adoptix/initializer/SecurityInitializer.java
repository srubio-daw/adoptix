package tk.srubio.adoptix.initializer;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import tk.srubio.adoptix.config.SecurityConfig;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfig.class };
	}
}