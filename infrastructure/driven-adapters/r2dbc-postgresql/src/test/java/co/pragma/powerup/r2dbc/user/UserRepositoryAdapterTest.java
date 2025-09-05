package co.pragma.powerup.r2dbc.user;

import co.pragma.powerup.model.config.BusinessException;
import co.pragma.powerup.model.user.User;
import co.pragma.powerup.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;
//
//    @Mock
//    TransactionalOperator txOperator;
//
//    @Mock
//    IUserRepository repository;
//
//    @Mock
//    ObjectMapper objectMapper;
//
//    @Mock
//    UserEntity userEntity;
//
//    User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User().builder()
//                .id(1L)
//                .name("Diego")
//                .lastName("Ramirez")
//                .email("example@hotmail.com")
//                .identityDocument("12345678")
//                .phone("912323123")
//                .idRole(1L)
//                .baseSalary(new BigDecimal(120302))
//                .build();
//    }
//
//    @Test
//    void save_ShouldSaveUser_WhenUserDoesNotExistAndAmountIsValid() {
//        // Arrange
//        when(repository.existsByEmailAndIdentityDocument(anyString(), anyString()))
//                .thenReturn(Mono.just(false));
//        when(objectMapper.map(any(User.class), eq(UserEntity.class))).thenReturn(userEntity);
//        when(repository.save(any(UserEntity.class)))
//                .thenReturn(Mono.just(userEntity));
//        when(txOperator.transactional(any(Mono.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act & Assert
//        StepVerifier.create(userRepositoryAdapter.save(user))
//                .verifyComplete();
//
//        // Verify
//        verify(repository).existsByEmailAndIdentityDocument("example@hotmail.com", "12345678");
//        verify(repository).save(any(UserEntity.class));
//        verify(txOperator).transactional(any(Mono.class));
//    }
//
//    @Test
//    void save_ShouldThrowBusinessException_WhenUserAlreadyExists() {
//        // Arrange
//        when(repository.existsByEmailAndIdentityDocument(anyString(), anyString()))
//                .thenReturn(Mono.just(true));
//        when(txOperator.transactional(any(Mono.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act & Assert
//        StepVerifier.create(userRepositoryAdapter.save(user))
//                .verifyErrorSatisfies(throwable -> {
//                    assertThat(throwable)
//                            .isInstanceOf(BusinessException.class)
//                            .hasFieldOrPropertyWithValue("code", "B002")
//                            .hasFieldOrPropertyWithValue("message", "User with given email and document already exists.");
//                });
//
//        // Verify
//        verify(repository).existsByEmailAndIdentityDocument("example@hotmail.com", "12345678");
//        verify(repository, never()).save(any());
//    }
//
//    @Test
//    void save_ShouldThrowBusinessException_WhenBaseSalaryIsBelowMinimum() {
//        // Arrange
//        user.setBaseSalary(new BigDecimal(-1000L));
//        when(repository.existsByEmailAndIdentityDocument(anyString(), anyString()))
//                .thenReturn(Mono.just(true));
//        when(txOperator.transactional(any(Mono.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act & Assert
//        StepVerifier.create(userRepositoryAdapter.save(user))
//                .verifyErrorSatisfies(throwable ->
//                        assertThat(throwable)
//                                .isInstanceOf(BusinessException.class)
//                                .hasFieldOrPropertyWithValue("code", "B001")
//                                .hasFieldOrPropertyWithValue("message","Minimum allowed base salary is 0")
//                );
//
//        // Verify
//        verify(repository, never()).save(any());
//    }
//
//    @Test
//    void save_ShouldThrowBusinessException_WhenBaseSalaryIsGreaterThanMaximum() {
//        // Arrange
//        user.setBaseSalary(new BigDecimal(200000000L));
//        when(repository.existsByEmailAndIdentityDocument(anyString(), anyString()))
//                .thenReturn(Mono.just(true));
//        when(txOperator.transactional(any(Mono.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act & Assert
//        StepVerifier.create(userRepositoryAdapter.save(user))
//                .verifyErrorSatisfies(throwable ->
//                        assertThat(throwable)
//                                .isInstanceOf(BusinessException.class)
//                                .hasFieldOrPropertyWithValue("code", "B001")
//                                .hasFieldOrPropertyWithValue("message","Maximum allowed base salary is 15000000")
//                );
//
//        // Verify
//        verify(repository, never()).save(any());
//    }
//
//    @Test
//    void save_ShouldThrowBusinessException_WhenBaseSalaryIsNull() {
//        // Arrange
//        user.setBaseSalary(null);
//        when(repository.existsByEmailAndIdentityDocument(anyString(), anyString()))
//                .thenReturn(Mono.just(true));
//        when(txOperator.transactional(any(Mono.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act & Assert
//        StepVerifier.create(userRepositoryAdapter.save(user))
//                .verifyErrorSatisfies(throwable ->
//                        assertThat(throwable)
//                                .isInstanceOf(BusinessException.class)
//                                .hasFieldOrPropertyWithValue("code", "B001")
//                                .hasFieldOrPropertyWithValue("message","Base Salary is required.")
//                );
//
//        // Verify
//        verify(repository, never()).save(any());
//    }
//
//    @Test
//    void existsByEmailAndIdentityDocument_ShouldDelegateToRepository() {
//        when(repository.existsByEmailAndIdentityDocument("test@email.com", "12345678"))
//                .thenReturn(Mono.just(true));
//
//        StepVerifier.create(userRepositoryAdapter.existsByEmailAndIdentityDocument("test@email.com", "12345678"))
//                .expectNext(true)
//                .verifyComplete();
//
//        verify(repository).existsByEmailAndIdentityDocument("test@email.com", "12345678");
//    }


}