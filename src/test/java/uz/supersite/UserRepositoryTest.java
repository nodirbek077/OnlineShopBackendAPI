//package uz.supersite;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.annotation.Rollback;
//import uz.supersite.entity.Role;
//import uz.supersite.entity.User;
//import uz.supersite.repository.UserRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//public class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testCreateNewUserWithOneRole() {
//        Role roleAdmin = entityManager.find(Role.class, 1);
//        User userNodirbek = new User("sarvar111@gmail.com",
//                                   "sarvar2020",
//                                  "Sarvar111",
//                                  "Shermatov111",
//                                   "Karshi tumani",
//                                  "+998945283534",
//                                      true);
//        userNodirbek.addRole(roleAdmin);
//
//        User savedUser = userRepository.save(userNodirbek);
//        assertThat(savedUser.getId()).isGreaterThan(0);
//    }
//}
