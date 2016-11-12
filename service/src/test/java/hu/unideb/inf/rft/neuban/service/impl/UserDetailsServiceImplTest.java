package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Sets;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    private static final String USERNAME_NON_EXISTENT = "unknown";
    private static final String USERNAME_ADMIN = "admin";
    private static final String USER_PASSWORD = "password";
    private static final String USER_ROLE_AS_STRING = "USER";
    private static final Role USER_ROLE = Role.USER;
    private static final String USERNAME_NOT_FOUND_ERROR_MESSAGE = "Username not found: unknown";

    private final Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet(new SimpleGrantedAuthority(USER_ROLE_AS_STRING));

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void loadUserByUsernameShouldThrowIllegalArgumentExceptionWhenParamUsernameIsNull() {
        // Given

        // When
        this.userDetailsService.loadUserByUsername(null);

        // Then
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUsernameDoesNotExist() {
        // Given
        given(this.userService.getByUserName(USERNAME_NON_EXISTENT)).willReturn(Optional.empty());

        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(USERNAME_NOT_FOUND_ERROR_MESSAGE);

        // When
        this.userDetailsService.loadUserByUsername(USERNAME_NON_EXISTENT);

        // Then
    }

    @Test
    public void loadUserByUsernameShouldReturnAnExistingUser() {
        // Given
        final UserDto userDto = UserDto.builder().userName(USERNAME_ADMIN).password(USER_PASSWORD).role(USER_ROLE).build();
        final UserDetails expectedUser = new User(USERNAME_ADMIN, USER_PASSWORD, grantedAuthorities);
        given(this.userService.getByUserName(USERNAME_ADMIN)).willReturn(Optional.of(userDto));

        // When
        final UserDetails actualUser = this.userDetailsService.loadUserByUsername(USERNAME_ADMIN);

        // Then
        assertThat(actualUser, equalTo(expectedUser));
        then(this.userService).should().getByUserName(USERNAME_ADMIN);
    }
}
