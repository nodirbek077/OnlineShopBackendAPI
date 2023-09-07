package uz.supersite.repository;

import org.springframework.data.repository.CrudRepository;
import uz.supersite.entity.Role;

public interface RoleRepository extends CrudRepository<Role,Integer> {
    public Long countById(Integer id);
}
