package academy.ingress.bookstoreingress.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentSignUpRequest {
    private String name;
    private String email;
    private String password;
}
