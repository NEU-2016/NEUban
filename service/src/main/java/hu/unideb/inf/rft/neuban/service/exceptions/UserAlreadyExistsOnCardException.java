package hu.unideb.inf.rft.neuban.service.exceptions;

public class UserAlreadyExistsOnCardException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "User already exists on card:";
    private static final String DEFAULT_USER_ID_MESSAGE = "UserID: ";
    private static final String DEFAULT_CARD_ID_MESSAGE = "CardID: ";

    public UserAlreadyExistsOnCardException(String userId, String cardId) {
        super(DEFAULT_ERROR_MESSAGE + "\n" + DEFAULT_USER_ID_MESSAGE + userId + "\n" + DEFAULT_CARD_ID_MESSAGE + cardId);
    }
}