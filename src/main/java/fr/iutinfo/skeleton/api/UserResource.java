package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private static Map<Integer, User> users = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(UserResource.class);

    @POST

    public User createUser(User user) {
        int id = users.size();
        user.setId(id + 1);
        users.put(user.getId(), user);
        return user;
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@Context SecurityContext context, @PathParam("id") Integer id) {
    	User currentUser = (User) context.getUserPrincipal();
        logger.debug("Current User :"+ currentUser.toString());
        if (User.isAnonymous(currentUser)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
        }
        
        if (id != currentUser.getId()) {
        	throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Cannot access to this ressource.").build());
        }
    	
    	if (users.containsKey(id)) {
            return Response.accepted().status(Status.ACCEPTED).build();
        }
        return Response.accepted().status(Status.NOT_FOUND).build();
    }

    protected User find(String name) {
        for (User user : users.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    protected User find(int id) {
        return users.get(id);
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") int id,
                               User user) {
        User oldUser = find(id);
        logger.info("Should update user with id: " + id + " (" + oldUser + ") to " + user);
        if (user == null) {
            throw new WebApplicationException(404);
        }
        oldUser.setName(user.getName());
        return Response.status(200).entity(oldUser).build();
    }

    @GET
    @Path("/{name}")
    public User getUser(@Context SecurityContext context, @PathParam("name") String name) {
    	User currentUser = (User) context.getUserPrincipal();
        logger.debug("Current User :"+ currentUser.toString());
        if (User.isAnonymous(currentUser)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
        }
        
        User out = find(name);
        if (out == null) {
            throw new WebApplicationException(404);
        }
        
        if (out.getId() != currentUser.getId()) {
        	throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Cannot access to this ressource.").build());
        }
        
        return out;
    }

    @GET
    public List<User> getUsers(@DefaultValue("10") @QueryParam("limit") int limit) {
        return new ArrayList<>(users.values());
    }

}
