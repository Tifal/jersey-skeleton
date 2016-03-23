package fr.iutinfo.skeleton.api.command;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.User;

public class Command {
	final static Logger logger = LoggerFactory.getLogger(User.class);

	private int id = 0;
	private int userid = 0;
	private String addressRetrait;
	private String addressLivraison;
	private Date dateRetrait;
	private Date dateLivraison;
	private double price = 40.0;
	private boolean paid;
	

	public Command(int id, int userid, String addressRetrait, String addressLivraison, Date dateRetrait,
			Date dateLivraison) {
		

	}

}
