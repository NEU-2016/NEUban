package hu.unideb.inf.rft.neuban.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hu.unideb.inf.rft.neuban.service.configuration.ServiceConfiguration;

@Configuration
@ComponentScan(basePackages = "hu.unideb.inf.rft.neuban.web.controllers")
@Import(ServiceConfiguration.class)
public class WebConfiguration {

}
