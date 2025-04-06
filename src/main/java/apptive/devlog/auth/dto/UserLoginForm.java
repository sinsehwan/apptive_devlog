package apptive.devlog.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginForm {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @NotBlank
    private String password;
}
