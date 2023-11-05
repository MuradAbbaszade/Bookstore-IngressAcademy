package academy.ingress.bookstoreingress.repository;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.domain.Student;
import academy.ingress.bookstoreingress.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUser(User user);
    @Query(value = "SELECT student.id FROM student " +
            "INNER JOIN subscribe AS s ON s.student_id = student.id " +
            "WHERE s.author_id = :authorId", nativeQuery = true)
    List<Long> getSubscribedStudents(@Param("authorId") Long authorId);


}
