package fr.iutinfo.skeleton.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.server.mvc.Template;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.command.Command;
import fr.iutinfo.skeleton.api.command.CommandDao;

@Path("/board")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class BoardViews {
	private static CommandDao dao = BDDFactory.getDbi().open(CommandDao.class);
	
	@GET
	@Template
	public String getAll(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
        if (currentUser == null || !User.isAdmin(currentUser)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
        }
        
        int commandes = dao.all().size();
        double ca = 0;
		
		for(Command command : dao.all()){
			ca += Double.valueOf(command.getPrice());
		}
		
		return String.format("<li class=\"list-group-item\">Nombre de commandes : %d</li><li class=\"list-group-item\">Chiffre d'affaire : %.2f€</li>", commandes, ca);
	}

}
