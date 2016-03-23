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
	private String dateRetrait;
	private String dateLivraison;
	private String price = "40.0";
	private int paid = 0;

	public Command() {

	}

	public Command(int id, int userid, String addressRetrait, String addressLivraison, String dateRetrait,
			String dateLivraison, String price) {
		this.id = id;
		this.userid = userid;
		this.addressRetrait = addressRetrait;
		this.addressLivraison = addressLivraison;
		this.dateRetrait = dateRetrait;
		this.dateLivraison = dateLivraison;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAddressRetrait() {
		return addressRetrait;
	}

	public void setAddressRetrait(String addressRetrait) {
		this.addressRetrait = addressRetrait;
	}

	public String getAddressLivraison() {
		return addressLivraison;
	}

	public void setAddressLivraison(String addressLivraison) {
		this.addressLivraison = addressLivraison;
	}

	public String getDateRetrait() {
		return dateRetrait;
	}

	public void setDateRetrait(String dateRetrait) {
		this.dateRetrait = dateRetrait;
	}

	public String getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(String dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int isPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

}
