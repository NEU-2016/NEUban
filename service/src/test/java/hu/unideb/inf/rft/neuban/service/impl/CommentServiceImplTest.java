package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.CommentEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.CommentRepository;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.CommentDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CommentNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.impl.shared.SingleDataUpdateServiceImpl;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private UserService userService;
    @Mock
    private CardService cardService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private DataListConverter<CommentEntity, CommentDto> commentDataListConverter;
    @Mock
    private SingleDataGetService<CommentDto, Long> singleCommentDataGetService;
    @Mock
    private SingleDataUpdateServiceImpl<CommentEntity, CommentDto, Long, CommentNotFoundException> singleCommentDataUpdateService;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private static final long COMMENT_ID = 1L;
    private static final long NEWER_COMMENT_ID = 2L;
    private static final long CARD_ID = 1L;
    private static final long USER_ID = 1L;
    private static final String CARD_TITLE = "Title";
    private static final String COMMENT_CONTENT = "Just a random comment passing by";
    private static final LocalDateTime COMMENT_DATE = LocalDateTime.of(1994, 8, 19, 0, 0);
    private static final LocalDateTime NEWER_COMMENT_DATE = LocalDateTime.of(1995, 8, 19, 0, 0);
    private static final String CARD_DESCRIPTION = "Test Descrtipton";

    private final CommentEntity commentEntity = CommentEntity.builder().id(COMMENT_ID).createdDateTime(COMMENT_DATE)
            .content(COMMENT_CONTENT).build();

    private final CommentDto commentDto = CommentDto.builder().id(COMMENT_ID).createdDateTime(COMMENT_DATE)
            .content(COMMENT_CONTENT).card(null).build();

    private final CommentDto newerCommentDto = CommentDto.builder().id(NEWER_COMMENT_ID)
            .createdDateTime(NEWER_COMMENT_DATE).content(COMMENT_CONTENT).card(null).build();

    private final CardDto cardDto = CardDto.builder().id(CARD_ID).title(CARD_TITLE).comments(Lists.newArrayList())
            .build();

    private final Optional<CardDto> cardDtoWithTwoCommentsForGetAll = Optional.of(CardDto.builder().id(CARD_ID)
            .title(CARD_TITLE).comments(Lists.newArrayList(commentDto, newerCommentDto)).build());

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowIllegalArgumentExceptionWhenParamCommentIdIsNull() {
        // Given
        given(this.singleCommentDataGetService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.commentService.get(null);

        // Then
    }

    @Test
    public void getShouldReturnWithEmptyOptionalWhenCommentDoesNotExist() {
        // Given
        given(this.singleCommentDataGetService.get(COMMENT_ID)).willReturn(Optional.empty());

        // When
        final Optional<CommentDto> result = this.commentService.get(COMMENT_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(this.singleCommentDataGetService).should().get(COMMENT_ID);
        verifyNoMoreInteractions(this.singleCommentDataGetService);
    }

    @Test
    public void getShouldReturnWithNonEmptyOptionalWhenCommentDoesExist() {
        // Given
        given(this.singleCommentDataGetService.get(COMMENT_ID)).willReturn(Optional.of(commentDto));

        // When
        final Optional<CommentDto> result = this.commentService.get(COMMENT_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(commentDto));

        then(this.singleCommentDataGetService).should().get(COMMENT_ID);
        verifyNoMoreInteractions(this.singleCommentDataGetService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllShouldThrowIllegalArgumentExceptionWhenParamCardIdIsNull() throws CommentNotFoundException {
        // When
        commentService.getAll(null);
    }

    @Test
    public void getAllShouldReturnEmptyListWhenCardDoesNotExist() throws CardNotFoundException {
        // Given
        given(this.cardService.get(CARD_ID)).willReturn(Optional.empty());

        // When
        final List<CommentDto> result = this.commentService.getAll(CARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        then(this.cardService).should().get(CARD_ID);
        verifyNoMoreInteractions(this.cardService);
    }

    @Test
    public void getAllShouldReturnOrderedCommentsByDateWhenCardExists() {
        // Given
        given(this.cardService.get(CARD_ID)).willReturn(cardDtoWithTwoCommentsForGetAll);
        given(this.commentDataListConverter.convertToTargets(
                commentRepository.findByCardIdOrderByCreatedTimeDesc(cardDtoWithTwoCommentsForGetAll.get().getId())))
                .willReturn(Arrays.asList(newerCommentDto, commentDto));
        // When

        final List<CommentDto> result = this.commentService.getAll(CARD_ID);

        // Then
        assertThat(result, notNullValue());

        assertThat(result.size(), equalTo(2));
        assertThat(result, equalTo(Lists.newArrayList(newerCommentDto, commentDto)));

        then(this.cardService).should().get(CARD_ID);
        verifyNoMoreInteractions(this.cardService);

    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgumentExceptionWhenParamCommentDtoIsNull() throws DataNotFoundException {
        // Given
        doThrow(IllegalArgumentException.class).when(this.singleCommentDataUpdateService).update(null);

        // When
        this.commentService.update(null);

        // Then
    }

    @Test(expected = CommentNotFoundException.class)
    public void updateShouldThrowCommentNotFoundExceptionWhenCommentIdIsNull() throws DataNotFoundException {
        // Given
        final CommentDto commentDto = CommentDto.builder().id(null).build();

        doThrow(CommentNotFoundException.class).when(this.singleCommentDataUpdateService).update(commentDto);

        // When
        this.commentService.update(commentDto);

        // Then
    }

    @Test
    public void updateShouldBeSuccessfulUpdatingWhenCommentExists() throws DataNotFoundException {
        // Given
        doNothing().when(this.singleCommentDataUpdateService).update(commentDto);

        // When
        this.commentService.update(commentDto);

        // Then
        then(this.singleCommentDataUpdateService).should().update(commentDto);
        verifyNoMoreInteractions(this.singleCommentDataUpdateService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowIllegalArgumentExceptionWhenParamCommentIdIsNull() throws CommentNotFoundException {
        // When
        commentService.remove(null);
    }

    @Test(expected = CommentNotFoundException.class)
    public void removeShouldThrowCommentNotFoundExceptionWhenParamCommentDoesNotExists()
            throws CommentNotFoundException {

        // Given

        given(this.commentRepository.findOne(COMMENT_ID)).willReturn(null);

        // When
        commentService.remove(COMMENT_ID);
    }

    @Test
    public void removeShouldBeSuccessfulDeletingWhenCommentExists() throws CommentNotFoundException {
        // Given
        given(this.commentRepository.findOne(COMMENT_ID)).willReturn(commentEntity);

        // When
        this.commentService.remove(COMMENT_ID);

        // Then
        then(this.commentRepository).should().findOne(COMMENT_ID);
        then(this.commentRepository).should().delete(COMMENT_ID);
        verifyNoMoreInteractions(this.commentRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCommentShouldThrowIllegalArgumentExceptionWhenParamUserIdIsNull() throws DataNotFoundException {
        // When
        commentService.addComment(null, CARD_ID, COMMENT_CONTENT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCommentShouldThrowIllegalArgumentExceptionWhenParamCardIdIsNull() throws DataNotFoundException {
        // When
        commentService.addComment(USER_ID, null, COMMENT_CONTENT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCommentShouldThrowIllegalArgumentExceptionWhenParamContentIsNull() throws DataNotFoundException {
        // When
        commentService.addComment(USER_ID, CARD_ID, null);
    }

    @Test
    public void addCommentShouldThrowCommentNotFoundExceptionWhenParamCommentDoesNotExists()
            throws DataNotFoundException {

        // Given
        given(this.cardService.get(CARD_ID)).willReturn(Optional.empty());
        expectedException.expect(CardNotFoundException.class);

        // When
        commentService.addComment(USER_ID, CARD_ID, COMMENT_CONTENT);
    }

    @Test
    public void addCommentShouldThrowUserNotFoundExceptionWhenParamCommentDoesNotExists() throws DataNotFoundException {

        // Given
        given(this.cardService.get(CARD_ID)).willReturn(Optional.of(cardDto));
        given(this.userService.get(USER_ID)).willReturn(Optional.empty());
        expectedException.expect(UserNotFoundException.class);

        // When
        commentService.addComment(USER_ID, CARD_ID, COMMENT_CONTENT);
    }

    @Test
    public void addCommentTest() throws DataNotFoundException {
        UserDto userDto = UserDto.builder().id(USER_ID).userName("USERNAME").password("PASSWORD")
                .boards(com.google.common.collect.Lists.newArrayList()).build();
        CardDto CardDtoBeforeSave = CardDto.builder().id(CARD_ID).comments(Lists.newArrayList())
                .description(CARD_DESCRIPTION).build();

        CardDto CardDtoAfterSave = CardDto.builder().id(CARD_ID).comments(Lists.newArrayList(commentDto))
                .description(CARD_DESCRIPTION).build();

        // Given
        given(this.cardService.get(CARD_ID)).willReturn(Optional.of(CardDtoBeforeSave), Optional.of(CardDtoAfterSave));
        given(this.userService.get(USER_ID)).willReturn(Optional.of(userDto));

        // When

        final Optional<CardDto> actualCardDtoBeforeSave = this.cardService.get(CARD_ID);

        this.commentService.addComment(USER_ID, CARD_ID, COMMENT_CONTENT);

        final Optional<CardDto> actualCardDtoAfterSave = this.cardService.get(CARD_ID);

        // Then
        assertThat(actualCardDtoAfterSave.get(), equalTo(CardDtoAfterSave));
        assertThat(actualCardDtoBeforeSave.get(), equalTo(CardDtoBeforeSave));
    }

}
