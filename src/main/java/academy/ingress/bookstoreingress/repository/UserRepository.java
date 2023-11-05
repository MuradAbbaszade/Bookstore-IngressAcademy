package academy.ingress.bookstoreingress.repository;

import academy.ingress.bookstoreingress.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
