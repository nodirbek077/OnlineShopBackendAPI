//package uz.supersite;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import uz.supersite.entity.Category;
//import uz.supersite.repository.CategoryRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//public class CategoryRepositoryTest {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Test
//    public void testAddSuccess(){
//        Category category = new Category();
//        category.setName("Electronics");
//        category.setParent(null);
//        category.setEnabled(true);
//        category.setImage("category_image.png");
//        category.setChildren(null);
//        category.setHasChildren(false);
//
//        Category savedCategory = categoryRepository.save(category);
//        Assertions.assertThat(savedCategory).isNotNull();
//        Assertions.assertThat(savedCategory.getName()).isEqualTo("Electronics");
//    }
//
//    @Test
//    public void testListSuccess(){
//        List<Category> categories = (List<Category>) categoryRepository.findAll();
//        Assertions.assertThat(categories).isNotEmpty();
//        categories.forEach(System.out::println);
//    }
//
//
//    @Test
//    public void testGetNotFound(){
//        Integer id = 1;
//        Optional<Category> category = categoryRepository.findById(id);
//        if(category.isPresent()){
//            Category category1 = category.get();
//            Assertions.assertThat(category1).isNull();
//        }
//    }
//
//
//    @Test
//    public void testGetFound(){
//        Integer id = 1;
//        Optional<Category> category = categoryRepository.findById(id);
//        if(category.isPresent()){
//            Category category1 = category.get();
//            Assertions.assertThat(category1).isNotNull();
//        }
//    }
//
//    @Test
//    public void testListRootCategories() {
//        List<Category> rootCategories = categoryRepository.findRootCategories();
//        rootCategories.forEach(cat -> System.out.println(cat.getName()));
//    }
//
//    @Test
//    public void testPrintHierarchicalCategories(){
//        Iterable<Category> categories = categoryRepository.findAll();
//        for (Category category : categories){
//            if (category.getParent() == null){
//                System.out.println(category.getName());
//                Set<Category> children = category.getChildren();
//                for (Category subCategory : children){
//                    System.out.println("--" + subCategory.getName());
//                    testPrintChildCategories(subCategory,1);
//                }
//            }
//        }
//    }
//    private void testPrintChildCategories(Category parent, int subLevel){
//        int newSubLevel = subLevel + 1;
//        Set<Category> children = parent.getChildren();
//
//        for (Category subCategory : children){
//            for(int i = 0; i < newSubLevel; i++){
//                System.out.println("--");
//            }
//            System.out.println(subCategory.getName());
//            testPrintChildCategories(subCategory, newSubLevel);
//        }
//    }
//}
