package uz.supersite.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.User;
import uz.supersite.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public HttpEntity<?> getUser(@PathVariable Integer id) {
		User user = userService.getUserById(id);
		return ResponseEntity.status(user != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(user);
    }
	@PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public HttpEntity<?> addUser(@Valid User user, MultipartFile file){
		User addedUser = userService.addUser(user, file);
		return ResponseEntity.status(201).body(addedUser);
	}
	@PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public HttpEntity<?> updateUser(@PathVariable Integer id, @Valid User user, MultipartFile file){
		User editedUser = userService.updateUser(user, id, file);
		return ResponseEntity.status(editedUser != null ? 202 : 409).body(editedUser);
	}

	@GetMapping("/{id}/enabled/{status}")
	public uz.supersite.ResponseEntity updateUserEnabledStatus(@PathVariable Integer id, @PathVariable boolean status){
		userService.updateUserEnabledStatus(id, status);
		return new uz.supersite.ResponseEntity("Status updated");
	}

	@DeleteMapping("/delete/{id}")
	public HttpEntity<?> deleteUser(@PathVariable Integer id){
		boolean deleted = userService.delete(id);
		if (deleted)
			return ResponseEntity.noContent().build();
		return ResponseEntity.notFound().build();
	}
}

