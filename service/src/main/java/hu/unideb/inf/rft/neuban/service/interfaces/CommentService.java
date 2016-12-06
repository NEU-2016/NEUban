package hu.unideb.inf.rft.neuban.service.interfaces;

import java.util.List;
import java.util.Optional;

import hu.unideb.inf.rft.neuban.service.domain.CommentDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.CommentNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundException;

public interface CommentService {

	public Optional<CommentDto> get(final Long commentId);

	List<CommentDto> getAll(Long cardId);

	//void update(CommentDto commentDto) throws CommentNotFoundException; // Not necessary now

	void remove(Long commentId) throws CommentNotFoundException;

	void addComment(Long userId, Long cardId, String content) throws UserNotFoundException, CardNotFoundException;

}
