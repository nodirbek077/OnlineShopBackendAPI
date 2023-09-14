package uz.supersite.service;

import java.util.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import uz.supersite.ResponseEntity;
import uz.supersite.entity.User;
import uz.supersite.entity.Vacancy;
import uz.supersite.exception.CustomPropertyValueException;
import uz.supersite.exception.ItemNotFoundException;
import uz.supersite.exception.UserNotFoundException;
import uz.supersite.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CloudinaryImageService cloudinaryImageService;

	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}

	public User getUserById(Integer id){
			Optional<User> optionalUser = userRepository.findById(id);
			return optionalUser.orElse(null);
	}

	public User addUser(User user, MultipartFile file) {
		if(!isEmailUnique(user.getId(), user.getEmail())){
			throw new CustomPropertyValueException("User with " + user.getEmail() + " is already exist!");
		}
			String imageUrl = cloudinaryImageService.upload(file);
			user.setPhotos(imageUrl);
			user.setEnabled(true);
			return userRepository.save(user);
	}
	
	public User updateUser(User userInForm, Integer id, MultipartFile file) {
		 Optional<User> optionalUser = userRepository.findById(id);

		 if(optionalUser.isPresent()) {
			 User userInDb = optionalUser.get();
			 userInDb.setPassword(userInForm.getPassword());

			 String fileUrl = cloudinaryImageService.upload(file);
    		 userInDb.setPhotos(fileUrl);

			 userInDb.setPhoneNumber(userInForm.getPhoneNumber());
			 userInDb.setAddress(userInForm.getAddress());
			 userInDb.setFirstName(userInForm.getFirstName());
			 userInDb.setLastName(userInForm.getLastName());
			 userInDb.setEmail(userInForm.getEmail());
   			 return userRepository.save(userInDb);
		 }
		 return null;

	}
	public boolean delete(Integer id){
		try {
			userRepository.deleteById(id);
			return true;
		}catch (Exception e){
			return false;
		}
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
