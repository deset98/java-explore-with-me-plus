package ru.practicum.ewm.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDto {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String name;
}
