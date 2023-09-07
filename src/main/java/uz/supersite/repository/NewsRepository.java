package uz.supersite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.supersite.entity.News;

public interface NewsRepository extends CrudRepository<News,Integer>, PagingAndSortingRepository<News,Integer> {

}
