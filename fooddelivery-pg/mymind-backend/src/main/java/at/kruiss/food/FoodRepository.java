package at.kruiss.food;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import java.util.List;

@RequestScoped
public class FoodRepository {
    private final EntityManager em;

    public FoodRepository(EntityManager em){
        this.em = em;
    }

    public void add(Food v){
        em.persist(v);
    }

    public List<Food> getFoodByName(String name){
        var qry = em.createQuery("select v from Food v where v.name = :name", Food.class);
        qry.setParameter("name", name);
        return qry.getResultList();
    }

    public List<Food> getFoodByCategory(Integer category_id){
        var qry = em.createQuery("select v from Food v where v.categoryId = :category_id", Food.class);
        qry.setParameter("category_id", category_id);
        return qry.getResultList();
    }

    public List<Food> getAllFood(){
        var qry = em.createQuery("select v from Food v", Food.class);
        return qry.getResultList();
    }

    public Food getById(Integer id){
        var qry = em.createQuery("select v from Food v where v.id = :id", Food.class);
        qry.setParameter("id", id);
        return qry.getResultStream().findFirst().orElse(null);
    }
}