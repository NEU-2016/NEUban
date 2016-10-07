package hu.unideb.inf.rft.service.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hu.unideb.inf.rft.neuban.persistence.configuration.PersistenceConfiguration;

@Configuration
@ComponentScan(basePackages = "hu.unideb.inf.rft.neuban.service")
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {

}
