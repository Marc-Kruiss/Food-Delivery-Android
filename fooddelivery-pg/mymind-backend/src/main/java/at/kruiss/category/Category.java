package at.kruiss.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String pic;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    // endregion

    public CategoryDTO getDTO(){
        var dto = new CategoryDTO();
        dto.setName(getName());
        dto.setId(getId());
        dto.setPic(getPic());
        return dto;
    }
}
