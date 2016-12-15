package hu.unideb.inf.rft.neuban.service.converter.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CommentEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.CommentDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class SingleCommentDataConverterTest {

	private static final long FIRST_ID = 1L;
	private static final String CONTENT = "Some Content";
	private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.of(1994, 8, 19, 06, 30);

	@InjectMocks
	private SingleCommentDataConverter singleCommentDataConverter;

	@Mock
	private SingleUserDataConverter singleUserDataConverter;

	@Mock
	private SingleCardDataConverter singleCardDataConverter;

	@Mock
	private DataListConverter<CommentEntity, CommentDto> commentDataListConverter;

	private final UserDto userDto = UserDto.builder().id(FIRST_ID).build();

	private final UserEntity userEntity = UserEntity.builder().id(FIRST_ID).build();

	private final CardDto cardDto = CardDto.builder().id(FIRST_ID).build();

	private final CardEntity cardEntity = CardEntity.builder().id(FIRST_ID).build();

	private final CommentDto commentDto = CommentDto.builder().id(FIRST_ID).content(CONTENT)
			.createdDateTime(CREATED_DATE_TIME).user(userDto).card(cardDto).build();

	private final CommentEntity commentEntity = CommentEntity.builder().id(FIRST_ID).content(CONTENT)
			.createdDateTime(CREATED_DATE_TIME).user(userEntity).card(cardEntity).build();

	@Test
	public void convertToSourceShouldReturnEmptyOptionalWhenParameterCommentDtoIsNull() {
		// Given

		// When
		final Optional<CommentEntity> result = this.singleCommentDataConverter.convertToSource(null);

		// Then
		assertThat(result, notNullValue());
		assertThat(result.isPresent(), is(false));
	}

	@Test
	public void convertToSourceShouldReturnOptionalWithValueWhenParameterCommentDtoIsNonNull() {
		// Given

		given(this.singleUserDataConverter.convertToSource(commentDto.getUser())).willReturn(Optional.of(userEntity));
		given(this.singleCardDataConverter.convertToSource(commentDto.getCard())).willReturn(Optional.of(cardEntity));

		// When
		final Optional<CommentEntity> result = this.singleCommentDataConverter.convertToSource(commentDto);

		// Then
		assertThat(result, notNullValue());
		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), equalTo(commentEntity));
	}

	@Test
	public void convertToTargetShouldReturnEmptyOptionalWhenParameterCommentEntityIsNull() {
		// Given

		// When
		final Optional<CommentDto> result = this.singleCommentDataConverter.convertToTarget(null);

		// Then
		assertThat(result, notNullValue());
		assertThat(result.isPresent(), is(false));
	}

	@Test
	public void convertToTargetShouldReturnOptionalWithValueWhenParameterCardEntityIsNonNull() {
		// Given

		given(this.singleUserDataConverter.convertToTarget(commentEntity.getUser())).willReturn(Optional.of(userDto));
		given(this.singleCardDataConverter.convertToTarget(commentEntity.getCard())).willReturn(Optional.of(cardDto));

		// When
		final Optional<CommentDto> result = this.singleCommentDataConverter.convertToTarget(commentEntity);

		// Then
		assertThat(result, notNullValue());
		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), equalTo(commentDto));
	}

}
