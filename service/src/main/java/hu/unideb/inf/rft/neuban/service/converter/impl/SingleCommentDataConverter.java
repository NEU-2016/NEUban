package hu.unideb.inf.rft.neuban.service.converter.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hu.unideb.inf.rft.neuban.persistence.entities.CommentEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.CommentDto;

@Component
public class SingleCommentDataConverter implements SingleDataConverter<CommentEntity, CommentDto> {

	@Autowired
	@Qualifier("singleUserDataConverter")
	private SingleUserDataConverter singleUserDataConverter;

	@Autowired
	@Qualifier("singleCardDataConverter")
	private SingleCardDataConverter singleCardDataConverter;

	@Override
	public Optional<CommentEntity> convertToSource(final CommentDto commentDto) {
		if (commentDto == null) {
			return Optional.empty();
		}
		return Optional.of(CommentEntity.builder().id(commentDto.getId()).content(commentDto.getContent())
				.createdDateTime(commentDto.getCreatedDateTime())
				.user(this.singleUserDataConverter.convertToSource(commentDto.getUser()).get())
				.card(this.singleCardDataConverter.convertToSource(commentDto.getCard()).get()).build());
	}

	@Override
	public Optional<CommentDto> convertToTarget(CommentEntity commentEntity) {
		if (commentEntity == null) {
			return Optional.empty();
		}
		return Optional.of(CommentDto.builder().id(commentEntity.getId()).content(commentEntity.getContent())
				.createdDateTime(commentEntity.getCreatedTime())
				.user(this.singleUserDataConverter.convertToTarget(commentEntity.getUser()).get())
				.card(this.singleCardDataConverter.convertToTarget(commentEntity.getCard()).get()).build());
	}
}
