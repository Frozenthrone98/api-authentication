package co.pragma.powerup.model.user.gateways;

import co.pragma.powerup.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Void> save(User user);
    Mono<Boolean> existsByEmailAndIdentityDocument(String email, String document);
}
