package academy.ingress.bookstoreingress.controller;

import academy.ingress.bookstoreingress.domain.Book;
import academy.ingress.bookstoreingress.domain.Student;
import academy.ingress.bookstoreingress.repository.BookRepository;
import academy.ingress.bookstoreingress.service.BookService;
import academy.ingress.bookstoreingress.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {
    private final BookService bookService;
    private final StudentService studentService;

    @GetMapping("/readers/{book-id}")
    public List<Student> getStudentsByBook(@PathVariable(name = "book-id") Long bookId) {
        return bookService.findById(bookId).getStudentList();
    }

    @GetMapping("/books")
    public List<Book> getBooks(Principal principal) {
        Student student = studentService.findByEmail(principal.getName());
        return student.getBookList();
    }

    @PutMapping("/read/{book-id}")
    public Book readBook(@PathVariable("book-id") Long bookId, Principal principal) {
        Student student = studentService.findByEmail(principal.getName());
        studentService.addBook(student, bookId);
        return bookService.findById(bookId);
    }

    @PostMapping("/subscribe/{author-id}")
    public void subscribe(@PathVariable("author-id") Long authorId, Principal principal) {
        Student student = studentService.findByEmail(principal.getName());
        studentService.subscribe(student.getId(), authorId);
    }

}
