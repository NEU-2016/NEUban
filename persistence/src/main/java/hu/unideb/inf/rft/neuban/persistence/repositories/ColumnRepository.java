package hu.unideb.inf.rft.neuban.persistence.repositories;

import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
	ColumnEntity findParentColumnByCardId(@Param("cardId") Long cardId);
}
