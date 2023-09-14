package uz.supersite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Vacancy;
import uz.supersite.repository.VacancyRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {
    public static final int VACANCIES_PER_PAGE = 10;
    private final VacancyRepository vacancyRepository;
    private final CloudinaryImageService cloudinaryImageService;
    public VacancyService(VacancyRepository vacancyRepository, CloudinaryImageService cloudinaryImageService){
        this.vacancyRepository = vacancyRepository;
        this.cloudinaryImageService = cloudinaryImageService;
    }

    public List<Vacancy> getVacancies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vacancy> vacancyPage = vacancyRepository.findAll(pageable);
        return vacancyPage.getContent();
    }

    public List<Vacancy> getVacanciesByPageSort(int pageNum, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1,VACANCIES_PER_PAGE, sort);
        Page<Vacancy> vacancyPage = vacancyRepository.findAll(pageable);
        return vacancyPage.getContent();
    }

    public List<Vacancy> getVacanciesBySortTitle(String title, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Vacancy> vacancyAllByTitle = vacancyRepository.findAllByTitle(title, pageable);
        return vacancyAllByTitle.getContent();
    }
    public Vacancy add(Vacancy vacancy, MultipartFile  file) {
        if(!file.isEmpty()){
            vacancy.setImage(cloudinaryImageService.upload(file));
        }else {
            if(vacancy.getImage().isEmpty()) vacancy.setImage(null);
        }
        return vacancyRepository.save(vacancy);
    }

    public Vacancy getVacancy(Integer id) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        return optionalVacancy.orElse(null);
    }

    public Vacancy editVacancy(Integer id, Vacancy vacancy, MultipartFile file) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()){
            Vacancy editingVacancy = optionalVacancy.get();
            editingVacancy.setTitle(vacancy.getTitle());
            editingVacancy.setFromSalary(vacancy.getFromSalary());
            editingVacancy.setToSalary(vacancy.getToSalary());
            editingVacancy.setActive(vacancy.isActive());
            editingVacancy.setShortDescription(vacancy.getShortDescription());

            String fileUrl = cloudinaryImageService.upload(file);
            editingVacancy.setImage(fileUrl);
            return vacancyRepository.save(editingVacancy);
        }
        return null;
    }

    public boolean deleteVacancy(Integer id){
        try {
            vacancyRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
