package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.UserAlreadyExistsOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long ADMIN_ID = 1L;
    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String USER_NAME_NON_EXISTENT = "non-existent username";
    //private static final String PASSWORD_HASH = "$GDSJDT6464EGDFG5353";


    private static final long COLUMN_ID = 1L;
    private static final long FIRST_CARD_ID = 1L;
    private static final long SECOND_CARD_ID = 2L;
    private static final long THIRD_CARD_ID = 3L;
    private static final long FOURTH_CARD_ID = 4L;
    private static final String CARD_TITLE = "Card title";

    private static final long FIRST_USER_ID = 1L;
    private static final long SECOND_USER_ID = 2L;
    private static final long THIRD_USER_ID = 3L;
    private static final long FOURTH_USER_ID = 4L;
    private static final String USERNAME = "Username";
    private static final String PASSWORD_HASH = "PasswordHash";

    private final CardEntity firstCardEntity = CardEntity.builder()
            .id(FIRST_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto firstCardDto = CardDto.builder()
            .id(FIRST_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto secondCardDto = CardDto.builder()
            .id(SECOND_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto thirdCardDto = CardDto.builder()
            .id(THIRD_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto fourthCardDto = CardDto.builder()
            .id(FOURTH_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final UserDto firstUserDto = UserDto.builder()
            .id(FIRST_USER_ID)
            .userName(USERNAME)
            .password(PASSWORD_HASH)
            .build();

    private final UserDto secondUserDto = UserDto.builder()
            .id(SECOND_USER_ID)
            .userName(USERNAME)
            .password(PASSWORD_HASH)
            .build();

    private final UserDto thirdUserDto = UserDto.builder()
            .id(THIRD_USER_ID)
            .userName(USERNAME)
            .password(PASSWORD_HASH)
            .build();

    private final UserDto fourthUserDto = UserDto.builder()
            .id(FOURTH_USER_ID)
            .userName(USERNAME)
            .password(PASSWORD_HASH)
            .build();

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private SingleDataGetService<UserDto, Long> singleUserDataGetService;
    @Mock
    private SingleDataUpdateService<UserDto> singleUserDataUpdateService;
    @Mock
    private CardService cardService;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    final UserDto userDto = UserDto.builder()
            .id(ADMIN_ID)
            .userName(ADMIN_USER_NAME)
            .password(ADMIN_PASSWORD)
            .role(Role.ADMIN)
            .build();
    final UserEntity userEntity = UserEntity.builder().
            id(ADMIN_ID)
            .userName(ADMIN_USER_NAME)
            .password(ADMIN_PASSWORD)
            .role(Role.ADMIN)
            .build();

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowIllegalArgumentExceptionWheParamUserIdIsNull() {
        // Given
        given(this.singleUserDataGetService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.userService.get(null);

        // Then
    }

    @Test
    public void getShouldReturnWithEmptyOptionalWhenUserDoesNotExist() {
        // Given
        given(this.singleUserDataGetService.get(ADMIN_ID)).willReturn(Optional.empty());

        // When
        final Optional<UserDto> result = this.userService.get(ADMIN_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(this.singleUserDataGetService).should().get(ADMIN_ID);
        verifyNoMoreInteractions(this.singleUserDataGetService);
    }

    @Test
    public void getShouldReturnWithNonEmptyOptionalWhenUserDoesExist() {
        // Given
        given(this.singleUserDataGetService.get(ADMIN_ID)).willReturn(Optional.of(userDto));

        // When
        final Optional<UserDto> result = this.userService.get(ADMIN_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(userDto));

        then(this.singleUserDataGetService).should().get(ADMIN_ID);
        verifyNoMoreInteractions(this.singleUserDataGetService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByUserNameShouldThrowIllegalArgumentExceptionWhenParamUserNameIsNull() {
        // Given

        // When
        this.userService.getByUserName(null);

        // Then
    }

    @Test
    public void getByUserNameShouldReturnNullWhenUserWithTheParamUserNameDoesNotExist() {
        // Given
        given(this.userRepository.findByUserName(USER_NAME_NON_EXISTENT)).willReturn(null);

        // When
        final UserDto actualUserDto = this.userService.getByUserName(USER_NAME_NON_EXISTENT).orElse(null);

        // Then
        assertThat(actualUserDto, nullValue());

        then(this.userRepository).should().findByUserName(USER_NAME_NON_EXISTENT);
        verifyZeroInteractions(this.modelMapper);
    }

    @Test
    public void getByUserNameShouldReturnAnExistingUserDtoWhenParamUserNameExists() {
        // Given
        given(this.userRepository.findByUserName(ADMIN_USER_NAME)).willReturn(userEntity);
        given(this.modelMapper.map(userEntity, UserDto.class)).willReturn(userDto);

        // When
        final UserDto actualUserDto = this.userService.getByUserName(ADMIN_USER_NAME).orElse(null);

        // Then
        assertThat(actualUserDto, notNullValue());
        assertThat(actualUserDto, equalTo(userDto));

        then(this.userRepository).should().findByUserName(ADMIN_USER_NAME);
        then(this.modelMapper).should().map(userEntity, UserDto.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowIllegalArgumentExceptionWhenParamUserDtoIsNull() {
        // Given

        // When
        this.userService.create(null);

        // Then
    }

    @Test(expected = IllegalStateException.class)
    public void createShouldThrowIllegalStateExceptionWhenParamUserDtoPasswordFieldExistentWithNullValue() {
        // Given
        final UserDto userDto = UserDto.builder().password(null).build();

        // When
        this.userService.create(userDto);

        // Then
    }

    @Test
    public void createShouldBeSuccessfulSaveWhenParamUserDtoIsValid() {
        // Given
        final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        given(this.modelMapper.map(userDto, UserEntity.class)).willReturn(userEntity);
        given(this.bCryptPasswordEncoder.encode(ADMIN_PASSWORD)).willReturn(PASSWORD_HASH);
        given(this.userRepository.saveAndFlush(any(UserEntity.class))).willReturn(userEntity);

        // When
        this.userService.create(userDto);

        // Then
        verify(this.userRepository).saveAndFlush(captor.capture());

        assertThat(userEntity.getPassword(), notNullValue());
        assertThat(userEntity.getPassword(), equalTo(PASSWORD_HASH));

        then(this.modelMapper).should().map(userDto, UserEntity.class);
        then(this.bCryptPasswordEncoder).should().encode(ADMIN_PASSWORD);
        then(this.userRepository).should().saveAndFlush(userEntity);
        verifyNoMoreInteractions(this.modelMapper, this.bCryptPasswordEncoder, this.userRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToCardShouldThrowIllegalArgumentExceptionWhenParameterUserIdIsNull() throws UserAlreadyExistsOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.userService.addUserToCard(null, FIRST_CARD_ID);

        // Then
    }

    @Test(expected = UserNotFoundException.class)
    public void addUserToCardShouldThrowUserNotFoundExceptionWhenUserDoesNotExist() throws UserAlreadyExistsOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.empty());

        // When
        this.userService.addUserToCard(FIRST_USER_ID, FIRST_CARD_ID);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToCardShouldThrowIllegalArgumentExceptionWhenParameterCardIdIsNull() throws UserAlreadyExistsOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.userService.addUserToCard(FIRST_USER_ID, null);

        // Then
    }

    @Test(expected = CardNotFoundException.class)
    public void addUserToCardShouldThrowCardNotFoundExceptionWhenCardDoesNotExist() throws UserAlreadyExistsOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(FIRST_CARD_ID)).willReturn(Optional.empty());

        // When
        this.userService.addUserToCard(FIRST_USER_ID, FIRST_CARD_ID);

        // Then
    }

    @Test(expected = UserAlreadyExistsOnCardException.class)
    public void addUserToCardShouldBeNotSuccessfulAddingWhenUserAlreadyExistsOnCard() throws UserAlreadyExistsOnCardException, DataNotFoundException {
        // Given
        firstUserDto.setCards(Collections.singletonList(firstCardDto));

        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(FIRST_CARD_ID)).willReturn(Optional.of(firstCardDto));

        // When
        this.userService.addUserToCard(FIRST_USER_ID, FIRST_CARD_ID);

        // Then
    }

    @Test
    public void addUserToCardShouldBeSuccessfulAddingWhenUserDoesNotExistOnCard() throws UserAlreadyExistsOnCardException, DataNotFoundException {
        // Given
        firstUserDto.setCards(Lists.newArrayList(firstCardDto, secondCardDto, thirdCardDto));

        given(this.cardService.get(FOURTH_CARD_ID)).willReturn(Optional.of(fourthCardDto));
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        doNothing().when(this.userService).update(firstUserDto);

        final ArgumentCaptor<UserDto> userDtoArgumentCaptor = ArgumentCaptor.forClass(UserDto.class);

        // When
        this.userService.addUserToCard(FIRST_USER_ID, FOURTH_CARD_ID);

        verify(this.userService).update(userDtoArgumentCaptor.capture());

        // Then
        assertThat(userDtoArgumentCaptor.getValue(), notNullValue());
        assertThat(userDtoArgumentCaptor.getValue().getCards(), notNullValue());
        assertThat(userDtoArgumentCaptor.getValue().getCards().size(), equalTo(4));
        assertThat(userDtoArgumentCaptor.getValue().getCards().contains(fourthCardDto), is(true));

        then(this.userService).should(times(2)).get(FIRST_USER_ID);
        then(this.cardService).should().get(FOURTH_CARD_ID);
        then(this.userService).should().update(userDtoArgumentCaptor.getValue());

        verifyNoMoreInteractions(this.cardService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserFromCardShouldThrowIllegalArgumentExceptionWhenParameterUserIdIsNull() throws UserNotFoundOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.userService.removeUserFromCard(null, FIRST_CARD_ID);

        // Then
    }

    @Test(expected = UserNotFoundException.class)
    public void removeUserFromCardShouldThrowUserNotFoundExceptionWhenUserDoesNotExist() throws UserNotFoundOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.empty());

        // When
        this.userService.removeUserFromCard(FIRST_USER_ID, FIRST_CARD_ID);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserFromCardShouldThrowIllegalArgumentExceptionWhenParameterCardIdIsNull() throws UserNotFoundOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.userService.removeUserFromCard(FIRST_USER_ID, null);

        // Then
    }

    @Test(expected = CardNotFoundException.class)
    public void removeUserFromCardShouldThrowCardNotFoundExceptionWhenCardDoesNotExist() throws UserNotFoundOnCardException, DataNotFoundException {
        // Given
        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(FIRST_CARD_ID)).willReturn(Optional.empty());

        // When
        this.userService.removeUserFromCard(FIRST_USER_ID, FIRST_CARD_ID);

        // Then
    }

    @Test(expected = UserNotFoundOnCardException.class)
    public void removeUserFromCardShouldBeNotSuccessfulRemovingWhenUserDoesNotExistsOnCard() throws UserNotFoundOnCardException, DataNotFoundException {
        // Given
        firstUserDto.setCards(Collections.emptyList());

        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(FIRST_CARD_ID)).willReturn(Optional.of(firstCardDto));

        // When
        this.userService.removeUserFromCard(FIRST_USER_ID, FIRST_CARD_ID);

        // Then
    }

    @Test
    public void removeUserFromCardShouldBeSuccessfulRemovingWhenUserDoesExistOnCard() throws UserNotFoundOnCardException, DataNotFoundException {
        // Given
        firstUserDto.setCards(Lists.newArrayList(firstCardDto, secondCardDto, thirdCardDto, fourthCardDto));

        given(this.userService.get(FIRST_USER_ID)).willReturn(Optional.of(firstUserDto));
        given(this.cardService.get(FOURTH_CARD_ID)).willReturn(Optional.of(fourthCardDto));
        doNothing().when(this.userService).update(firstUserDto);

        final ArgumentCaptor<UserDto> userDtoArgumentCaptor = ArgumentCaptor.forClass(UserDto.class);

        // When
        this.userService.removeUserFromCard(FIRST_USER_ID, FOURTH_CARD_ID);

        verify(this.userService).update(userDtoArgumentCaptor.capture());

        // Then
        assertThat(userDtoArgumentCaptor.getValue(), notNullValue());
        assertThat(userDtoArgumentCaptor.getValue().getCards(), notNullValue());
        assertThat(userDtoArgumentCaptor.getValue().getCards().size(), equalTo(3));
        assertThat(userDtoArgumentCaptor.getValue().getCards().contains(fourthUserDto), is(false));

        then(this.userService).should(times(2)).get(FIRST_USER_ID);
        then(this.cardService).should().get(FOURTH_CARD_ID);
        then(this.userService).should().update(userDtoArgumentCaptor.getValue());

        verifyNoMoreInteractions(this.cardService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgumentExceptionWhenParamUserDtoIsNull() throws DataNotFoundException {
        // Given
        doThrow(IllegalArgumentException.class).when(this.singleUserDataUpdateService).update(null);

        // When
        this.userService.update(null);

        // Then
    }

    @Test(expected = UserNotFoundException.class)
    public void updateShouldThrowUserNotFoundExceptionWhenUserIdIsNull() throws DataNotFoundException {
        // Given
        final UserDto userDto = UserDto.builder().id(null).build();

        doThrow(UserNotFoundException.class).when(this.singleUserDataUpdateService).update(userDto);

        // When
        this.userService.update(userDto);

        // Then
    }

    @Test
    public void updateShouldBeSuccessfulUpdatingWhenUserExists() throws DataNotFoundException {
        // Given
        doNothing().when(this.singleUserDataUpdateService).update(firstUserDto);

        // When
        this.userService.update(firstUserDto);

        // Then
        then(this.singleUserDataUpdateService).should().update(firstUserDto);
        verifyNoMoreInteractions(this.singleUserDataUpdateService);
    }
}