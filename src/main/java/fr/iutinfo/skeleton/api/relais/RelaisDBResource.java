package fr.iutinfo.skeleton.api.relais;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.BDDFactory;

@Path("/relaisdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelaisDBResource {

	private static RelaisDao dao = BDDFactory.getDbi().open(RelaisDao.class);
    final static Logger logger = LoggerFactory.getLogger(RelaisDBResource.class);
    
	public RelaisDBResource() {
		try {
			dao.createRelaisTable();
			dao.insert(new Relais("Relais factice", "123 rue du relais"));
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}
	
	@GET
	public List<Relais> getAllRelais() {
		return dao.all();
	}
	
}
