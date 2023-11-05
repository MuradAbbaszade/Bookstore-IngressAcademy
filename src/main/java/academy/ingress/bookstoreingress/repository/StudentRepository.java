package academy.ingress.bookstoreingress.repository;

import academy.ingress.bookstoreingress.domain.Student;
import academy.ingress.bookstoreingress.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);

    @Modifying
    @Query(value = "INSERT INTO student_book(student_id,book_id) values(:studentId,:bookId)", nativeQuery = true)
    void addBook(@Param("studentId") Long studentId, @Param("bookId") Long bookId);

    @Modifying
    @Query(value = "INSERT INTO subscribe(student_id,author_id) values(:studentId,:authorId)", nativeQuery = true)
    void subscribe(@Param("studentId") Long studentId, @Param("authorId") Long authorId);

    @Query(value = "SELECT COUNT(*) from subscribe where student_id=:studentId and author_id=:authorId", nativeQuery = true)
    int isSubscribed(@Param("studentId") Long studentId, @Param("authorId") Long authorId);
}
