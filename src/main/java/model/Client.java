package model;

public class Client extends Person{

    private long clientID;
    private String enterprise;

    public Client(String firstname, String lastname) {
        super(firstname, lastname);
    }

    public Client (long clientID, String firstname, String lastname){

        super(firstname, lastname);
        this.clientID = clientID;
    }

    public Client(String firstname, String lastname, String enterprise) {
        super(firstname, lastname);
        this.enterprise = enterprise;
    }

    public Client(long clientID, String firstname, String lastname, String enterprise) {
        super(firstname, lastname);
        this.clientID = clientID;
        this.enterprise = enterprise;
    }

    //getter und setter
    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
