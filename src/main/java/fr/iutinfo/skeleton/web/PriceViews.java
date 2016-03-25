package fr.iutinfo.skeleton.web;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import fr.iutinfo.skeleton.api.price.Price;
import fr.iutinfo.skeleton.api.price.PriceDao;

@Path("/pricing")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class PriceViews {
	private static PriceDao dao = BDDFactory.getDbi().open(PriceDao.class);
	
	@GET
    @Template
    public List<Price> getAll(@Context SecurityContext context) {
    	User currentUser = (User) context.getUserPrincipal();
        if (currentUser == null || !User.isAdmin(currentUser)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
        }
        
        return dao.all();
    }
	
	@POST
    @Template(name = "changeprice")
    @Path("/{item}")
    public void getDetail(@Context SecurityContext context, @PathParam("item") String item, @FormParam("price") String price) {
    	User currentUser = (User) context.getUserPrincipal();
        if (currentUser == null || !User.isAdmin(currentUser)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
        }
        
        System.out.println("set price of " + item + " to " + price);
        
        Price p = dao.findByName(item);
        
        if (p == null)
        	return;
        
        p.setPrice(price);
        
        dao.update(p);

        return;
    }

}
