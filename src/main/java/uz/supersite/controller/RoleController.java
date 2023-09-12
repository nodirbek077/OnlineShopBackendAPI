package uz.supersite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.supersite.entity.Role;
import uz.supersite.exception.RoleNotFoundException;
import uz.supersite.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@GetMapping()
	public List<Role> getAll(){
		return roleService.getAllRoles();
	}

	@GetMapping("/{id}")
	public HttpEntity<?> getRole(@PathVariable Integer id){
		Role role =  roleService.getRole(id);
		return ResponseEntity.status(role != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(role);
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Role role) {
		Role savedRole = roleService.addRole(role);
	    return 	ResponseEntity.status(201).body(savedRole);
	}
	
	@PutMapping("/update/{id}")
	public HttpEntity<?> update(@PathVariable Integer id, @RequestBody Role role) throws RoleNotFoundException {
		Role editedRole = roleService.updateRole(role, id);
		return ResponseEntity.status(editedRole != null ? 202 : 409).body(editedRole);
	}

	@DeleteMapping("/delete/{id}")
	public HttpEntity<?> delete(@PathVariable Integer id){
		boolean deleted = roleService.delete(id);
		if (deleted)
			return ResponseEntity.noContent().build();
		return ResponseEntity.notFound().build();
	}
}

