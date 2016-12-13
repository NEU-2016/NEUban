package hu.unideb.inf.rft.neuban.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.unideb.inf.rft.neuban.persistence.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	
	List<CommentEntity> findByCardIdOrderByCreatedTimeDesc(Long cardId);
	
}
