package co.pragma.powerup.model.user.gateways;

import co.pragma.powerup.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Void> save(User user);
    Flux<User> findByEmail(String email);
    Flux<User> findByIdentityDocument(String identityDocument);
    Mono<Boolean> existsByIdentityDocument(String document);
}
