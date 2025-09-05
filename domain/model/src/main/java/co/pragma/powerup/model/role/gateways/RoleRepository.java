package co.pragma.powerup.model.role.gateways;

import co.pragma.powerup.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> findById(Long id);
}
