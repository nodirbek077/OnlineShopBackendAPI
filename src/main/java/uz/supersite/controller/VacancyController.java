package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Vacancy;
import uz.supersite.service.VacancyService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;

    }
    @GetMapping()
    public ResponseEntity<?> getVacanciesByPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        List<Vacancy> vacancies = vacancyService.getVacancies(page,size);
        return ResponseEntity.ok(vacancies);
    }
    @GetMapping("/page/{pageNum}")
    public ResponseEntity<?> getVacanciesByFieldSortable(@PathVariable("pageNum") int pageNum, @RequestParam String sortField,@RequestParam String sortDir){
        List<Vacancy> vacanciesByPage = vacancyService.getVacanciesByPageSort(pageNum, sortField, sortDir);
        return ResponseEntity.ok(vacanciesByPage);
    }

    @GetMapping("/byTitle")
    public ResponseEntity<?> getVacanciesByTitleSortable(@RequestParam(defaultValue = "title") String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        List<Vacancy> vacanciesBySortTitle = vacancyService.getVacanciesBySortTitle(title,page,size);
        return ResponseEntity.ok(vacanciesBySortTitle);
    }
    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@Valid Vacancy vacancy, MultipartFile file) {
        Vacancy addedVacancy = vacancyService.add(vacancy, file);
        return ResponseEntity.status(201).body(addedVacancy);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getVacancy(@PathVariable Integer id){
       Vacancy vacancy =  vacancyService.getVacancy(id);
       return ResponseEntity.status(vacancy != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(vacancy);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> editVacancy(@PathVariable Integer id, @Valid Vacancy vacancy, @RequestPart MultipartFile file){
        Vacancy editedVacancy = vacancyService.editVacancy(id, vacancy,file);
        return ResponseEntity.status(editedVacancy != null ? 202 : 409).body(editedVacancy);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteVacancy(@PathVariable Integer id){
        boolean deleted = vacancyService.deleteVacancy(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
