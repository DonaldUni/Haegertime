package Helper;

import model.Employee;
import model.Project;
import model.RequestOfHoliday;
import model.enumeration.Power;
import model.enumeration.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);


    //Methoden zur Prüfung von gültigen eingabeFormat für Datum, Tage und Stunden
    public static boolean checkDateFormat(String givenDate) {
        // Format ein Zeitraum ist : dd.mm.yyyy

        String format = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        boolean validDate = false;

        try {

            Date date = dateFormat.parse(givenDate);
            validDate = true;
        } catch (ParseException ex) {
            logger.info("Das gültige Format für Datum ist dd.MM.yyyy und nicht anders.");
            ex.printStackTrace();
        }

        return validDate;
    }

    public static String BuildPeriod(String date1, String date2) {

        String period = "";

        if (checkDateFormat(date1) && checkDateFormat(date2)) {

            period = date1 + "-" + date2;
        }

        return period;
    }

    public static boolean compareDate(String date1, String date2) {  //date2 muss höher als date1 sein und date1 muss höher als das heutige Datum

        String format = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        boolean validated = false;


        try {
            // create Today's Date
            Date date = new Date();
            String strDate = dateFormat.format(date);
            Date todayDate = dateFormat.parse(strDate);

            Date firstDate = dateFormat.parse(date1);
            Date secondDate = dateFormat.parse(date2);

            if (todayDate.before(firstDate) && firstDate.before(secondDate)) {

                validated = true;
            }else {

                logger.info("Das erste Datum muss höher als das heutige Datum sein und das zweite Datum muss höher als das erste Datum.");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return validated;
    }

    public static boolean compareDateWithToday(String date1) {  //date2 muss höher als date1 sein und date1 muss höher als das heutige Datum

        String format = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        boolean validated = false;


        try {
            // create Today's Date
            Date date = new Date();
            String strDate = dateFormat.format(date);
            Date todayDate = dateFormat.parse(strDate);

            Date firstDate = dateFormat.parse(date1);

            if (todayDate.before(firstDate) || todayDate.equals(firstDate)) {

                validated = true;
            }else {

                logger.info("Das Startdatum stimmt nicht mit heutigen Datum");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return validated;
    }

    public static boolean checkNumberOfDayFormat(String numberOfDay) {

        boolean validated = false;

        if (numberOfDay.contains(",")) {

            int index = numberOfDay.indexOf(",");
            String firstPart = numberOfDay.substring(0, index);
            String secondPart = numberOfDay.substring(index + 1);

            try {
                int validFirstPart = Integer.parseInt(firstPart);
                int validSecondPart = Integer.parseInt(secondPart);

                if (validFirstPart > 0) {

                    if (validSecondPart == 5 || validSecondPart == 0) {

                        validated = true;
                    }else {

                        logger.info("Bitte nur halb Tag eingeben. Bsp : 12,5 ");
                    }
                }else {
                    logger.info("Kein negative Zahl eingeben bitte.");
                }
            } catch (Exception e) {

                logger.info("Richtige Anzahl von Tage eingeben bitte.");
                e.printStackTrace();
            }
        } else {
            try {

                int validNumberOfDay = Integer.parseInt(numberOfDay);
                if (validNumberOfDay > 0) {
                    System.out.println("Valid Anzahl von Tagen");
                    validated = true;
                }else {
                    logger.info("Kein negative Zahl eingeben bitte.");
                }
            } catch (Exception e) {

                logger.info("Richtige Anzahl von Tage eingeben bitte.");
                e.printStackTrace();
            }
        }

        return validated;
    }

    public static boolean checkHourFormat(String hour) {
        //Valid Format von Stunden ist hh,m . Bsp : 10,6 Std
        //Falls es geht um eine rund Nummer trotzdem die Komma eingeben. Bsp 10,0

        boolean validFormat = false;

        if (hour.contains(",")){

            int index = hour.indexOf(",");
            String firstPart = hour.substring(0, index);
            String secondPart = hour.substring(index+1);

            if (secondPart.length()>1){
                logger.info("Nach dem Komma nur eine Zahl als eingeben bitte. Bsp 10,6");
            }else {
                if (secondPart.length() == 0){

                    logger.info("Nach dem Komma eine Zahl eingeben bitte. Bsp 10,6");
                }else {

                    try {
                        int validFirstPart = Integer.parseInt(firstPart);
                        int validSecondPart = Integer.parseInt(secondPart);

                        if (validFirstPart < 0){

                            logger.info("Keine negative Anzahl von Stunden eingeben bitte.");
                        }else {
                            if (validFirstPart >24){

                                logger.info("Keine ungültige Anzahl von Stunden eingeben bitte.");
                            }else{

                                if (validSecondPart<0){

                                    logger.info("Keine komishe Anzahl von Stunden eingeben bitte.");
                                }else {

                                    validFormat = true;
                                    logger.info("super");
                                }
                            }
                        }
                    }catch (Exception e){

                        logger.info("Geben Sie ein gültige Anzahl von Stunde bitte.");
                    }
                }
            }

        }else {

            logger.info("Das Format für die Anzahl von Stunde ist hh,m. Bsp: 10,0 oder 10,6");
        }

        return validFormat;
    }


    // Hilfe Methode für EmployeeRepository
    public static Power convertStringToPower(String name) {

        return switch (name) {
            case "Administrator" -> Power.Administrator;
            case "Bookkeeper" -> Power.Bookkeeper;
            default -> Power.Employee;
        };
    }

    public static Status convertStringToStatus(String name) {

        if (name.equals("deactived")) {

            return Status.deactived;
        } else {
            return Status.actived;
        }
    }

    public static String convertBooleantoString(boolean bool) {

        if (bool) {
            return "true";
        } else {
            return "false";
        }
    }


    // Hilfe Methode für andere Klassen
    public static boolean convertStringToBoolean(String name) {

        return name.equals("true");
    }

    public static ArrayList<Employee> sortEmployeeAlphabeticaly(ArrayList<Employee> array) {

        //        Employee temp;
//        //        for (int j = 0; j < array.size(); j++) {
////            for (int i = j + 1; i < array.size(); i++) {
////                // comparing adjacent lastname
////                if (array.get(i).getLastname().compareTo(array.get(j).getLastname()) < 0) {
////                    temp = array.get(j);
////                    array.set(j, array.get(i));
////                    array.set(i, temp);
////                }
////            }
////        }

        return array.stream().sorted(Comparator.comparing(Employee::getLastname)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Employee> createEmployees() {

        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("Richard", "Obama", 1, "Robama", "password1", "richard_obama@gmail.com");

        Employee employee2 = new Employee("Sebastian", "Schwarz", 2, "schwarz", "password2", "sebastian_schwarz@gmail.com");
        Employee employee3 = new Employee("Sandra", "Merkel", 3, "Smerkel", "password3", "sandra_merkel@gmail.com");
        employee3.setPower(Power.Administrator);
        Employee employee4 = new Employee("Ruth", "Krieg", 4, "Rkrieg", "password4", "ruth_krieg@gmail.com");
        employee4.setPower(Power.Administrator);
        Employee employee5 = new Employee("Mark", "Zuckenberg", 5, "Mzugkenberg", "password5", "mark_zugkenberg@gmail.com");
        employee5.setPower(Power.Bookkeeper);
        Employee employee6 = new Employee("Martin", "Lutter", 6, "Mlutter", "password6", "martin_lutter@gmail.com");
        employee6.setPower(Power.Bookkeeper);
        Employee employee7 = new Employee("Magelan", "Kapersky", 7, "Mkapersky", "password7", "magelan_kapersky@gmail.com");
        employee7.setPower(Power.Bookkeeper);
        Employee employee8 = new Employee("Yves", "Weiss", 8, "Yweiss", "password8", "yves_weiss@gmail.com");
        employee8.setPower(Power.Administrator);
        Employee employee9 = new Employee("Derick", "Kruger", 9, "Dkruger", "password9", "derik_kruger@gmail.com");
        employee9.setPower(Power.Bookkeeper);

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        employees.add(employee6);
        employees.add(employee7);
        employees.add(employee8);
        employees.add(employee9);

        return employees;
    }

    public static ArrayList<Employee> createEmployeeWithFalseData() {

        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("Ric", "Ob", 1, "obama", "password1", "richard_obama@gmail.com");
        Employee employee2 = new Employee("Sebast", "Sch", 20, "schwarz", "password2", "sebastian_schwarz@gmail.com");

        employees.add(employee1);
        employees.add(employee2);

        return employees;
    }

    public static ArrayList<Project> createProjectsWithNonFinalHour() {

        ArrayList<Project> projects = new ArrayList<>();
        String period = BuildPeriod("01.10.2021","01.11.2021"); // Hier prüft man ob die Daten im richtigen Format eingegeben wurden und ob die logisch sind
        //d.h date2 höher als date1 und date1 höher als das heutige Datum

        if (!period.equals("")){
            Project project1 = new Project(1, "Autohaus", 1, 80f, 12, 8, period);
            Project project2 = new Project(2, "HaegerTime", 3, 160f, 0, 0, period);
            Project project3 = new Project(3, "Bauhaus", 1, 160f, 100, 10, period);
            Project project4 = new Project(4, "Webapp", 2, 90f, 0, 0, period);
            Project project5 = new Project(5, "TestAutomatisierung", 5, 100f, 0, 0, period);
            Project project6 = new Project(1, "Autohaus", 1, 80f, 12, 8, period);
            Project project7 = new Project(2, "HaegerTime", 3, 160f, 0, 0, period);
            Project project8 = new Project(3, "Bauhaus", 1, 160f, 100, 10, period);
            Project project9 = new Project(4, "Webapp", 2, 90f, 0, 0, period);
            Project project10 = new Project(5, "TestAutomatisierung", 5, 100f, 0, 0, period);

            projects.add(project1);
            projects.add(project2);
            projects.add(project3);
            projects.add(project4);
            projects.add(project5);
            projects.add(project6);
            projects.add(project7);
            projects.add(project8);
            projects.add(project9);
            projects.add(project10);
        }

        return projects;
    }

    public static Project createProjectWithNonFinalHour(long id, String projektName, long employeeNummer, String workhour, String overtime, String undertime, String startDate, String finishDate) {

        String period = BuildPeriod("01.10.2021", "01.11.2021"); // Hier prüft man ob die Daten im richtigen Format eingegeben wurden und ob die logisch sind
        //d.h date2 höher als date1 und date1 höher als das heutige Datum
        boolean validWorkhour = checkHourFormat(workhour);
        boolean validOvertime = checkHourFormat(overtime);
        boolean validUndertime = checkHourFormat(undertime);

        Project project = null;

        if (!period.equals("") && validWorkhour && validOvertime && validUndertime){

            project = new Project(id, projektName, employeeNummer, Float.parseFloat(workhour), Float.parseFloat(overtime), Float.parseFloat(undertime), period);
        }

        return project;
    }

    public static Project reviseHour() {

        return new Project("Autohaus", 1, 100f, 12, 8, "01.05.2021-01.06.2021");
    }

    private static ArrayList<RequestOfHoliday> requestOfHolidays = new ArrayList<>();

    public static ArrayList<RequestOfHoliday> createRequestsOfHolidays() {

        createRequestOfHolidays(0,1, "14", "09.06.2021", "17.07.2021");
        createRequestOfHolidays(1,2, "18", "09.06.2021", "30.07.2021");
        createRequestOfHolidays(2,3, "10", "07.06.2021", "11.07.2021");
        createRequestOfHolidays(3,4, "7", "02.05.2021", "15.07.2021");
        createRequestOfHolidays(4,5, "24", "14.06.2021", "25.07.2021");
        createRequestOfHolidays(5,6, "20", "28.06.2021", "21.07.2021");
        createRequestOfHolidays(6,7, "14", "01.07.2021", "09.09.2021");
        createRequestOfHolidays(7,8, "10", "07.08.2021", "09.09.2021");
        createRequestOfHolidays(8,9, "10", "18.09.2021", "19.09.2021");
        createRequestOfHolidays(1,9, "7", "01.10.2021", "08.10.2021");

        return requestOfHolidays;
    }


    public static void createRequestOfHolidays(long requestId, long employeeNummer, String numberOfRequestedDays, String startDate, String finishDate) {

        boolean validNumberOfDay = checkNumberOfDayFormat(numberOfRequestedDays);
        boolean validStartDate = checkDateFormat(startDate);
        boolean validFinishDate = checkDateFormat(finishDate);
        boolean validDate = compareDate(startDate, finishDate);

        if (validNumberOfDay && validStartDate && validFinishDate && validDate) {

            RequestOfHoliday request = new RequestOfHoliday(requestId, employeeNummer, Integer.parseInt(numberOfRequestedDays), startDate, finishDate);
            requestOfHolidays.add(request);
        }
    }

    public static ArrayList<RequestOfHoliday> getRequestOfHolidays() {
        return requestOfHolidays;
    }
}
