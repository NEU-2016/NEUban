package hu.unideb.inf.rft.neuban.persistence.repositories;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}