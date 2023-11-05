package academy.ingress.bookstoreingress.controller;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.domain.Book;
import academy.ingress.bookstoreingress.dto.BookCreateRequest;
import academy.ingress.bookstoreingress.service.AuthorService;
import academy.ingress.bookstoreingress.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    @DeleteMapping("/delete-book/{book-id}")
    public void deleteBook(Principal principal, @PathVariable("book-id") Long bookId) {
        Author author = authorService.findByEmail(principal.getName());
        bookService.deleteByAuthorAndId(author, bookId);
    }

    @PostMapping("/book")
    public void createBook(@RequestBody BookCreateRequest bookCreateRequest, Principal principal) throws IllegalArgumentException {
        Author author = authorService.findByEmail(principal.getName());
        bookService.createBook(bookCreateRequest, author);
    }

}
