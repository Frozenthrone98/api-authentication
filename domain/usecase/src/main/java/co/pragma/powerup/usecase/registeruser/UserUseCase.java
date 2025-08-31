package co.pragma.powerup.usecase.registeruser;

import co.pragma.powerup.model.user.User;
import co.pragma.powerup.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository userRepository;

    public Mono<Void> register(User user) {
        return userRepository.save(user);
    }
}
