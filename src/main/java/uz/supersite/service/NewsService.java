package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.News;
import uz.supersite.repository.NewsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;

    public List<News> getNewsByPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsRepository.findAll(pageable);
        return newsPage.getContent();
    }

    public News get(Integer id) {
        Optional<News> optionalCategory = newsRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    public News add(News news, MultipartFile file) {
        if(!file.isEmpty()){
            news.setImage(cloudinaryImageService.upload(file));
            news.setLink(news.getLink());
        }else {
            if(news.getImage().isEmpty()) news.setImage(null);
        }
        return newsRepository.save(news);
    }

    public News updateNews(Integer id, News news, MultipartFile file) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isPresent()){
            News editingNews = optionalNews.get();
            editingNews.setTitle(news.getTitle());
            editingNews.setDescription(news.getDescription());
            editingNews.setLink(news.getLink());
            editingNews.setActive(news.isActive());
            String fileUrl = cloudinaryImageService.upload(file);
            editingNews.setImage(fileUrl);
            return newsRepository.save(editingNews);
        }
        return null;
    }

    public boolean delete(Integer id){
        try {
            newsRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
