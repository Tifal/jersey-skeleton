package fr.iutinfo.skeleton.web;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.command.Command;
import fr.iutinfo.skeleton.api.command.CommandDao;

@Path("/planning")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class PlanningViews {
	private static CommandDao dao = BDDFactory.getDbi().open(CommandDao.class);

	@GET
	@Template
	public List<Command> getAll() {
		List<Command> commands = new ArrayList<Command>();
		GregorianCalendar aujourdhui = new GregorianCalendar();
		
		for(Command command : dao.all()){
			String[] dateData = command.getDateLivraison().split("/");
			GregorianCalendar livraison = new GregorianCalendar(Integer.valueOf("20" + dateData[2]), Integer.valueOf(dateData[1]), Integer.valueOf(dateData[0]));
			
			if (aujourdhui.before(livraison))
				commands.add(command);
		}
		return commands;
	}
}
