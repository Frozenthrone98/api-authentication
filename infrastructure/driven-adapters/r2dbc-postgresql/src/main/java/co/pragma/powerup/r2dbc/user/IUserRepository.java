package co.pragma.powerup.r2dbc.user;

import co.pragma.powerup.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {
    Flux<UserEntity> findByEmail(String email);
    Flux<UserEntity> findByIdentityDocument(String identityDocument);
    Mono<Boolean> existsByIdentityDocument(String identityDocument);
}
