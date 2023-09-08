package uz.supersite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uz.supersite.entity.Vacancy;

public interface VacancyRepository extends CrudRepository<Vacancy,Integer>, PagingAndSortingRepository<Vacancy,Integer> {
    @Query("SELECT v FROM Vacancy v WHERE v.title LIKE %?1%")
    Page<Vacancy>findAllByTitle(@Param("title") String title, Pageable pageable);
}
