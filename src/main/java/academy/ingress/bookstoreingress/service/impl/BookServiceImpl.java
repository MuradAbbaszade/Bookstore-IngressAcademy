package academy.ingress.bookstoreingress.service.impl;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.domain.Book;
import academy.ingress.bookstoreingress.domain.Student;
import academy.ingress.bookstoreingress.dto.BookCreateRequest;
import academy.ingress.bookstoreingress.exception.CustomNotFoundException;
import academy.ingress.bookstoreingress.mail.MailSenderService;
import academy.ingress.bookstoreingress.repository.AuthorRepository;
import academy.ingress.bookstoreingress.repository.BookRepository;
import academy.ingress.bookstoreingress.repository.StudentRepository;
import academy.ingress.bookstoreingress.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MailSenderService mailSenderService;
    private final StudentRepository studentRepository;

    @Override
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new CustomNotFoundException("Book not found"));
    }

    @Override
    public void deleteByAuthorAndId(Author author, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Book not found"));
        if (author.getBookList().contains(book)) bookRepository.deleteById(id);
        else throw new IllegalArgumentException("You can't delete this book");
    }

    @Override
    public void createBook(BookCreateRequest bookCreateRequest, Author author) throws IllegalArgumentException {
        Book book = new Book();
        book.setName(bookCreateRequest.getName());
        book.setAuthor(author);
        List<Long> studentList = authorRepository.getSubscribedStudents(author.getId());
        String bookInfo = "Book:" + book.getName() + "\n" +
                "Author:" + author.getName();
        for (Long studentId : studentList) {
            Student student = studentRepository.findById(studentId).get();
            mailSenderService.sendEmail(student.getUser().getUsername(), "Ingress BookStore Book Notification", bookInfo);
        }
        bookRepository.save(book);
    }
}
