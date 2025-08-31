package co.pragma.powerup.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UserRequestDTO(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Last name must not be blank")
        String lastName,

        @NotBlank(message = "Email must not be blank")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email must be valid")
        String email,

        @NotBlank(message = "Identity document must not be blank")
        @Pattern(regexp = "\\d{8,12}", message = "Identity document must be 8-12 digits")
        String identityDocument,

        @NotBlank(message = "Phone must not be blank")
        @Pattern(regexp = "^\\d{9}$", message = "Phone must be 9 digits and only numbers")
        String phone,

        @NotNull(message = "Role ID must not be null")
        Long idRole,

        BigDecimal baseSalary
) {
}
