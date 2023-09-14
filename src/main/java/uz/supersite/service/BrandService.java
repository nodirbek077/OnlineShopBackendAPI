package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Brand;
import uz.supersite.entity.Category;
import uz.supersite.repository.BrandRepository;
import uz.supersite.utils.FileUploadUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;

    public List<Brand> getBrandsByPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Brand> newsPage = brandRepository.findAll(pageable);
        return newsPage.getContent();
    }

    public Brand get(Integer id) {
        Optional<Brand> optionalCategory = brandRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    public Brand add(Brand brand, MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            brand.setLogo(cloudinaryImageService.upload(file));
            Brand savedBrand = brandRepository.save(brand);
            String uploadDir = "brand-photos/" + savedBrand.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, file.getOriginalFilename(), file);
        }else {
            if(brand.getLogo().isEmpty()) brand.setLogo(null);
            brandRepository.save(brand);
        }
        return brand;
    }

    public Brand updateBrand(Integer id, Brand brandInRequest, MultipartFile file) throws IOException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()){
            Brand editingBrand = optionalBrand.get();
            editingBrand.setNameUz(brandInRequest.getNameUz());
            editingBrand.setNameRu(brandInRequest.getNameRu());
            editingBrand.setNameEn(brandInRequest.getNameEn());
            editingBrand.setCategories(brandInRequest.getCategories());

            String fileUrl = cloudinaryImageService.upload(file);
            editingBrand.setLogo(fileUrl);

            Brand savedBrand = brandRepository.save(editingBrand);
            String uploadDir = "brand-photos/" + savedBrand.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, file.getOriginalFilename(), file);
            return savedBrand;
        }
        return null;
    }

    public boolean delete(Integer id){
        try {
            brandRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
