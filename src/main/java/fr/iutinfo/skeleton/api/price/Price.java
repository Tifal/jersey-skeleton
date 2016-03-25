package fr.iutinfo.skeleton.api.price;

import java.security.Principal;

public class Price implements Principal {
	
	private String item;
	private String price;
	
	public Price() {

	}
	
	public Price(String item, String price) {
		this.item = item;
		this.price = price;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getPrice() {
		return price;
	}

	@Override
	public String getName() {
		return item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
            return false;
        Price price = (Price) obj;
        return item.equals(price.item) && this.price == price.price;

	}
	
	@Override
	public String toString() {
		return item.replaceAll("_", " ").toLowerCase();
	}
}
