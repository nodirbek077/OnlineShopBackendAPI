package uz.supersite.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long barCode;

    @Column(unique = true, length = 255, nullable = false)
    private String nameUz;

    @Column(unique = true, length = 255, nullable = false)
    private String nameRu;

    @Column(unique = true, length = 255, nullable = false)
    private String nameEn;

    @Column(unique = true, length = 255, nullable = false)
    private String aliasUz;

    @Column(unique = true, length = 255, nullable = false)
    private String aliasRu;

    @Column(unique = true, length = 255, nullable = false)
    private String aliasEn;

    @Column(name = "short_description", length = 512, nullable = false)
    private String shortDescription;

    @Column(name = "full_description", length = 4096, nullable = false)
    private String fullDescription;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private Date createdTime;

    @CreationTimestamp
    @Column(name = "updated_time")
    private Date updatedTime;

    private boolean enabled;

    @Column(name = "in_stock")
    private boolean inStock;

    private float cost;

    private float price;

    private String size;

    private String color;

    @Column(name = "discount_percent")
    private float discountPercent;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Product() {
    }

    public Product(Integer id, String nameUz,String nameRu,String nameEn){
        this.id = id;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    public Product(Integer id, Long barCode, String nameUz, String nameRu, String nameEn, String aliasUz, String aliasRu, String aliasEn, String shortDescription, String fullDescription, Date createdTime, Date updatedTime, boolean enabled, boolean inStock, float cost, float price, String size, String color, float discountPercent, Category category, Brand brand) {
        this.id = id;
        this.barCode = barCode;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.aliasUz = aliasUz;
        this.aliasRu = aliasRu;
        this.aliasEn = aliasEn;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.enabled = enabled;
        this.inStock = inStock;
        this.cost = cost;
        this.price = price;
        this.size = size;
        this.color = color;
        this.discountPercent = discountPercent;
        this.category = category;
        this.brand = brand;
    }

    public String getNameUz() {
        return nameUz;
    }

    public void setNameUz(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBarCode() {
        return barCode;
    }

    public void setBarCode(Long barCode) {
        this.barCode = barCode;
    }

    public String getAliasUz() {
        return aliasUz;
    }

    public void setAliasUz(String aliasUz) {
        this.aliasUz = aliasUz;
    }

    public String getAliasRu() {
        return aliasRu;
    }

    public void setAliasRu(String aliasRu) {
        this.aliasRu = aliasRu;
    }

    public String getAliasEn() {
        return aliasEn;
    }

    public void setAliasEn(String aliasEn) {
        this.aliasEn = aliasEn;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameUz='" + nameUz + '\'' +
                ", nameRu='" + nameRu + '\'' +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }
}
