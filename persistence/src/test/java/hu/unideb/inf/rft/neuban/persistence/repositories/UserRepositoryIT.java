package hu.unideb.inf.rft.neuban.persistence.repositories;

import hu.unideb.inf.rft.neuban.persistence.annotations.JPARepositoryTest;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@JPARepositoryTest
@Sql(scripts = "classpath:sql/data-insert-user.sql")
public class UserRepositoryIT {

    private static final long ADMIN_ID = 1L;
    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String USER_NAME_NON_EXISTENT = "non-existent username";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUserNameShouldReturnNullWhenParamUserNameIsNull() {
        // Given

        // When
        final UserEntity actualUserEntity = this.userRepository.findByUserName(null);

        // Then
        assertThat(actualUserEntity, nullValue());
    }

    @Test
    public void findByUserNameShouldReturnNullWhenParamUserNameNotExists() {
        // Given

        // When
        final UserEntity actualUserEntity = this.userRepository.findByUserName(USER_NAME_NON_EXISTENT);

        // Then
        assertThat(actualUserEntity, nullValue());
    }

    @Test
    public void findByUserNameShouldReturnAnExistingUserEntityWhenParamUserNameExists() {
        // Given

        // When
        final UserEntity actualUserEntity = this.userRepository.findByUserName(ADMIN_USER_NAME);

        // Then
        assertThat(actualUserEntity, notNullValue());

        assertThat(actualUserEntity.getId(), notNullValue());
        assertThat(actualUserEntity.getId(), equalTo(ADMIN_ID));

        assertThat(actualUserEntity.getUserName(), notNullValue());
        assertThat(actualUserEntity.getUserName(), equalTo(ADMIN_USER_NAME));

        assertThat(actualUserEntity.getEmail(), notNullValue());
        assertThat(actualUserEntity.getEmail(), equalTo(ADMIN_EMAIL));

        assertThat(actualUserEntity.getPassword(), notNullValue());
        assertThat(actualUserEntity.getPassword(), equalTo(ADMIN_PASSWORD));

        assertThat(actualUserEntity.getRole(), notNullValue());
        assertThat(actualUserEntity.getRole(), equalTo(Role.ADMIN));

        assertThat(actualUserEntity.getBoards(), notNullValue());
        assertThat(actualUserEntity.getBoards().isEmpty(), is(false));
    }
}