package co.pragma.powerup.r2dbc.user;

import co.pragma.powerup.model.user.User;
import co.pragma.powerup.model.user.gateways.UserRepository;
import co.pragma.powerup.r2dbc.entity.UserEntity;
import co.pragma.powerup.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User, UserEntity, Long, IUserRepository> implements UserRepository {

    private final TransactionalOperator txOperator;

    public UserRepositoryAdapter(IUserRepository repository, ObjectMapper mapper, TransactionalOperator txOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.txOperator = txOperator;
    }

    @Override
    public Mono<Void> save(User user) {
        return repository.save(toData(user))
                .then()
                .as(txOperator::transactional);
    }

    @Override
    public Flux<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toEntity);
    }

    @Override
    public Flux<User> findByIdentityDocument(String identityDocument) {
        return repository.findByIdentityDocument(identityDocument)
                .map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existsByIdentityDocument(String document) {
        return repository.existsByIdentityDocument(document);
    }
}
