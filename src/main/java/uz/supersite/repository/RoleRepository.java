package uz.supersite.repository;

import org.springframework.data.repository.CrudRepository;
import uz.supersite.entity.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
