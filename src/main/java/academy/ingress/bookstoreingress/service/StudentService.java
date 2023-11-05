package academy.ingress.bookstoreingress.service;

import academy.ingress.bookstoreingress.domain.Student;
import academy.ingress.bookstoreingress.dto.StudentSignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    void signUp(StudentSignUpRequest signUpRequest);

    Student findByEmail(String email);

    void addBook(Student student, Long bookId);

    void subscribe(Long studentId, Long authorId);
}
