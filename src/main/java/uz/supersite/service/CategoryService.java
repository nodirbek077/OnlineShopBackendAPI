package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Category;
import uz.supersite.repository.CategoryRepository;

import java.util.*;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CloudinaryImageServiceImpl cloudinaryImageService;

	public Category add(Category category, MultipartFile file){
		if(!file.isEmpty()){
			String fileUrl = cloudinaryImageService.upload(file);
			category.setImage(fileUrl);
			category.setEnabled(true);
			return categoryRepository.save(category);
		}else {
			if(category.getImage().isEmpty()) category.setImage(null);
			return categoryRepository.save(category);
		}
	}
	public List<Category> getRootCategories(){
		return categoryRepository.findRootCategories();
	}

	public List<Category> list(){
		List<Category> rootCategories = categoryRepository.findRootCategories();
		return getListHierarchicalCategories(rootCategories);
	}

	private List<Category> getListHierarchicalCategories(List<Category> rootCategories){
		List<Category> hierarchicalCategories = new ArrayList<>();

		for(Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.fullCopy(rootCategory));

			Set<Category> children = rootCategory.getChildren();
			for(Category subCategory : children) {
				hierarchicalCategories.add(Category.fullCopy(subCategory));
				getListSubHierarchicalCategories(hierarchicalCategories, subCategory);
			}
		}
		return hierarchicalCategories;

	}

	private void getListSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent){
		Set<Category> children = parent.getChildren();
		for (Category subCategory : children){
			hierarchicalCategories.add(Category.fullCopy(subCategory, subCategory.getName()));
			getListSubHierarchicalCategories(hierarchicalCategories, parent);
		}
	}

	public Category get(Integer id){
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		return optionalCategory.orElse(null);
	}

	public Category update(Category categoryInRequest,MultipartFile file, Integer id) {
		Optional<Category> categoryInDb = categoryRepository.findById(id);

		if (categoryInDb.isPresent()){
			Category editingCategory = categoryInDb.get();

			editingCategory.setName(categoryInRequest.getName());
			editingCategory.setAlias(categoryInRequest.getAlias());
			editingCategory.setChildren(categoryInRequest.getChildren());

			String uploadUrl = cloudinaryImageService.upload(file);
			editingCategory.setImage(uploadUrl);

			editingCategory.setEnabled(categoryInRequest.isEnabled());
			editingCategory.setParent(categoryInRequest.getParent());
			return categoryRepository.save(editingCategory);
		}
		return null;
	}

	public void updateCategoryEnabledStatus(Integer id, boolean enabled){
         categoryRepository.updateCategoryEnabledStatus(id, enabled);
    }

	public boolean delete(Integer id) {
		try {
			categoryRepository.deleteById(id);
			return true;
		}catch (Exception e){
			return false;
		}

	}


}
