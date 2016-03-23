package fr.iutinfo.skeleton.api.price;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;

@Path("/pricedb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Pricing {
	private static PriceDao dao = BDDFactory.getDbi().open(PriceDao.class);
	final static Logger logger = LoggerFactory.getLogger(Price.class);
	
	public Pricing() {
		try {
			dao.dropPriceTable();
			dao.createPriceTable();
			
			BufferedReader reader = new BufferedReader(new FileReader(new File("pricing")));
			
			String price = null;
			
			while ((price = reader.readLine()) != null) {
				String[] data = price.split(":");
				dao.insert(new Price(data[0], data[1]));
			}
			
			reader.close();
		} catch (Exception e) {
			
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Price> getAllPrices() {
		return dao.all();
	}
}
