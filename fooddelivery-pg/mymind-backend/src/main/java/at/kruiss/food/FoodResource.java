package at.kruiss.food;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/food")
public class FoodResource {
    private final FoodService service;

    public FoodResource(FoodService fs){
        service = fs;
    }

    @Transactional
    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public FoodDTO add(FoodDTO dto){
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
    public Object[] addMany(List<FoodDTO> dtos){
        List<FoodDTO> resultDTOs = new ArrayList<>();
        dtos.forEach(foodDTO ->
        {
            resultDTOs.add(service.add(foodDTO).getDTO());
        });

        return resultDTOs.toArray();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/user/{uname}")
    public List<FoodDTO> getForUser(@PathParam("uname") String username){
        //return service.getFoodsForUser(username).stream().map(Food::getDTO).collect(Collectors.toList());
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{id}")
    public FoodDTO getById(@PathParam("id") Integer id){
        return service.getFoodById(id).getDTO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("category/{id}")
    public List<FoodDTO> getByCategory(@PathParam("id") Integer id){
        return service.getFoodByCategory(id).stream().map(Food::getDTO).collect(Collectors.toList());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/all")
    public List<FoodDTO> getAll(){
        return service.getAllFood().stream().map(Food::getDTO).collect(Collectors.toList());
    }
}