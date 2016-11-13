package hu.unideb.inf.rft.neuban.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;

@Service
public class BoardServiceImpl extends BaseServiceImpl<BoardEntity, BoardDto, Long> implements BoardService {

	private final BoardRepository boardRepository;

	@Autowired
	public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper) {
		super(BoardDto.class, BoardEntity.class, modelMapper, boardRepository);
		this.boardRepository = boardRepository;
	}
}
