package uz.supersite.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 128, nullable = false, unique = true)
	@JsonProperty("name")
	@NotNull
	@NotBlank(message = "Category name cannot be left blank")
	private String name;

	@Column(length = 64, nullable = false, unique = true)
	@JsonProperty("alias")
	@NotBlank(message = "Alias cannot be left blank")
	private String alias;

	@Column(length = 128, nullable = false)
	@JsonProperty("image")
	private String image;

	private boolean enabled;

	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")

	private Set<Category> children = new HashSet<>();

	@Transient
	private boolean hasChildren;


	public Category() {
	}
	
	public Category(Integer id) {
		this.id = id;
	}
	
	public Category(Integer id, String name, String alias) {
		this.id = id;
		this.name = name;
	}

	public static Category copyIdAndName(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		
		return copyCategory;
	}
	
	public static Category copyIdAndName(Integer id, String name) {
		Category copyCategory = new Category();
		copyCategory.setId(id);
		copyCategory.setName(name);
		
		return copyCategory;
	}
	
	public static Category fullCopy(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		copyCategory.setImage(category.getImage());
		copyCategory.setEnabled(category.isEnabled());
		copyCategory.setHasChildren(!category.getChildren().isEmpty());
		return copyCategory;
	}
	
	public static Category fullCopy(Category category, String name) {
		Category copyCategory = Category.fullCopy(category);
		copyCategory.setName(name);
		
		return copyCategory;
	}

	public Category(String name) {
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}
	
	public Category(String name, Category parent) {
		this(name);
		this.parent = parent;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	@Transient
	@JsonIgnore
	public String getImagePath() {
		if(this.id == null) return "/images/image-thumbnail.png";
		
		return "/category-images/" + this.id + "/" + this.image;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category category = (Category) o;
		return Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
