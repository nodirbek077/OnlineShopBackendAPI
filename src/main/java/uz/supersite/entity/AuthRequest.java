package uz.supersite.entity;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class AuthRequest {
    @Email @Length(min = 5, max = 50)
    private String email;
    @Length(min = 5, max = 10)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
