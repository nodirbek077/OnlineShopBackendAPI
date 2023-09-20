package uz.supersite.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import uz.supersite.entity.Brand;
import uz.supersite.entity.Category;
import uz.supersite.entity.Product;
import uz.supersite.repository.ProductRepository;

import java.util.Date;
import java.util.Optional;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateProduct(){
        Brand brand = entityManager.find(Brand.class, 7);
        Category category = entityManager.find(Category.class,4);

        Product product = new Product();
        product.setNameUz("Dell Inspiration uz");
        product.setNameRu("Dell Inspiration ru");
        product.setNameEn("Dell Inspiration en");
        product.setAliasUz("dell_inspiration_uz");
        product.setAliasRu("dell_inspiration_ru");
        product.setAliasEn("dell_inspiration_en");
        product.setShortDescription("Short description for Dell Inspiration");
        product.setFullDescription("Full description for Dell Inspiration");
        product.setBarCode(2000000007489L);
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(456);
        product.setCost(400);
        product.setEnabled(true);
        product.setInStock(true);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepository.save(product);
        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListProducts(){
        Iterable<Product> products = productRepository.findAll();
        products.forEach(System.out::println);
    }

    @Test
    public void testGetProduct(){
        Integer id = 3;
        Product product = productRepository.findById(id).get();
        System.out.println(product);

        Assertions.assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct(){
        Integer id = 4;
        Product product = productRepository.findById(id).get();
        product.setPrice(499);

        productRepository.save(product);
        Product updatedProduct = entityManager.find(Product.class, id);
        Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(499);
    }

    @Test
    public void testDeleteProduct(){
        Integer id = 3;
        productRepository.deleteById(id);

        Optional<Product> result = productRepository.findById(id);
        Assertions.assertThat(result.isEmpty());
    }
}
