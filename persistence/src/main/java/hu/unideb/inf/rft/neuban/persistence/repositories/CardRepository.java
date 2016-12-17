package hu.unideb.inf.rft.neuban.persistence.repositories;


import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
	
	List<CardEntity> findByTitleContaining(String searchText);
}
