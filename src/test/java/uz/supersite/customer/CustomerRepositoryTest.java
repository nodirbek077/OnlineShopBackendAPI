package uz.supersite.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import uz.supersite.entity.Country;
import uz.supersite.entity.Customer;
import uz.supersite.repository.CustomerRepository;

import java.util.Date;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateCustomer1(){
        Integer countryId = 3;
        Country country = testEntityManager.find(Country.class,countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstName("David");
        customer.setLastName("Aripov");
        customer.setPassword("david123");
        customer.setEmail("davidaripov@gmail.com");
        customer.setPhoneNumber("+998945283534");
        customer.setAddress("Nasaf 49");
        customer.setCity("Karshi");
        customer.setState("Ushoktepa");
        customer.setPostalCode("121314");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = customerRepository.save(customer);
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCustomer2(){
        Integer countryId = 4;
        Country country = testEntityManager.find(Country.class,countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstName("Sanya");
        customer.setLastName("Lad");
        customer.setPassword("password456");
        customer.setEmail("sanyalad2020@gmail.com");
        customer.setPhoneNumber("022295283534");
        customer.setAddress("Shah, Sunmill Road");
        customer.setCity("Mumbai");
        customer.setState("Maharashtra");
        customer.setPostalCode("400013");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = customerRepository.save(customer);
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testListCustomers(){
        Iterable<Customer> customers = customerRepository.findAll();
        customers.forEach(System.out::println);

        Assertions.assertThat(customers).hasSizeGreaterThan(1);
    }

    @Test
    public void testUpdateCustomer(){
        Integer customerId = 8;
        String lastName = "Stanfield";

        Customer customer = customerRepository.findById(customerId).get();
        customer.setLastName(lastName);
        customer.setEnabled(true);

        Customer updatedCustomer = customerRepository.save(customer);
        Assertions.assertThat(updatedCustomer.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testGetCustomer(){
        Integer customerId = 9;
        Optional<Customer> customer = customerRepository.findById(customerId);
        Assertions.assertThat(customer).isPresent();

        Customer customer1 = customer.get();
        System.out.println(customer1);
    }

    @Test
    public void testDeleteCustomer(){
        Integer customerId = 8;
        customerRepository.deleteById(customerId);

        Optional<Customer> customer = customerRepository.findById(customerId);
        Assertions.assertThat(customer).isNotPresent();
    }

    @Test
    public void testFindByEmail(){
        String email = "sanyalad2020@gmail.com";
        Customer byEmail = customerRepository.findByEmail(email);
        Assertions.assertThat(byEmail).isNotNull();
        System.out.println(byEmail);
    }

    @Test
    public void testFindByVerificationCode(){
        String code = "code_123";
        Customer byVerificationCode = customerRepository.findByVerificationCode(code);
        Assertions.assertThat(byVerificationCode).isNotNull();
        System.out.println(byVerificationCode);
    }

    @Test
    public void testEnableCustomer(){
        Integer customerId = 9;
        customerRepository.enable(customerId);

        Customer customer = customerRepository.findById(customerId).get();
        Assertions.assertThat(customer.isEnabled()).isTrue();
    }
}
