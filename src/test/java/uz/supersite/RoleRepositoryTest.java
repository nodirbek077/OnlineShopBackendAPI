//package uz.supersite;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import uz.supersite.entity.Role;
//import uz.supersite.repository.RoleRepository;
//
//import java.util.List;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class RoleRepositoryTest {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    public void testCreateFirstRole() {
//        Role roleAdmin = new Role("Admin", "manage everything");
//        Role savedRole = roleRepository.save(roleAdmin);
//
//        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testCreateRestRoles() {
//        Role roleSalesperson = new Role("Salesperson", "manage product price, customers, shipping, orders and sales report");
//        Role roleEditor = new Role("Editor", "manage product price, customers, shipping, orders and sales report");
//        Role roleShipper = new Role("Shipper", "view products, view orders and update order status");
//        Role roleAssistant = new Role("Assistant", "manage questions and reviews");
//
//        roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
//    }
//}
