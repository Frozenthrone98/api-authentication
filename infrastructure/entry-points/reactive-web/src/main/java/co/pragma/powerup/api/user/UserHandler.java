package co.pragma.powerup.api.user;

import co.pragma.powerup.api.dto.UserRequestDTO;
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

import static co.pragma.powerup.api.util.Constants.E002;
import static co.pragma.powerup.api.util.Constants.EMPTY_BODY_ERROR_E002;

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
                .doOnSuccess(response -> log.info("[INFO] HTTP response built successfully"))
                .doOnError(error -> log.error("[ERROR] Error building response: {}", error.getMessage()));
    }
}
