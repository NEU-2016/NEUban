package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;

import java.util.Optional;

public interface BoardService {

    Optional<BoardDto> get(Long boardId);

    Long update(BoardDto boardDto) throws BoardNotFoundException;
}
