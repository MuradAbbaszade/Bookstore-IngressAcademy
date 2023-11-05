package academy.ingress.bookstoreingress.service;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.domain.Book;
import academy.ingress.bookstoreingress.dto.BookCreateRequest;
import org.springframework.stereotype.Service;


@Service
public interface BookService {
    Book findById(Long bookId);
    void deleteByAuthorAndId(Author author,Long id);
    void createBook(BookCreateRequest bookCreateRequest, Author author) throws IllegalArgumentException;
}
