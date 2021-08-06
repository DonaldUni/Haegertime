package model;

import model.enumeration.RequestStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestOfHoliday {

    private long requestID;
    private long employeeNummer;
    private float numberOfRequestedDay;
    private String startDate;
    private String finishDate;
    private RequestStatus status = RequestStatus.Waiting ;
    private String datum;
    private String hour;


    public RequestOfHoliday(long employeeNummer, float numberOfRequestedDay, String startDate, String finishDate) {
        this.employeeNummer = employeeNummer;
        this.numberOfRequestedDay = numberOfRequestedDay;
        this.startDate = startDate;
        this.finishDate = finishDate;
        setDateAndHour();
    }

    public RequestOfHoliday(long requestID, long employeeNummer, float numberOfRequestedDay, String startDate, String finishDate) {
        this.requestID = requestID;
        this.employeeNummer = employeeNummer;
        this.numberOfRequestedDay = numberOfRequestedDay;
        this.startDate = startDate;
        this.finishDate = finishDate;
        setDateAndHour();
    }

    public RequestOfHoliday(long requestID, long employeeNummer, float numberOfRequestedDay, String startDate, String finishDate, RequestStatus status, String datum, String hour) {
        this.requestID = requestID;
        this.employeeNummer = employeeNummer;
        this.numberOfRequestedDay = numberOfRequestedDay;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.datum = datum;
        this.hour = hour;
    }

    //getter und setter
    private void setDateAndHour(){
        SimpleDateFormat formatDay = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
        Date date = new Date();

        this.datum = formatDay.format(date);
        this.hour = formatHour.format(date);
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public long getEmployeeNummer() {
        return employeeNummer;
    }

    public void setEmployeeNummer(long employeeNummer) {
        this.employeeNummer = employeeNummer;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public float getNumberOfRequestedDay() {
        return numberOfRequestedDay;
    }

    public void setNumberOfRequestedDay(float numberOfRequestedDay) {
        this.numberOfRequestedDay = numberOfRequestedDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getDatum() {
        return datum;
    }

    public String getHour() {
        return hour;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
