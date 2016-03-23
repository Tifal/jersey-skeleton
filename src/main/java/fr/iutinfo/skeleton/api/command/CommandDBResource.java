package fr.iutinfo.skeleton.api.command;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.BDDFactory;

@Path("/commanddb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandDBResource {
	private static CommandDao dao = BDDFactory.getDbi().open(CommandDao.class);
    final static Logger logger = LoggerFactory.getLogger(CommandDBResource.class);
	public CommandDBResource() {
		try {
			dao.dropCommandTable();
			dao.createCommandTable();
			dao.insert(new Command(0, 1, "rue du test", "rue du bug", "02/10/16", "03/10/16", "50.0"));
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}
	
	@GET
	public List<Command> getAllUsers() {
		return dao.all();
	}

}
