package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.supersite.ResponseEntity;
import uz.supersite.entity.Role;
import uz.supersite.entity.User;
import uz.supersite.exception.RoleNotFoundException;
import uz.supersite.exception.UserNotFoundException;
import uz.supersite.repository.RoleRepository;
import uz.supersite.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public ResponseEntity addRole(Role role){
        roleRepository.save(role);
        return new ResponseEntity(0, "Role saved!");
    }

    public ResponseEntity updateRole(Role role, Integer id) throws RoleNotFoundException {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if(optionalRole.isEmpty()) {
            throw  new RoleNotFoundException("Could not found any role with ID " + id);
        }

        Role roleInDb = optionalRole.get();
        roleInDb.setName(role.getName());
        roleInDb.setDescription(role.getDescription());
        roleRepository.save(roleInDb);

        return new ResponseEntity(0, "Success");
    }

    public ResponseEntity delete(Integer id) throws  RoleNotFoundException {
        Long countById = roleRepository.countById(id);
        if(countById == null || countById == 0) {
            throw new RoleNotFoundException("Could not found any role with ID " + id);
        }
        roleRepository.deleteById(id);
        return new ResponseEntity(0, "Success");
    }

}
