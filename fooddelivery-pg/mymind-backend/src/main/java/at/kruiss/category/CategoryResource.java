package at.kruiss.category;

import at.kruiss.food.Food;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/category")
public class CategoryResource {
    private final CategoryService service;

    public CategoryResource(CategoryService cs){
        service = cs;
    }

    @Transactional
    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CategoryDTO add(CategoryDTO dto){
        var toAdd = service.add(dto);
        if(toAdd == null){
            return null;
        }

        return toAdd.getDTO();
    }

    @Transactional
    @Path("add/many")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object[] addMany(List<CategoryDTO> dtos){
        List<CategoryDTO> resultDTOs = new ArrayList<>();
        dtos.forEach(categoryDTO ->
        {
            resultDTOs.add(service.add(categoryDTO).getDTO());
        });

        return resultDTOs.toArray();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/user/{uname}")
    public List<CategoryDTO> getForUser(@PathParam("uname") String username){
        //return service.getFoodsForUser(username).stream().map(Food::getDTO).collect(Collectors.toList());
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{id}")
    public CategoryDTO getById(@PathParam("id") Integer id){
        return service.getCategoryById(id).getDTO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/all")
    public List<CategoryDTO> getAll(){
        return service.getAllCategory().stream().map(Category::getDTO).collect(Collectors.toList());
    }
}
