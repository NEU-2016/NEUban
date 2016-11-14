package hu.unideb.inf.rft.neuban.persistence.repositories;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String name);
}