package hu.unideb.inf.rft.neuban.service.interfaces;

import java.util.List;

import hu.unideb.inf.rft.neuban.service.domain.CommentDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CommentNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

public interface CommentService extends SingleDataGetService<CommentDto, Long>, SingleDataUpdateService<CommentDto> {

	List<CommentDto> getAll(Long cardId);

	void remove(Long commentId) throws CommentNotFoundException;

	void addComment(Long userId, Long cardId, String content) throws DataNotFoundException;

}
