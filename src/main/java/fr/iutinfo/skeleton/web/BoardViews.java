package fr.iutinfo.skeleton.web;

import java.util.GregorianCalendar;

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

	public BoardViews() {
		try {
			dao.dropCommandTable();
			dao.createCommandTable();
			dao.insert(new Command(0, 3, "rue du test unitaire", "rue du bug", "02/10/16 17:30", "03/10/16 17:30", "50.0",
					"LINGE_QUOTIDIEN_REPASSAGE_PLIAGE:1;LINGE_QUOTIDIEN_LAVAGE_SECHAGE:2"));
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}

	@GET
	@Template
	public String getAll(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		if (currentUser == null || !User.isAdmin(currentUser)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"")
					.entity("Ressource requires login.").build());
		}
		
		GregorianCalendar aujourdhui = new GregorianCalendar();

		int commandes = dao.all().size();
		int newCommanes = 0;
		double ca = 0, newCa = 0;

		for (Command command : dao.all()) {
			String[] dateData = command.getDateLivraison().split(" ")[0].split("/");
			GregorianCalendar livraison = new GregorianCalendar(Integer.valueOf("20" + dateData[2]), Integer.valueOf(dateData[1]), Integer.valueOf(dateData[0]));

			double val = Double.valueOf(command.getPrice());
			
			if (aujourdhui.before(livraison)) {
				newCommanes ++;
				newCa += val;
			}
			
			ca += val;
		}

		return String.format(
				"<li class=\"list-group-item\">Depuis le debut</li><li class=\"list-group-item\">Nombre de commandes : %d</li><li class=\"list-group-item\">Chiffre d'affaire : %.2f€</li>",
				commandes, ca);
	}

}
