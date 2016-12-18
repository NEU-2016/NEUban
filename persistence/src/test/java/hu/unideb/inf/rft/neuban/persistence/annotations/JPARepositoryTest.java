package hu.unideb.inf.rft.neuban.persistence.annotations;

import hu.unideb.inf.rft.neuban.persistence.configuration.PersistenceConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DataJpaTest
@Transactional
@SpringBootTest(classes = PersistenceConfiguration.class)
@ActiveProfiles("integration")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JPARepositoryTest {
}
