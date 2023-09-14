package uz.supersite.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.supersite.entity.Category;
import java.util.List;
public interface CategoryRepository extends CrudRepository<Category,Integer>, PagingAndSortingRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    List<Category> findRootCategories();
    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    @Transactional
    void updateCategoryEnabledStatus(Integer id, boolean enabled);
    Category findByName(String name);
}
