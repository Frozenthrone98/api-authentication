package co.pragma.powerup.api.user;

import co.pragma.powerup.api.dto.request.register.UserRequestDTO;
import co.pragma.powerup.api.dto.response.validate.UserValidateResponseDTO;
import co.pragma.powerup.api.exception.EmptyBodyException;
import co.pragma.powerup.api.mapper.UserMapper;
import co.pragma.powerup.api.util.ValidatorUtil;
import co.pragma.powerup.usecase.registeruser.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.pragma.powerup.api.util.Constants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;
    private final ValidatorUtil validatorUtil;

    public Mono<ServerResponse> register(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .switchIfEmpty(Mono.error(EmptyBodyException.builder()
                        .code(E002)
                        .message(EMPTY_BODY_ERROR_E002)
                        .build()))
                .flatMap(validatorUtil::validate)
                .map(userMapper::toModel)
                .flatMap(userUseCase::register)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .doOnSuccess(response -> log.info("[INFO] HTTP response register built successfully"))
                .doOnError(error -> log.error("[ERROR] Error building register response: {}", error.getMessage()));
    }

    public Mono<ServerResponse> validate(ServerRequest serverRequest) {
        String identityDocument = serverRequest.pathVariable("identityDocument");
        return Mono.just(identityDocument)
                .filter(document -> !document.trim().isEmpty())
                .switchIfEmpty(Mono.error(EmptyBodyException.builder()
                        .code(E002)
                        .message(EMPTY_BODY_ERROR_E002)
                        .build()))
                .flatMap(userUseCase::validateByIdentityDocument)
                .flatMap(isFound -> ServerResponse.ok()
                        .bodyValue(new UserValidateResponseDTO(
                                isFound,
                                isFound ? RESPONSE_VALIDATIONS_IS_FOUND : RESPONSE_VALIDATIONS_NOT_FOUND)))
                .doOnSuccess(response -> log.info("[INFO] HTTP response validate built successfully"))
                .doOnError(error -> log.error("[ERROR] Error building validate response: {}", error.getMessage()));
    }
}
