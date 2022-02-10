package at.kruiss.food;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Food {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String pic;
    private Integer categoryId;

    // region getter/setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String field) {
        this.name = field;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer amount) {
        this.categoryId = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    // endregion

    public FoodDTO getDTO(){
        var dto = new FoodDTO();
        dto.setName(getName());
        dto.setCategoryId(getCategoryId());
        dto.setId(getId());
        dto.setPrice(getPrice());
        dto.setDescription(getDescription());
        dto.setPic(getPic());
        return dto;
    }
}
