package hu.unideb.inf.rft.neuban.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUserName(String name);
}