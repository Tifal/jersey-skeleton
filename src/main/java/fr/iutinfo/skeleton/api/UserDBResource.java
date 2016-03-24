package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {
	private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);


    public UserDBResource() {
		try {
			dao.dropUserTable();
			dao.createUserTable();
			User admin = new User(0, "admin", "admin");
			admin.setPassword("admin");
			dao.insert(admin);
			dao.insert(new User(0, "Maxime Picault", "Le flemmard", "12 rue de la flem"));
			dao.insert(new User(0, "Jean Jacques", "Rousseau", "12 rue des livres"));
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}
	
	@POST
	public User createUser(User user) {
        user.resetPasswordHash();
        int id = dao.insert(user);
        user.setId(id);
		return user;
	}

	@GET
	@Path("/{name}")
	public User getUser(@PathParam("name") String name) {
		User user = dao.findByName(name);
		if (user == null) {
			throw new WebApplicationException(404);
		}
		return user;
	}

	@GET
	public List<User> getAllUsers() {
		return dao.all();
	}

}
