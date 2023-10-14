package uz.supersite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.supersite.entity.Customer;
import uz.supersite.entity.Role;
import uz.supersite.entity.VerificationToken;
import uz.supersite.exception.UserAlreadyExistsException;
import uz.supersite.repository.CustomerRepository;
import uz.supersite.repository.RoleRepository;
import uz.supersite.repository.VerificationTokenRepository;
import uz.supersite.reqAndRes.RegisterRequest;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    @Override
    public Customer register(RegisterRequest request) {
        Optional<Customer> customer = this.findByEmail(request.getEmail());
        if (customer.isPresent()){
            throw new UserAlreadyExistsException(
                    "Customer with email " + request.getEmail() + " already exists!");
        }

        var newCustomer = new Customer();
        newCustomer.setFirstName(request.getFirstName());
        newCustomer.setLastName(request.getLastName());
        newCustomer.setEmail(request.getEmail());
        newCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
        newCustomer.setBirthDate(request.getBirthDate());
        newCustomer.setPhoneNumber(request.getPhoneNumber());

        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        if (roleAdmin.isEmpty()){
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            Role savedRole = roleRepository.save(role);
            roleAdmin = Optional.of(savedRole);
        }
        newCustomer.setRoles(Collections.singleton(roleAdmin.get()));
        return customerRepository.save(newCustomer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public void saveCustomerVerificationToken(Customer customer, String token) {
        var verificationToken = new VerificationToken(token, customer);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null) return "Invalid verification token";

        Customer customer = token.getCustomer();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }

        customer.setEnabled(true);
        customerRepository.save(customer);
        return "valid";
    }
}
