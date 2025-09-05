package co.pragma.powerup.api.dto.response.validate;

public record UserValidateResponseDTO(
        Boolean exists,
        String message
) {
}
