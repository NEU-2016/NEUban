package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<BoardDto> get(final Long boardId) {
        Assert.notNull(boardId);

        final Optional<BoardEntity> boardEntityOptional = Optional.ofNullable(this.boardRepository.findOne(boardId));

        if (boardEntityOptional.isPresent()) {
            return Optional.of(modelMapper.map(boardEntityOptional.get(), BoardDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<BoardDto> getAllByUserId(final Long userId) {
        final Optional<UserDto> userDtoOptional = this.userService.get(userId);

        if (userDtoOptional.isPresent()) {
            return userDtoOptional.get().getBoards();
        }
        return Lists.newArrayList();
    }

    @Transactional
    @Override
    public Long update(final BoardDto boardDto) throws BoardNotFoundException {
        Assert.notNull(boardDto);
        Assert.notNull(boardDto.getId());

        final BoardEntity boardEntity = Optional.ofNullable(this.boardRepository.findOne(boardDto.getId()))
                .orElseThrow(() -> new BoardNotFoundException(String.valueOf(boardDto.getId())));

        return this.boardRepository.saveAndFlush(boardEntity).getId();
    }
}
