package uz.supersite.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.supersite.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
    Customer findByVerificationCode(String code);

    @Query("UPDATE Customer c SET c.enabled = true WHERE c.id = ?1")
    @Modifying
    void enable(Integer id);
}
