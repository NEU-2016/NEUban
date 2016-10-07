package hu.unideb.inf.rft.neuban.persistence.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "hu.unideb.inf.rft.neuban.persistence.repositories")
@EntityScan(basePackages = "hu.unideb.inf.rft.neuban.persistence.entities")
public class PersistenceConfiguration {

}
