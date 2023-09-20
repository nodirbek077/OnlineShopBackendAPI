package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Banner;
import uz.supersite.repository.BannerRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;

    public List<Banner> getBannerByPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Banner> bannerPage = bannerRepository.findAll(pageable);
        return bannerPage.getContent();
    }

    public Banner get(Integer id) {
        Optional<Banner> optionalCategory = bannerRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    public Banner add(Banner banner, MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            banner.setImage(cloudinaryImageService.upload(file));
        }else {
            if(banner.getImage().isEmpty()) banner.setImage(null);
        }
        return bannerRepository.save(banner);
    }

    public Banner updateBanner(Integer id, Banner bannerInRequest, MultipartFile file) throws IOException {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()){
            Banner editingBanner = optionalBanner.get();
            editingBanner.setTitle(bannerInRequest.getTitle());
            editingBanner.setSubtitle(bannerInRequest.getSubtitle());

            String fileUrl = cloudinaryImageService.upload(file);
            editingBanner.setImage(fileUrl);
            return bannerRepository.save(editingBanner);
        }
        return null;
    }

    public boolean delete(Integer id){
        try {
            bannerRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
