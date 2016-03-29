
package fr.iutinfo.skeleton.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {
	private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);


    public UserDBResource() {
		try {
			//dao.createUserTable();
			User admin = new User(0, "admin", "admin");
			admin.setPassword("admin");
			dao.insert(admin);
		} catch (Exception e) {
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
