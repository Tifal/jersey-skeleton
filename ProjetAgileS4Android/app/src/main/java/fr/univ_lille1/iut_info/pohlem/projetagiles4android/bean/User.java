package fr.univ_lille1.iut_info.pohlem.projetagiles4android.bean;

public class User {

    private int id;
    private String name;
    private String alias;
    private String address;;
    private String passwdHash;

    public User() {

    }


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }

    @Override
    public String toString() {
        String s = getId() + " : ";
        s+= getAlias() != null ? getAlias()+" (Nom:" + getName() + ", addresse: " + getAddress() + ")" : "Erreur: pas d'alias";
        return s;
    }

}