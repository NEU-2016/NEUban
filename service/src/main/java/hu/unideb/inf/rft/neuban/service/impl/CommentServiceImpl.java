package hu.unideb.inf.rft.neuban.service.impl;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataGetServiceBeanNameProvider.SINGLE_BOARD_DATA_GET_SERVICE;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataUpdateServiceBeanNameProvider.SINGLE_BOARD_DATA_UPDATE_SERVICE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import hu.unideb.inf.rft.neuban.persistence.repositories.CommentRepository;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.CommentDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CommentNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.CommentService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private UserService userService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CardService cardService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Qualifier(SINGLE_BOARD_DATA_GET_SERVICE)
	private SingleDataGetService<CommentDto, Long> singleCommentDataGetService;

	@Autowired
	@Qualifier(SINGLE_BOARD_DATA_UPDATE_SERVICE)
	private SingleDataUpdateService<CommentDto> singleCommentDataUpdateService;

	@Override
	public Optional<CommentDto> get(Long commentId) {
		return this.singleCommentDataGetService.get(commentId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<CommentDto> getAll(final Long cardId) {

		Assert.notNull(cardId);

		final Optional<CardDto> cardDtoOptional = this.cardService.get(cardId);

		if (cardDtoOptional.isPresent()) {
			return cardDtoOptional.get().getComments();
		}
		return Lists.newArrayList();
	}

	@Override
	public void update(CommentDto commentDto) throws DataNotFoundException {
		singleCommentDataUpdateService.update(commentDto);

	}

	@Transactional
	@Override
	public void remove(final Long commentId) throws CommentNotFoundException {

		Assert.notNull(commentId);

		Optional.ofNullable(this.commentRepository.findOne(commentId))
				.orElseThrow(() -> new CommentNotFoundException(String.valueOf(commentId)));

		this.commentRepository.delete(commentId);

	}

	@Transactional
	@Override
	public void addComment(final Long userId, final Long cardId, final String content) throws DataNotFoundException {

		Assert.notNull(userId);
		Assert.notNull(cardId);
		Assert.notNull(content);

		final Optional<CardDto> cardDto = Optional
				.of(cardService.get(cardId).orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId))));
		final Optional<UserDto> userDto = Optional
				.of(userService.get(userId).orElseThrow(() -> new UserNotFoundException(String.valueOf(userId))));
		final CommentDto commentDto = CommentDto.builder().content(content).userDto(userDto.get())
				.createdDateTime(LocalDateTime.now()).build();

		cardDto.get().getComments().add(commentDto);

		this.cardService.update(cardDto.get());
	}

}
