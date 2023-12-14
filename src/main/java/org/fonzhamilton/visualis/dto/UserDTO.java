package org.fonzhamilton.visualis.dto;

import org.fonzhamilton.visualis.annotation.FieldMatch;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch.List( { @FieldMatch(first = "password", second = "matchingPassword", message = "Password Fields Must Be Matching")})
public class UserDTO {

    @NotEmpty(message = "Required")
    @NotEmpty(message = "Enter a Username")
    private String userName;

    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String firstName;

    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;

    @NotEmpty(message = "Required")
    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty(message = "Required")
    private String password;

    @NotEmpty(message = "Required")
    private String matchingPassword;

    public UserDTO(@NotEmpty String userName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String firstName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName, @Email String email, @NotEmpty(message = "Required") String password, @NotEmpty(message = "Required") String matchingPassword) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}
