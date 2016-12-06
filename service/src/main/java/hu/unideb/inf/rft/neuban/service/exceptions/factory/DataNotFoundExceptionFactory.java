package hu.unideb.inf.rft.neuban.service.exceptions.factory;

import hu.unideb.inf.rft.neuban.service.exceptions.data.*;

public class DataNotFoundExceptionFactory {

    private DataNotFoundExceptionFactory() {
    }

    public static DataNotFoundException create(final Class<? extends DataNotFoundException> exceptionClass, final String exceptionMessage) {
        if (exceptionClass.equals(UserNotFoundException.class)) {
            return new UserNotFoundException(exceptionMessage);
        } else if (exceptionClass.equals(BoardNotFoundException.class)) {
            return new BoardNotFoundException(exceptionMessage);
        } else if (exceptionClass.equals(ColumnNotFoundException.class)) {
            return new ColumnNotFoundException(exceptionMessage);
        } else if (exceptionClass.equals(CardNotFoundException.class)) {
            return new CardNotFoundException(exceptionMessage);
        }
        return null;
    }
}
