package uz.supersite.service;

import uz.supersite.entity.Customer;
import uz.supersite.reqAndRes.RegisterRequest;

import java.util.Optional;

public interface IAuthService {
    Customer register(RegisterRequest request);
    Optional<Customer> findByEmail(String email);
    void saveCustomerVerificationToken(Customer theUser, String verificationToken);
    String validateToken(String theToken);
}
