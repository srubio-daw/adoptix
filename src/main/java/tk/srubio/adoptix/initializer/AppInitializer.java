package tk.srubio.adoptix.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("tk.srubio.adoptix.config");
		
		container.addListener(new ContextLoaderListener(context));
		
		ServletRegistration.Dynamic registration = container.addServlet("Adoptix Servlet",
				new DispatcherServlet(context));
				
		registration.setLoadOnStartup(1);
		registration.addMapping("*.html");
	}
	
}
