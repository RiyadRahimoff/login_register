package com.azecoders.community.model.request.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateRegisterRequest {
  @NotBlank(message = "First name cannot be empty!")
  String firstName;
  @NotBlank(message = "Last name cannot be empty!")
  String lastName;
  @NotBlank(message = "Email cannot be empty!")
  @Email(regexp = "^[\\w.-]+@[\\w.-]+\\.(com|ru)$", message = "The email must also have the @ symbol and end with .com or .ru.")
  String email;
  @NotBlank(message = "Password cannot be empty!")
  @Size(min=8,max = 255)
  String password;
}
