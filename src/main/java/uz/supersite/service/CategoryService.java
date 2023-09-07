package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.supersite.ResponseEntity;
import uz.supersite.entity.Category;
import uz.supersite.entity.User;
import uz.supersite.exception.CategoryNotFoundException;
import uz.supersite.exception.CustomPropertyValueException;
import uz.supersite.exception.UserNotFoundException;
import uz.supersite.repository.CategoryRepository;

import java.util.*;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
	}

	public Category add(Category category){
		if(category.getParent() == null){
			return categoryRepository.save(category);
		}
return null;
//		getCategoryByParentId();
	}

	private List<Category> getChildCategoriesByParentId() {
		List<Category> categoryList = new ArrayList<>();
		List<Category> rootCategoriesInDb = categoryRepository.findRootCategories();

		for(Category category : rootCategoriesInDb){
		    if(category.getParent() == null){

				categoryList.add(Category.copyIdAndName(category));

				Set<Category> children = category.getChildren();
				for (Category subCategory : children){
					categoryList.add(Category.copyIdAndName(subCategory.getId(), subCategory.getName()));
					getSubCategories(categoryList, subCategory);
				}

			}
		}

		return categoryList;
	}

	private void getSubCategories(List<Category> categories, Category category){
		Set<Category> children = category.getChildren();

		for(Category subCategory : children){
			categories.add(Category.copyIdAndName(subCategory.getId(), subCategory.getName()));
			getSubCategories(categories, subCategory);
		}
	}

	public List<Category> getRootCategories(){
		return categoryRepository.findRootCategories();
	}

	public List<Category> list(){
//		return (List<Category>) categoryRepository.findAll();
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

	public Category get(Integer id) throws CategoryNotFoundException {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(optionalCategory.isPresent()){
            return optionalCategory.get();
		}else {
			throw new CategoryNotFoundException("category not found id " + id);
		}
	}

	public Category update(Category categoryInRequest, Integer id) throws CategoryNotFoundException {
		Optional<Category> categoryInDb = categoryRepository.findById(id);

		if(categoryInDb.isEmpty()){
			throw new CategoryNotFoundException("No category found with the given id: " + id);
		}

		Category category = categoryInDb.get();
		category.setName(categoryInRequest.getName());
		category.setAlias(categoryInRequest.getAlias());
		category.setChildren(categoryInRequest.getChildren());
		category.setImage(categoryInRequest.getImage());
		category.setEnabled(categoryInRequest.isEnabled());
		category.setParent(categoryInRequest.getParent());
		return categoryRepository.save(category);
	}

	public void delete(Integer id) throws CategoryNotFoundException {
		if(!categoryRepository.existsById(id)){
			throw new CategoryNotFoundException("No category find with ID: " + id);
		}
		categoryRepository.deleteById(id);
	}


}
