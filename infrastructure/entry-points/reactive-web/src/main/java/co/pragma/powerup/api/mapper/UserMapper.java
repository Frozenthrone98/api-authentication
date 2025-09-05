package co.pragma.powerup.api.mapper;

import co.pragma.powerup.api.dto.request.register.UserRequestDTO;
import co.pragma.powerup.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserRequestDTO userDTO);
}
