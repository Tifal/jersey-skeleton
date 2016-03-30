package fr.univ_lille1.iut_info.pohlem.projetagiles4android.bean;

public class Cmd {

    private int id;
    private int userid;

    private String addressLivraison;
    private String addressRetrait;
    private String dateLivraison;;
    private String dateRetrait;
    private String details;
    private boolean paid;
    private String price;

    public Cmd() { }

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

    public String getAddressLivraison() {
        return addressLivraison;
    }

    public void setAddressLivraison(String addressLivraison) {
        this.addressLivraison = addressLivraison;
    }

    public String getAddressRetrait() {
        return addressRetrait;
    }

    public void setAddressRetrait(String addressRetrait) {
        this.addressRetrait = addressRetrait;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(String dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String s = getId()+", client " + getUserid()+" : \n";
        s+= " - addresse retrait : \n"+getAddressRetrait()+"\n";
        s+= " - date retrait : \n"+getDateRetrait()+"\n";
        s+= " - addresse livraison : \n"+getAddressLivraison()+"\n";
        s+= " - date livraison : \n"+getDateLivraison()+"\n";
        s+= " - prix : \n"+getPrice()+"\n";
        return s;
    }
}