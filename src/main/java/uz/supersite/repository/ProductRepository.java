package uz.supersite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.supersite.entity.Product;

public interface ProductRepository extends CrudRepository<Product,Integer>, PagingAndSortingRepository<Product, Integer> {
}
