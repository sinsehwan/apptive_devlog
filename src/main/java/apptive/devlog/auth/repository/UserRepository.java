package apptive.devlog.auth.repository;

import apptive.devlog.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
