package fr.iutinfo.skeleton.web;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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

@Path("/planning")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class PlanningViews {
	private static CommandDao dao = BDDFactory.getDbi().open(CommandDao.class);
	
	public PlanningViews() {
		try {
			//dao.dropCommandTable();
			dao.createCommandTable();
			dao.insert(new Command(0, 3, "rue du test unitaire", "rue du bug", "02/10/16 17:30", "03/10/16 17:30", "50.0", "LINGE_QUOTIDIEN_REPASSAGE_PLIAGE:1;LINGE_QUOTIDIEN_LAVAGE_SECHAGE:2"));
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}

	@GET
	@Template
	public List<Command> getAll(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
        if (currentUser == null || !User.isAdmin(currentUser)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
        }
        
		List<Command> commands = new ArrayList<Command>();
		GregorianCalendar aujourdhui = new GregorianCalendar();
		
		for(Command command : dao.all()){
			try {
				String[] dateData = command.getDateLivraison().split(" ")[0].split("/");
				GregorianCalendar livraison = new GregorianCalendar(Integer.valueOf("20" + dateData[2]), Integer.valueOf(dateData[1]), Integer.valueOf(dateData[0]));
				
				if (aujourdhui.before(livraison))
					commands.add(command);
			} catch (Exception e) {
				
			}
		}
		return commands;
	}
}
