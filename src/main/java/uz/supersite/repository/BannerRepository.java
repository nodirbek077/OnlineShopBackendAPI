package uz.supersite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.supersite.entity.Banner;

public interface BannerRepository extends CrudRepository<Banner,Integer>, PagingAndSortingRepository<Banner,Integer> {
}
