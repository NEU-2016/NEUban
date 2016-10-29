package hu.unideb.inf.rft.neuban.persistence.annotations;

import hu.unideb.inf.rft.neuban.persistence.configuration.PersistenceConfiguration;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DataJpaTest
@SpringBootTest(classes = PersistenceConfiguration.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles(profiles = "integration")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JPARepositoryTest {
}
