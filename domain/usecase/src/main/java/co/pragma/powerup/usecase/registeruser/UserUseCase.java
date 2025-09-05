package co.pragma.powerup.usecase.registeruser;

import co.pragma.powerup.model.config.BusinessException;
import co.pragma.powerup.model.role.gateways.RoleRepository;
import co.pragma.powerup.model.user.User;
import co.pragma.powerup.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static co.pragma.powerup.usecase.util.Constants.*;


@RequiredArgsConstructor
public class UserUseCase {

    private static final Logger log = Logger.getLogger(UserUseCase.class.getName());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Mono<Void> register(User user) {
        return validateAmount(user)
                .then(validateUserCreation(user))
                .then(userRepository.save(user))
                .doOnSuccess(unused -> log.info("[INFO] - User saved successfully"))
                .doOnError(error -> {
                    log.severe("[ERROR] - Error saving user: " + error.getMessage());
                    error.printStackTrace();
                })
                .then();
    }

    public Mono<Boolean> validateByIdentityDocument(String identityDocument) {
        return userRepository.existsByIdentityDocument(identityDocument)
                .doOnSuccess(unused -> log.info("[INFO] - User with identity document found"))
                .doOnError(error -> {
                    log.severe("[ERROR] - Error validating user: " + error.getMessage());
                    error.printStackTrace();
                });
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

    public Mono<Void> validateUserCreation(User user) {
        return validateEmailWithDocument(user.getEmail(), user.getIdentityDocument())
                .then(validateDocumentWithRole(user.getIdentityDocument(), user.getIdRole())
                        .then(validateRole(user.getIdRole())));
    }

    private Mono<Void> validateEmailWithDocument(String email, String identityDocument) {
        return userRepository.findByEmail(email)
                .filter(existingUser -> !existingUser.getIdentityDocument().equals(identityDocument))
                .hasElements()
                .flatMap(hasConflict -> hasConflict ?
                        Mono.error(BusinessException.builder()
                                .code(B002)
                                .message(BUSINESS_VALIDATIONS_EMAIL_EXIST_B002)
                                .build())
                        : Mono.empty());
    }

    private Mono<Void> validateDocumentWithRole(String identityDocument, Long idRole) {
        return userRepository.findByIdentityDocument(identityDocument)
                .filter(user -> user.getIdRole().equals(idRole))
                .hasElements()
                .flatMap(exists -> exists ?
                        Mono.error(BusinessException.builder()
                                .code(B002)
                                .message(BUSINESS_VALIDATIONS_USER_WITH_ROL_EXIST_B002)
                                .build())
                        : Mono.empty());
    }

    private Mono<Void> validateRole(Long idRole) {
        return roleRepository.findById(idRole)
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B003)
                        .message(BUSINESS_VALIDATIONS_ROLE_NOT_FOUND_B003)
                        .build()))
                .then();
    }
}