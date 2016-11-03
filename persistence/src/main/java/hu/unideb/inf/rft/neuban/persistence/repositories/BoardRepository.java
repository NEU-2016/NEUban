package hu.unideb.inf.rft.neuban.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

}