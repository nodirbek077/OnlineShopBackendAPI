package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.supersite.entity.Role;
import uz.supersite.repository.RoleRepository;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public Role addRole(Role role){
        return  roleRepository.save(role);
    }

    public Role updateRole(Role role, Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isPresent()){
            Role roleInDb = optionalRole.get();
            roleInDb.setName(role.getName());
            roleInDb.setDescription(role.getDescription());
            return roleRepository.save(roleInDb);
        }
        return null;
    }

    public boolean delete(Integer id){
        try {
            roleRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Role getRole(Integer id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }
}
