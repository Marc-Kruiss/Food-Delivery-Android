package at.kruiss.category;

import at.kruiss.food.Food;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import java.util.List;

@RequestScoped
public class CategoryRepository {
    private final EntityManager em;

    public CategoryRepository(EntityManager em){
        this.em = em;
    }

    public void add(Category v){
        em.persist(v);
    }

    public List<Category> getCategoriesForUser(String name){
        var qry = em.createQuery("select v from Category v where v.name = :name", Category.class);
        qry.setParameter("name", name);
        return qry.getResultList();
    }

    public Category getById(Integer id){
        var qry = em.createQuery("select v from Category v where v.id = :id", Category.class);
        qry.setParameter("id", id);
        return qry.getResultStream().findFirst().orElse(null);
    }

    public List<Category> getAllCategory(){
        var qry = em.createQuery("select v from Category v", Category.class);
        return qry.getResultList();
    }
}
