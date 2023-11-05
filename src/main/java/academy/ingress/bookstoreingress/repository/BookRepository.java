package academy.ingress.bookstoreingress.repository;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT COUNT(*) from student_book where student_id=:studentId and book_id=:bookId", nativeQuery = true)
    int isAdded(@Param("studentId") Long studentId, @Param("bookId") Long bookId);
}
