package academy.ingress.bookstoreingress.controller;

import academy.ingress.bookstoreingress.dto.AuthorSignUpRequest;
import academy.ingress.bookstoreingress.dto.SignInRequest;
import academy.ingress.bookstoreingress.dto.StudentSignUpRequest;
import academy.ingress.bookstoreingress.service.AuthorService;
import academy.ingress.bookstoreingress.service.StudentService;
import academy.ingress.bookstoreingress.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthorService authorService;
    private final StudentService studentService;

    @PostMapping("/student/sign-up")
    public String signUpStudent(@RequestBody StudentSignUpRequest signUpRequest) {
        studentService.signUp(signUpRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getEmail(),
                        signUpRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return token;
    }

    @PostMapping("/author/sign-up")
    public String signUpAuthor(@RequestBody AuthorSignUpRequest authorSignUpRequest) {
        authorService.signUp(authorSignUpRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authorSignUpRequest.getEmail(),
                        authorSignUpRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return token;
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequest signInRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);
        return token;
    }
}
