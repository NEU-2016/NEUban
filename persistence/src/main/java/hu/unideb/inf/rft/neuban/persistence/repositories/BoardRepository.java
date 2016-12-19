package hu.unideb.inf.rft.neuban.persistence.repositories;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	BoardEntity findParentBoardbyColumnId(@Param("columnId") Long columnId);
}