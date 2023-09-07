package uz.supersite.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.supersite.ResponseEntity;
import uz.supersite.entity.User;
import uz.supersite.exception.CustomPropertyValueException;
import uz.supersite.exception.UserNotFoundException;
import uz.supersite.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}

	public User getUserById(Integer id) throws UserNotFoundException{

			Optional<User> optionalUserById = userRepository.findById(id);
			if (optionalUserById.isPresent()){
                //				gotUser.setFirstName(user.getFirstName());
//				gotUser.setLastName(user.getLastName());
//				gotUser.setRoles(user.getRoles());
//				gotUser.setPassword(user.getPassword());
//				gotUser.setPhoneNumber(user.getPhoneNumber());
//				gotUser.setEmail(user.getEmail());
//				gotUser.setAddress(user.getAddress());
//				gotUser.setPhotos(user.getPhotos());
//				gotUser.setEnabled(user.isEnabled());
				return optionalUserById.get();
			}else {
			    throw  new UserNotFoundException("Could not found any user with ID " + id);
			}
	}
	public ResponseEntity addUser(User user) {
		try {

			if(isEmailUnique(user.getId(), user.getEmail())){
				user.setEmail(user.getEmail());
			}else {
				return new ResponseEntity("Email is already exist");
			}

			userRepository.save(user);
			return new ResponseEntity(0,"Success");
		} catch (Exception e) {
			throw new CustomPropertyValueException("not-null property references a null or transient value");
		}	
	}
	
	public ResponseEntity updateUser(User userInForm, Integer id) throws UserNotFoundException {
		 Optional<User> optionalUser = userRepository.findById(id);

		 if(optionalUser.isEmpty()) {
			throw  new UserNotFoundException("Could not found any user with ID " + id);
		 }
			User userInDb = optionalUser.get();

			if(!userInForm.getPassword().isEmpty()) {
				userInDb.setPassword(userInForm.getPassword());
			}

			if(userInForm.getPhotos() != null) {
				userInDb.setPhotos(userInForm.getPhotos());
			}

			if(userInForm.getPhoneNumber() != null) {
				userInDb.setPhoneNumber(userInForm.getPhoneNumber());
			}

			if(userInForm.getAddress() != null) {
				userInDb.setAddress(userInForm.getAddress());
			}

			userInDb.setFirstName(userInForm.getFirstName());
			userInDb.setLastName(userInForm.getLastName());
			userInDb.setEmail(userInForm.getEmail());

			userRepository.save(userInDb);
			return new ResponseEntity(0, "Success"); 

	}
	public ResponseEntity delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not found any user with ID " + id);
		}
		userRepository.deleteById(id);
		return new ResponseEntity(0, "Success");
	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);

		if(userByEmail == null) return true;

		boolean isCreatingNew = (id == null);

		if(isCreatingNew) {
            return false;
		}else {
            return Objects.equals(userByEmail.getId(), id);
		}
    }

}
