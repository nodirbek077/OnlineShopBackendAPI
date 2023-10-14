package uz.supersite.reqAndRes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class RegisterRequest {
    String firstName;
    String lastName;

    String email;

    String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    Date birthDate;

    String phoneNumber;
}
