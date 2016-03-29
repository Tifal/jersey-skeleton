package fr.iutinfo.skeleton.api.command;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.price.Price;
import fr.iutinfo.skeleton.api.price.PriceDao;

@Path("/commanddb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandDBResource {
	private static CommandDao dao = BDDFactory.getDbi().open(CommandDao.class);
	private static PriceDao priceDao = BDDFactory.getDbi().open(PriceDao.class);
	final static Logger logger = LoggerFactory.getLogger(CommandDBResource.class);

	public CommandDBResource() {
		try {
			//dao.dropCommandTable();
			dao.createCommandTable();
			dao.insert(new Command(0, 3, "rue du test unitaire", "rue du bug", "02/10/16 17:30", "03/10/16 17:30", "50.0",
					"LINGE_QUOTIDIEN_REPASSAGE_PLIAGE:1;LINGE_QUOTIDIEN_LAVAGE_SECHAGE:2"));
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}

	@POST
	public Command createCommand(@Context SecurityContext context, Command command) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Ressource requires login.").build());
		}

		if (command.getUserid() != currentUser.getId()) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Cannot access to this ressource.").build());
		}

		command.setPaid(0);

		// calculate command price

		List<Price> prices = priceDao.all();

		double total = 0;
		String[] data = command.getDetails().split(";");

		for (String d : data) {
			String[] row = d.split(":");

			for (Price price : prices) {
				if (price.getItem().equals(row[0])) {
					total += Integer.valueOf(row[1]) * Double.valueOf(price.getPrice());
					break;
				}
			}
		}
		
		command.setPrice(String.format("%.2f",total).replaceAll(",", "."));

		System.err.println("command.getDateLivraison(): "+command.getDateLivraison());
		System.out.println(command.toString());
		
		int id = dao.insert(command);
		command.setId(id);
		return command;
	}

	@GET
	@Path("/{id}")
	public Command getCommand(@Context SecurityContext context, @PathParam("id") int id) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Ressource requires login.").build());
		}

		Command command = dao.findById(id);

		if (command == null) {
			throw new WebApplicationException(404);
		}

		if (command.getUserid() != currentUser.getId()) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Cannot access to this ressource.").build());
		}

		return command;
	}

	@PUT
	@Path("{id}")
	public Command updateCommand(@Context SecurityContext context, @PathParam("id") int id, Command command) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Ressource requires login.").build());
		}

		Command oldUser = dao.findById(id);

		if (oldUser == null) {
			throw new WebApplicationException(404);
		}

		if (command.getUserid() != currentUser.getId()) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Cannot access to this ressource.").build());
		}

		dao.update(command);
		return command;
	}

	@GET
	public List<Command> getAllUsers(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Ressource requires login.").build());
		}

		return dao.findAllByUserId(currentUser.getId());
	}

}
