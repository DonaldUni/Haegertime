package model;

import model.enumeration.Power;
import model.enumeration.Status;

public class Employee extends Person{

    private long employeeNummer;
    private String userName;
    private String password;
    private String email;
    private Power power = Power.Employee;
    private Status status = Status.actived;

    private float numberOfUsedHoliday = 0;
    private float numberOfRestHoliday = 0;
    private float numberOfSickDay = 0 ;
    private final int NUMBEROFHOLIDAY = 30;

    private String logged = "false";


    public Employee(String firstname, String lastname, long employeeNummer, String userName, String password, String email) {
        super(firstname, lastname);
        this.employeeNummer = employeeNummer;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public Employee(String firstname, String lastname, long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay) {
        super(firstname, lastname);
        this.employeeNummer = employeeNummer;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.power = power;
        this.status = status;
        this.numberOfUsedHoliday = numberOfUsedHoliday;
        this.numberOfRestHoliday = numberOfRestHoliday;
        this.numberOfSickDay = numberOfSickDay;
    }

    public Employee(long id, String firstname, String lastname,long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay) {
        super(id, firstname, lastname);
        this.employeeNummer = employeeNummer;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.power = power;
        this.status = status;
        this.numberOfUsedHoliday = numberOfUsedHoliday;
        this.numberOfRestHoliday = numberOfRestHoliday;
        this.numberOfSickDay = numberOfSickDay;
    }

    public Employee(long id, String firstname, String lastname,long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay, String logged) {
        super(id, firstname, lastname);
        this.employeeNummer = employeeNummer;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.power = power;
        this.status = status;
        this.numberOfUsedHoliday = numberOfUsedHoliday;
        this.numberOfRestHoliday = numberOfRestHoliday;
        this.numberOfSickDay = numberOfSickDay;
        this.logged = logged;
    }



    //getter und setter
    public long getEmployeeNummer() {
        return employeeNummer;
    }

    public void setEmployeeNummer(long employeeNummer) {
        this.employeeNummer = employeeNummer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public float getNumberOfUsedHoliday() {
        return numberOfUsedHoliday;
    }

    public void setNumberOfUsedAndRestHoliday(float numberOfUsedHoliday) {

        this.numberOfUsedHoliday = numberOfUsedHoliday;
        this.numberOfRestHoliday = NUMBEROFHOLIDAY - numberOfUsedHoliday;
    }

    public float getNumberOfRestHoliday() {
        return numberOfRestHoliday;
    }

    public void setNumberOfRestHoliday(float numberOfRestHoliday) {
        this.numberOfRestHoliday = numberOfRestHoliday;
    }

    public float getNumberOfSickDay() {
        return numberOfSickDay;
    }

    public void setNumberOfSickDay(float numberOfSickDay) {
        this.numberOfSickDay = numberOfSickDay;
    }

    public int getNUMBEROFHOLIDAY() {
        return NUMBEROFHOLIDAY;
    }

    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

}
