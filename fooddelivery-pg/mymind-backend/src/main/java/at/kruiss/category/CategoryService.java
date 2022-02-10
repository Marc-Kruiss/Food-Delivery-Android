package at.kruiss.category;

import at.kruiss.food.Food;
import at.kruiss.food.FoodDTO;
import at.kruiss.food.FoodRepository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class CategoryService {
    private final CategoryRepository repo;
    //private final AppUserRepository userRepo;

    public CategoryService(CategoryRepository repo/*,AppUserRepository userRepo*/) {
        this.repo = repo;
        //this.userRepo = userRepo;
    }

    public Category add(CategoryDTO dto){
        var category = new Category();
        //var user = userRepo.getUserByUsername(dto.getUser());

        //feeling.setColorCode(dto.getColorCode());
        category.setName(dto.getName());
        category.setPic(dto.getPic());
        repo.add(category);

        return category;
    }
    /*
    public List<Food> getFoodsForUser(String username){
        //return repo.getFoodsForUser(username);
        return null;
    }*/

    public Category getCategoryById(Integer id){
        return repo.getById(id);
    }

    public List<Category> getAllCategory(){
        return repo.getAllCategory();
    }
}
