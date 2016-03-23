package fr.iutinfo.skeleton.web;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.command.Command;
import fr.iutinfo.skeleton.api.command.CommandDao;

@Path("/command")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class CommandViews {
	private static CommandDao dao = BDDFactory.getDbi().open(CommandDao.class);
	
	@GET
    @Template
    public List<Command> getAll() {
        return dao.all();
    }

    @GET
    @Template(name = "detail")
    @Path("/{id}")
    public Command getDetail(@PathParam("id") String id) {
        Command command = dao.findById(Integer.parseInt(id));
        if (command == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return command;
    }

}
