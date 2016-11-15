package hu.unideb.inf.rft.neuban.web.configuration;

import hu.unideb.inf.rft.neuban.service.configuration.ServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = "hu.unideb.inf.rft.neuban.web.controllers")
@Import(ServiceConfiguration.class)
public class WebConfiguration {
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("i18n/messages");
		return messageSource;
	}
}
