package apptive.devlog.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserSaveForm {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;


    private LocalDate birth;

    private String gender;

}
