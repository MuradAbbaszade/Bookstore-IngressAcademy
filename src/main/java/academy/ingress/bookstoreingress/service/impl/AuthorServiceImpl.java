package academy.ingress.bookstoreingress.service.impl;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.domain.Authority;
import academy.ingress.bookstoreingress.domain.User;
import academy.ingress.bookstoreingress.dto.AuthorSignUpRequest;
import academy.ingress.bookstoreingress.enums.UserAuthority;
import academy.ingress.bookstoreingress.exception.CustomNotFoundException;
import academy.ingress.bookstoreingress.repository.AuthorRepository;
import academy.ingress.bookstoreingress.repository.AuthorityRepository;
import academy.ingress.bookstoreingress.repository.UserRepository;
import academy.ingress.bookstoreingress.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorRepository authorRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional
    public void signUp(AuthorSignUpRequest authorSignUpRequest) {
        User user = new User();
        userRepository.findByUsername(authorSignUpRequest.getEmail()).ifPresent(account -> {
            throw new IllegalArgumentException("Email used");
        });
        user.setUsername(authorSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authorSignUpRequest.getPassword()));
        Authority authority = authorityRepository.findByAuthority(UserAuthority.AUTHOR.toString()).orElse(
                Authority.builder().authority(UserAuthority.AUTHOR.toString()).build()
        );
        authorityRepository.save(authority);
        user.setAuthorities(Set.of(authority));
        Author author = new Author();
        author.setName(authorSignUpRequest.getName());
        author.setAge(authorSignUpRequest.getAge());
        author.setUser(user);
        userRepository.save(user);
        authorRepository.save(author);
    }

    @Override
    public Author findByEmail(String email) {
        User user = userRepository.findByUsername(email).orElseThrow(() -> new CustomNotFoundException("User not found"));
        Author author = authorRepository.findByUser(user).orElseThrow(() -> new CustomNotFoundException("Author not found"));
        return author;
    }
}
