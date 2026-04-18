package team10.group_app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import team10.group_app.domain.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByActive(Boolean active);

    List<User> findByRole(String role);
}
