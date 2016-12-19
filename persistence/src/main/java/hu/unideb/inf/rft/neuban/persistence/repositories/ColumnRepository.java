package hu.unideb.inf.rft.neuban.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {

	ColumnEntity findParentColumn(@Param("cardId") Long cardId);
}
