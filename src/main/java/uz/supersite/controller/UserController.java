package uz.supersite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResizableByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import uz.supersite.entity.User;
import uz.supersite.exception.UserNotFoundException;
import uz.supersite.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsersController(){
		return userService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById( @PathVariable Integer id) {
        User userById;
        try {
            userById = userService.getUserById(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userById, HttpStatus.valueOf(200));
    }

	@PostMapping("/users/add")
	public HttpEntity<?> addUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.addUser(user));
	}
	
	@PutMapping("/users/update/{id}")
	public HttpEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) throws UserNotFoundException {
		return ResponseEntity.ok(userService.updateUser(user, id));
	}

	@GetMapping("/users/delete/{id}")
	public HttpEntity<?> deleteUser(@PathVariable(name = "id") Integer id) throws UserNotFoundException {
		return ResponseEntity.ok(userService.delete(id));
	}
}

