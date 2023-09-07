package uz.supersite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.supersite.entity.Role;
import uz.supersite.entity.User;
import uz.supersite.exception.RoleNotFoundException;
import uz.supersite.exception.UserNotFoundException;
import uz.supersite.service.RoleService;
import uz.supersite.service.UserService;

import java.util.List;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/roles")
	public List<Role> getAllUsersController(){
		return roleService.getAllRoles();
	}

	@PostMapping("/roles/add")
	public HttpEntity<?> addRole(@RequestBody Role role) {
		return ResponseEntity.ok(roleService.addRole(role));
	}
	
	@PutMapping("/roles/update/{id}")
	public HttpEntity<?> updateUser(@PathVariable Integer id, @RequestBody Role role) throws RoleNotFoundException {
		return ResponseEntity.ok(roleService.updateRole(role, id));
	}

	@GetMapping("/roles/delete/{id}")
	public HttpEntity<?> deleteUser(@PathVariable(name = "id") Integer id) throws RoleNotFoundException {
		return ResponseEntity.ok(roleService.delete(id));
	}
}

