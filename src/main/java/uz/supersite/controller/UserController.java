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
import uz.supersite.exception.UserNotFoundException;
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
	public HttpEntity<?> addUser(@Valid User user, MultipartFile file) {
		User addedUser = userService.addUser(user, file);
		return ResponseEntity.status(201).body(addedUser);
	}
	
	@PutMapping("/update/{id}")
	public HttpEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) throws UserNotFoundException {
		return ResponseEntity.ok(userService.updateUser(user, id));
	}

	@GetMapping("/delete/{id}")
	public HttpEntity<?> deleteUser(@PathVariable(name = "id") Integer id) throws UserNotFoundException {
		return ResponseEntity.ok(userService.delete(id));
	}
}

