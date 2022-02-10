package at.kruiss.food;


import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class FoodService {
    private final FoodRepository repo;
    //private final AppUserRepository userRepo;

    public FoodService(FoodRepository repo/*,AppUserRepository userRepo*/) {
        this.repo = repo;
        //this.userRepo = userRepo;
    }

    public Food add(FoodDTO dto){
        var food = new Food();
        food.setName(dto.getName());
        food.setCategoryId(dto.getCategoryId());
        food.setPrice(dto.getPrice());
        food.setDescription(dto.getDescription());
        food.setPic(dto.getPic());
        repo.add(food);

        return food;
    }
    /*
    public List<Food> getFoodsForUser(String username){
        //return repo.getFoodsForUser(username);
        return null;
    }*/

    public Food getFoodById(Integer id){
        return repo.getById(id);
    }

    public List<Food> getFoodByCategory(Integer category_id){
        return repo.getFoodByCategory(category_id);
    }

    public List<Food> getAllFood(){
        return repo.getAllFood();
    }


}