package co.pragma.powerup.r2dbc.user;

import co.pragma.powerup.model.config.BusinessException;
import co.pragma.powerup.model.user.User;
import co.pragma.powerup.model.user.gateways.UserRepository;
import co.pragma.powerup.r2dbc.entity.UserEntity;
import co.pragma.powerup.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import static co.pragma.powerup.r2dbc.util.Constants.*;

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
        return validateAmount(user)
                .then(existsByEmailAndIdentityDocument(user.getEmail(), user.getIdentityDocument()))
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B002)
                        .message(BUSINESS_VALIDATIONS_USER_EXIST_B002)
                        .build()))
                .flatMap(exist -> repository.save(toData(user)))
                .doOnSuccess(unused -> log.info("[INFO] - User saved successfully"))
                .doOnError(error -> log.error("[ERROR] - Error saving user: {}", error.getMessage(), error))
                .then()
                .as(txOperator::transactional);
    }

    private Mono<User> validateAmount(User user) {
        return Mono.just(user)
                .filter(usr -> usr.getBaseSalary() != null)
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B001)
                        .message(BUSINESS_VALIDATIONS_BASE_SALARY_B001)
                        .build()))
                .filter(usr -> usr.getBaseSalary().compareTo(MIN_BASE_SALARY) >= 0)
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B001)
                        .message(BUSINESS_VALIDATIONS_MIN_BASE_SALARY_B001 + MIN_BASE_SALARY)
                        .build()))
                .filter(usr -> usr.getBaseSalary().compareTo(MAX_BASE_SALARY) <= 0)
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B001)
                        .message(BUSINESS_VALIDATIONS_MAX_BASE_SALARY_B001 + MAX_BASE_SALARY)
                        .build()));
    }

    @Override
    public Mono<Boolean> existsByEmailAndIdentityDocument(String email, String document) {
        return repository.existsByEmailAndIdentityDocument(email, document);
    }
}
