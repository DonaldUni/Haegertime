package model;

import model.enumeration.Power;
import model.enumeration.Status;

public class Bookkeeper extends Employee{


    public Bookkeeper(String firstname, String lastname, long employeeNummer, String userName, String password, String email) {
        super(firstname, lastname, employeeNummer, userName, password, email);
        setPower(Power.Bookkeeper);

    }

    public Bookkeeper(String firstname, String lastname, long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay) {
        super(firstname, lastname, employeeNummer, userName, password, email, power, status, numberOfUsedHoliday, numberOfRestHoliday, numberOfSickDay);
        setPower(Power.Bookkeeper);
    }

    public Bookkeeper(long id, String firstname, String lastname, long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay) {
        super(id, firstname, lastname, employeeNummer, userName, password, email, power, status, numberOfUsedHoliday, numberOfRestHoliday, numberOfSickDay);
        setPower(Power.Bookkeeper);
    }
}
