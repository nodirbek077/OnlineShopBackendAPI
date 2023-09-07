package uz.supersite.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uz.supersite.entity.User;

public interface UserRepository extends CrudRepository<User,Integer>, PagingAndSortingRepository<User, Integer> {
    public Long countById(Integer id);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);
}
