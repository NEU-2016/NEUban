package hu.unideb.inf.rft.neuban.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.unideb.inf.rft.neuban.persistence.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUserName(String name) ;

	User findUserById(Long id);
}