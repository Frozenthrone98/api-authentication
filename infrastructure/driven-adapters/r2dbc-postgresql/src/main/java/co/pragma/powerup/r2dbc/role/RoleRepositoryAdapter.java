package co.pragma.powerup.r2dbc.role;

import co.pragma.powerup.model.role.Role;
import co.pragma.powerup.model.role.gateways.RoleRepository;
import co.pragma.powerup.r2dbc.entity.RoleEntity;
import co.pragma.powerup.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class RoleRepositoryAdapter extends ReactiveAdapterOperations<
        Role, RoleEntity, Long, IRoleRepository> implements RoleRepository {

    public RoleRepositoryAdapter(IRoleRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

}
