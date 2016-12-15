package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentBoardIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataGetServiceBeanNameProvider.SINGLE_BOARD_DATA_GET_SERVICE;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataUpdateServiceBeanNameProvider.SINGLE_BOARD_DATA_UPDATE_SERVICE;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Qualifier(SINGLE_BOARD_DATA_GET_SERVICE)
	private SingleDataGetService<BoardDto, Long> singleBoardDataGetService;

	@Autowired
	@Qualifier(SINGLE_BOARD_DATA_UPDATE_SERVICE)
	private SingleDataUpdateService<BoardDto> singleBoardDataUpdateService;

	@Transactional(readOnly = true)
	@Override
	public Optional<BoardDto> get(final Long boardId) {
		return this.singleBoardDataGetService.get(boardId);
	}

	@Transactional(readOnly = true)
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
	public void update(final BoardDto boardDto) throws DataNotFoundException {
		this.singleBoardDataUpdateService.update(boardDto);
	}

	@Transactional
	@Override
	public void remove(final Long boardId) throws DataNotFoundException {
		Assert.notNull(boardId);

		List<UserDto> userDtos = userService.getAll();

		final BoardEntity boardEntity = Optional.ofNullable(this.boardRepository.findOne(boardId))
				.orElseThrow(() -> new BoardNotFoundException(boardId.toString()));

		final BoardDto boardDto = modelMapper.map(boardEntity, BoardDto.class);
		userDtos = userDtos.stream().filter(userDto -> userDto.getBoards().contains(boardDto))
				.collect(Collectors.toList());
		for (UserDto userDtoIter : userDtos) {
			userDtoIter.getBoards().remove(boardDto);
			userService.update(userDtoIter);
		}
		this.boardRepository.delete(boardId);

	}

	@Transactional
	@Override
	public void removeUserFromBoardByUserIdAndByBoardId(final Long userId, final Long boardId)
			throws NonExistentBoardIdException, RelationNotFoundException, NonExistentUserIdException,
			DataNotFoundException {

		// TODO Refactor needed

		Assert.notNull(userId);
		Assert.notNull(boardId);

		UserDto userDto = this.userService.get(userId).orElseThrow(() -> new NonExistentUserIdException(userId));
		final int sizeOfUserDtoBoards = userDto.getBoards().size();

		BoardDto boardDto = get(boardId).orElseThrow(() -> new NonExistentBoardIdException(boardId));

		if (userDto.getBoards() != null) {
			userDto.setBoards(userDto.getBoards().stream()
					.filter(userBoards -> userBoards.getId().equals(boardDto.getId())).collect(Collectors.toList()));
		}

		if (userDto.getBoards().isEmpty() || Integer.compare(userDto.getBoards().size(), sizeOfUserDtoBoards) != 0) {
			throw new RelationNotFoundException();
		}

		userService.update(userDto);

	}

	@Transactional
	@Override
	public void addUserToBoardByUserIdAndByBoardId(final Long userId, final Long boardId)
			throws NonExistentBoardIdException, NonExistentUserIdException, DataNotFoundException {

		Assert.notNull(userId);
		Assert.notNull(boardId);

		UserDto userDto = this.userService.get(userId).orElseThrow(() -> new NonExistentUserIdException(userId));

		BoardDto boardDto = get(boardId).orElseThrow(() -> new NonExistentBoardIdException(boardId));

		if (userDto.getBoards() != null) {
			userDto.getBoards().add(boardDto);
		} else {
			userDto.setBoards(Lists.newArrayList(boardDto));
		}

		userService.update(userDto);

	}

	@Transactional
	@Override
	public void createBoard(final Long userId, final String title)
			throws NonExistentUserIdException, DataNotFoundException {

		Assert.notNull(userId);
		Assert.notNull(title);

		UserDto userDto = this.userService.get(userId).orElseThrow(() -> new NonExistentUserIdException(userId));

		BoardDto boardDto = BoardDto.builder().title(title).build();

		userDto.getBoards().add(boardDto);

		userService.update(userDto);
	}

}
