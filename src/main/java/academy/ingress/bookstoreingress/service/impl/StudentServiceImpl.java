package academy.ingress.bookstoreingress.service.impl;

import academy.ingress.bookstoreingress.domain.Authority;
import academy.ingress.bookstoreingress.domain.Student;
import academy.ingress.bookstoreingress.domain.User;
import academy.ingress.bookstoreingress.dto.StudentSignUpRequest;
import academy.ingress.bookstoreingress.enums.UserAuthority;
import academy.ingress.bookstoreingress.exception.CustomNotFoundException;
import academy.ingress.bookstoreingress.repository.*;
import academy.ingress.bookstoreingress.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional
    public void signUp(StudentSignUpRequest signUpRequest) {
        User user = new User();
        userRepository.findByUsername(signUpRequest.getEmail()).ifPresent(account -> {
            throw new IllegalArgumentException("Email used");
        });
        user.setUsername(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Authority authority = authorityRepository.findByAuthority(UserAuthority.STUDENT.toString()).orElse(
                Authority.builder().authority(UserAuthority.STUDENT.toString()).build()
        );
        authorityRepository.save(authority);
        user.setAuthorities(Set.of(authority));
        Student student = new Student();
        student.setName(signUpRequest.getName());
        student.setUser(user);
        userRepository.save(user);
        studentRepository.save(student);
    }

    @Override
    public Student findByEmail(String email) {
        User user = userRepository.findByUsername(email).orElseThrow(() -> new CustomNotFoundException("User not found"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new CustomNotFoundException("Student not found"));
        return student;
    }

    @Override
    @Transactional
    public void addBook(Student student, Long bookId) {
        bookRepository.findById(bookId).orElseThrow(() -> new CustomNotFoundException("Book not found"));
        int a = bookRepository.isAdded(student.getId(), bookId);
        if (a == 0) studentRepository.addBook(student.getId(), bookId);
        else log.info("{} book already added to {} student", bookId, student.getId());
    }

    @Override
    @Transactional
    public void subscribe(Long studentId, Long authorId) {
        authorityRepository.findById(authorId).orElseThrow(() -> new CustomNotFoundException("Author not found"));
        int a = studentRepository.isSubscribed(studentId, authorId);
        if (a == 0) studentRepository.subscribe(studentId, authorId);
        else log.info("{} student already subscribed to {} author", studentId, authorId);
    }
}
