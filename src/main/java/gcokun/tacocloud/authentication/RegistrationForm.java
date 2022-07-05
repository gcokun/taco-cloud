package gcokun.tacocloud.authentication;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private final String username;
    private final String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String apt;
    private final String phone;

    public Users toUser(PasswordEncoder passwordEncoder) {
        return new Users(username, passwordEncoder.encode(password), fullname, street, city, apt, phone);
    }
}
