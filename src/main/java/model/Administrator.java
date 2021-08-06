package model;

import model.enumeration.Power;
import model.enumeration.Status;

public class Administrator extends Employee{


    public Administrator(String firstname, String lastname, long employeeNummer, String userName, String password, String email) {
        super(firstname, lastname, employeeNummer, userName, password, email);
        setPower(Power.Administrator);
    }

    public Administrator(String firstname, String lastname, long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay) {
        super(firstname, lastname, employeeNummer, userName, password, email, power, status, numberOfUsedHoliday, numberOfRestHoliday, numberOfSickDay);
        setPower(Power.Administrator);

    }

    public Administrator(long id, String firstname, String lastname, long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay) {
        super(id, firstname, lastname, employeeNummer, userName, password, email, power, status, numberOfUsedHoliday, numberOfRestHoliday, numberOfSickDay);
        setPower(Power.Administrator);

    }

    public Administrator(long id, String firstname, String lastname, long employeeNummer, String userName, String password, String email, Power power, Status status, float numberOfUsedHoliday, float numberOfRestHoliday, float numberOfSickDay, String logged) {
        super(id, firstname, lastname, employeeNummer, userName, password, email, power, status, numberOfUsedHoliday, numberOfRestHoliday, numberOfSickDay, logged);
        setPower(Power.Administrator);

    }
}
