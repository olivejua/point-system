package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByName(String name);
}
