package Helper;

import model.RequestOfHoliday;
import model.enumeration.Power;
import model.enumeration.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelperTest {


    @Test
    @DisplayName("testCheckDateFormat")
    void testCheckDateFormat(){

        String date1 = "01.05.2021";
        String date2 = "10.06.-0001";
        String date3 = "10/06/2021";
        String date4 = "10060001";

        assertAll(                      // Test the result of all typ of input
                () -> assertTrue(Helper.checkDateFormat(date1), () -> "Should be true."),
                () -> assertFalse(Helper.checkDateFormat(date2), () -> "Should be false."),
                () -> assertFalse(Helper.checkDateFormat(date3), () -> "Should be false."),
                () -> assertFalse(Helper.checkDateFormat(date4), () -> "Should be false.")
        );
    }

    @Test
    @DisplayName("testBuildPeriod")
    void testBuildPeriod(){

        String date1 = "01.05.2021";
        String date2 = "10.06.2021";
        String period = date1 + "-" +date2;
        String date3 = "10/06/2021";
        String date4 = "10/06/2021";
        String result = "";

        assertAll(
                () -> assertEquals(period, Helper.BuildPeriod(date1,date2), () -> "Should return a period."),
                () -> assertEquals(result, Helper.BuildPeriod(date3,date1), () -> "Should return a empty String."),
                () -> assertEquals(result, Helper.BuildPeriod(date3,date4), () -> "Should return a empty String.")
        );
    }

    @Test
    @DisplayName("testCompareDate")
    void testCompareDate(){

        String date1 = "01.07.2021";
        String date2 = "10.07.2021";
        String date3 = "01.05.2021";
        String date4 = "11.04.2021";

        assertAll(                                                                              // test all case of method compareDate
                () -> assertTrue(Helper.compareDate(date1,date2), () ->"Should be true."),      //todaysDate < date1 < date2 => True
                () -> assertFalse(Helper.compareDate(date1,date3),() ->"Should be false."),     //todaysDate < date1 > date3 => False
                () -> assertFalse(Helper.compareDate(date3,date2),() ->"Should be false."),     //todaysDate > date3 < date2 => False
                () -> assertFalse(Helper.compareDate(date3,date4),() ->"Should be false.")      //todaysDate > date1 > date4 => False
                );
    }

    @Test
    @DisplayName("testCheckNumberOfDayFormat")
    void testCheckNumberOfDayFormat(){

        String numberOfDay1 = "12";
        String numberOfDay2 = "12,0";
        String numberOfDay3 = "12,5";
        String numberOfDay4 = "-12";
        String numberOfDay5 = "12,a";
        String numberOfDay6 = "12,60";
        String numberOfDay7 = "-12,5";

        assertAll(
                () -> assertTrue(Helper.checkNumberOfDayFormat(numberOfDay1), () ->"Should be true."),
                () -> assertTrue(Helper.checkNumberOfDayFormat(numberOfDay2), () ->"Should be true."),
                () -> assertTrue(Helper.checkNumberOfDayFormat(numberOfDay3), () ->"Should be true."),
                () -> assertFalse(Helper.checkNumberOfDayFormat(numberOfDay4), () ->"Should be false."),
                () -> assertFalse(Helper.checkNumberOfDayFormat(numberOfDay5), () ->"Should be false."),
                () -> assertFalse(Helper.checkNumberOfDayFormat(numberOfDay6), () ->"Should be false."),
                () -> assertFalse(Helper.checkNumberOfDayFormat(numberOfDay7), () ->"Should be false.")
                );
    }

    @Test
    @DisplayName("testCheckHourFormat")
    void testCheckHourFormat(){

        String hour1 = "12,0";
        String hour2 = "12,6";
        String hour3 = "12,65";
        String hour4 = "-12,0";
        String hour5 = "12,a";
        String hour6 = "12,-6";
        String hour7 = "12,0,6";
        String hour8 = "14";
        String hour9 = "100,0";


        assertAll(
                () -> assertTrue(Helper.checkHourFormat(hour1), () ->"Should be true with input" + hour1 +"."),
                () -> assertTrue(Helper.checkHourFormat(hour2), () ->"Should be true with input" + hour2 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour3), () ->"Should be false with input" + hour3 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour4), () ->"Should be false with input " + hour4 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour5), () ->"Should be false with input " + hour5 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour6), () ->"Should be false with input " + hour6 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour7), () ->"Should be false with input " + hour7 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour8), () ->"Should be false with input " + hour8 +"."),
                () -> assertFalse(Helper.checkHourFormat(hour9), () ->"Should be false with input " + hour9 +".")

        );
    }

    @Test
    @DisplayName("testConvertStringToPower")
    void testConvertStringToPower(){

        String name1 = "Administrator";
        String name2 = "Bookkeeper";
        String name3 = "a";
        String name4 = "Employee";

        assertAll(
                () -> assertEquals(Power.Administrator, Helper.convertStringToPower(name1), () ->"Should be Power Administrator with input" + name1 +"."),
                () -> assertEquals(Power.Bookkeeper, Helper.convertStringToPower(name2), () ->"Should be Power Bookkeeper with input" + name2 +"."),
                () -> assertEquals(Power.Employee, Helper.convertStringToPower(name3), () ->"Should be Power Employee with input" + name3 +"."),
                () -> assertEquals(Power.Employee, Helper.convertStringToPower(name4), () ->"Should be Power Employee false with input " + name4 +".")
        );
    }

    @Test
    @DisplayName("testConvertStringToPower")
    void testConvertStringToStatus(){

        String name1 = "deactived";
        String name2 = "actived";
        String name3 = "a";
        String name4 = "nichts";

        assertAll(
                () -> assertEquals(Status.deactived, Helper.convertStringToStatus(name1), () ->"Should be Status deactived with input" + name1 +"."),
                () -> assertEquals(Status.actived, Helper.convertStringToStatus(name2), () ->"Should be Status actived with input" + name2 +"."),
                () -> assertEquals(Status.actived, Helper.convertStringToStatus(name3), () ->"Should be Status actived with input" + name3 +"."),
                () -> assertEquals(Status.actived, Helper.convertStringToStatus(name4), () ->"Should be Status actived false with input " + name4 +".")
        );
    }

    @Test
    @DisplayName("testConvertBooleantoString")
    void testConvertBooleantoString(){

        String name1 = "true";
        String name2 = "false";

        assertAll(
                () -> assertEquals(name1, Helper.convertBooleantoString(true), () ->"Should be return " + name1 +"."),
                () -> assertEquals(name2, Helper.convertBooleantoString(false), () ->"Should be return " + name2 +".")
                );
    }

    @Test
    @DisplayName("testConvertStringToBoolean")
    void testConvertStringToBoolean(){

        String name1 = "true";
        String name2 = "false";
        String name3 = "y";

        assertAll(
                () -> assertTrue(Helper.convertStringToBoolean(name1), () ->"Should be return true with input " + name1 +"."),
                () -> assertFalse(Helper.convertStringToBoolean(name2), () ->"Should be return false with input " + name2 +"."),
                () -> assertFalse(Helper.convertStringToBoolean(name3), () ->"Should be return false with input " + name3 +".")
        );
    }

    @Test
    @DisplayName("testCreateEmployees")
    void testCreateEmployees(){

        int arraySize = 9;

        assertEquals(arraySize, Helper.createEmployees().size(),()-> "The size of the array ist wrong.");
    }

    @Test
    @DisplayName("testCreateRequestOfHolidays")
    void testCreateRequestOfHolidays(){

        long requestId = 2;
        long employeeNummer = 3;
        String numberOfRequestedDays = "10";
        String startDate = "20.07.2021";
        String finishDate = "30.07.2021";

        RequestOfHoliday request = new RequestOfHoliday(requestId, employeeNummer, Integer.parseInt(numberOfRequestedDays), startDate, finishDate);
        Helper.createRequestOfHolidays(requestId, employeeNummer, numberOfRequestedDays, startDate, finishDate);
        final boolean[] equal = new boolean[1];
        Helper.getRequestOfHolidays().forEach( requestOfHoliday -> {
            boolean equal1 = requestOfHoliday.getRequestID() == request.getRequestID();
            boolean equal2 = requestOfHoliday.getEmployeeNummer() == request.getEmployeeNummer();
            boolean equal3 = requestOfHoliday.getStartDate().equals(request.getStartDate());
            boolean equal4 = requestOfHoliday.getFinishDate().equals(request.getFinishDate());

            if (equal1 && equal2 && equal3 && equal4 ){
                equal[0] = true;
            }
        });
        System.out.println();
        assertTrue(equal[0], ()->"should contains the request.");

    }


}
