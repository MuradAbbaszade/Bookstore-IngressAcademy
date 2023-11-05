package academy.ingress.bookstoreingress.service;

import academy.ingress.bookstoreingress.domain.Author;
import academy.ingress.bookstoreingress.dto.AuthorSignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    void signUp(AuthorSignUpRequest signUpRequest);

    Author findByEmail(String email);
}
