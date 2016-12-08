package hu.unideb.inf.rft.neuban.service.exceptions.factory;


import hu.unideb.inf.rft.neuban.service.exceptions.data.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class DataNotFoundExceptionFactoryTest {

    private static final String ERROR_ID = String.valueOf(-1L);
    private static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found: -1";
    private static final String BOARD_NOT_FOUND_ERROR_MESSAGE = "Board not found: -1";
    private static final String COLUMN_NOT_FOUND_ERROR_MESSAGE = "Column not found: -1";
    private static final String CARD_NOT_FOUND_ERROR_MESSAGE = "Card not found: -1";

    @Test
    public void createShouldReturnUserNotFoundExceptionWhenParamClassTypeIsUserNotFoundException() {
        // Given

        // When
        final DataNotFoundException result = DataNotFoundExceptionFactory.create(UserNotFoundException.class, ERROR_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getClass(), equalTo(UserNotFoundException.class));
        assertThat(result.getMessage(), equalTo(USER_NOT_FOUND_ERROR_MESSAGE));
    }

    @Test
    public void createShouldReturnBoardNotFoundExceptionWhenParamClassTypeIsBoardNotFoundException() {
        // Given

        // When
        final DataNotFoundException result = DataNotFoundExceptionFactory.create(BoardNotFoundException.class, ERROR_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getClass(), equalTo(BoardNotFoundException.class));
        assertThat(result.getMessage(), equalTo(BOARD_NOT_FOUND_ERROR_MESSAGE));
    }

    @Test
    public void createShouldReturnColumnNotFoundExceptionWhenParamClassTypeIsColumnNotFoundException() {
        // Given

        // When
        final DataNotFoundException result = DataNotFoundExceptionFactory.create(ColumnNotFoundException.class, ERROR_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getClass(), equalTo(ColumnNotFoundException.class));
        assertThat(result.getMessage(), equalTo(COLUMN_NOT_FOUND_ERROR_MESSAGE));
    }

    @Test
    public void createShouldReturnCardNotFoundExceptionWhenParamClassTypeIsCardNotFoundException() {
        // Given

        // When
        final DataNotFoundException result = DataNotFoundExceptionFactory.create(CardNotFoundException.class, ERROR_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getClass(), equalTo(CardNotFoundException.class));
        assertThat(result.getMessage(), equalTo(CARD_NOT_FOUND_ERROR_MESSAGE));
    }

    @Test
    public void createShouldReturnNullWhenParamClassTypeDoesNotExistInDataNotFoundExceptionFactory() {
        // Given

        // When
        final DataNotFoundException result = DataNotFoundExceptionFactory.create(TestDataNotFoundException.class, ERROR_ID);

        // Then
        assertThat(result, nullValue());
    }


    private static final class TestDataNotFoundException extends DataNotFoundException {

        private static final long serialVersionUID = -553451140625592404L;

        public TestDataNotFoundException(final String message) {
            super(message);
        }
    }
}
