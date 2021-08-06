package services;

import Helper.Helper;
import model.Employee;
import model.Project;
import model.RequestOfHoliday;
import model.enumeration.Power;
import model.enumeration.RequestStatus;
import repository.EmployeeRepository;
import repository.ProjektRepository;
import repository.RequestRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookkeeperServices {

    private  final EmployeeRepository EMPLOYEEREPOSITORY = new EmployeeRepository();
    private  final ProjektRepository PROJECTREPOSITORY = new ProjektRepository();
    private  final RequestRepository REQUESTOFHOLIDAYSREPOSITORY = new RequestRepository();


    //spezifischen Methoden von AdminServices
    //bk04: Alle beantragten Urlaubstage anzeigen
    public  ArrayList<RequestOfHoliday> getAllRequestOfHolidays(String username) {

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        ArrayList<RequestOfHoliday> requestOfHolidays = null;

        if (employee != null) {

            if (employee.getPower() == Power.Bookkeeper) {

                requestOfHolidays = new ArrayList<>();
                ArrayList<RequestOfHoliday> allRequests = REQUESTOFHOLIDAYSREPOSITORY.getAllRequestOfHoliday();

                for (RequestOfHoliday request : allRequests) {

                    if (request.getStatus() == RequestStatus.Waiting) {

                        requestOfHolidays.add(request);
                    }
                }
            }
        }

        return requestOfHolidays;
    }

    //bk05: Beantragte Urlaubstage bestätigen/ablehnen
    public  void replyToRequest(String username, long requestId, RequestStatus answerFromBookkeeper) {

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null) {
            if (employee.getPower() == Power.Bookkeeper) {

                RequestOfHoliday request = REQUESTOFHOLIDAYSREPOSITORY.getRequestOfHolidayById(requestId);

                if (!(request.getStatus() == answerFromBookkeeper)) {

                    REQUESTOFHOLIDAYSREPOSITORY.setStatusOfRequestOfHoliday(requestId, answerFromBookkeeper);
                    float numberOfAllowedDay = request.getNumberOfRequestedDay();
                    Employee employee1 = EMPLOYEEREPOSITORY.findByEmployeenummer(request.getEmployeeNummer());
                    employee1.setNumberOfUsedAndRestHoliday(employee1.getNumberOfUsedHoliday()+ numberOfAllowedDay);
                    EMPLOYEEREPOSITORY.setUsedAndRestHolidays(employee1);

                }
            }
        }
    }

    //bk03: Alle Employees, die im Urlaub sind, anzeigen
    public  ArrayList<Employee> getAllEmployeesInHoliday(String username) {

        ArrayList<Employee> employeesInHollidays = null;
        boolean inHoliday = false;
        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null) {
            if (employee.getPower() == Power.Bookkeeper) {

                employeesInHollidays = new ArrayList<>();
                ArrayList<RequestOfHoliday> requests = REQUESTOFHOLIDAYSREPOSITORY.getAllRequestOfHoliday();

                String format = "dd.MM.yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.setLenient(false);
                try {
                    // create Today's Date
                    Date date = new Date();
                    String strDate = dateFormat.format(date);
                    Date todayDate = dateFormat.parse(strDate);

                    for (RequestOfHoliday request: requests) {

                        String date1 = request.getStartDate();
                        String date2 = request.getFinishDate();
                        Date startDate = dateFormat.parse(date1);
                        Date finishDate = dateFormat.parse(date2);

                        inHoliday = todayDate.equals(startDate);

                        if (!inHoliday){

                            inHoliday = todayDate.equals(finishDate);
                        }

                        if (!inHoliday){

                            inHoliday = (startDate.before(todayDate)) && (todayDate.before(finishDate));
                        }

                        if (inHoliday){

                            Employee employee1 = EMPLOYEEREPOSITORY.findByEmployeenummer(request.getEmployeeNummer());
                            employeesInHollidays.add(employee1);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return employeesInHollidays;
    }


    public  void dropRequestOfHolidays(String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null) {
            if (employee.getPower() == Power.Bookkeeper) {

                REQUESTOFHOLIDAYSREPOSITORY.deleteRequestOfHolidays();
            }
        }
    }

    //bk10: finalisierte/unfinalisierte Stunden von einzelnen Employees anzeigen
    //bk11: Über-/Unter-Stunden einzelner Employees anzeigen
    // Die Erfüllung den Anforderungen bk10 und bk11 erfolgt durch die Methode getAllUser(), getMyAllWorkHour und getMyWorkOverAndUndertime()
    /*// durch die zwei unteren Methoden und die Methode getAllUser wird die Logik der Methode zur Erfüllung der Anforderung bk10 in ApplicationForBookkeeper
    // implementiert
    public static ArrayList<Project> getFinalProjects(String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        ArrayList<Project> finalProjects = null;

        if (employee!= null){
            if (employee.getPower() == Power.Bookkeeper){

                finalProjects = PROJECTREPOSITORY.getFinalProjects();
            }
        }

        return finalProjects;
    }

    public static ArrayList<Project> getNonFinalProjects(String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        ArrayList<Project> nonFinalProjects = null;

        if (employee!= null){
            if (employee.getPower() == Power.Bookkeeper){

                nonFinalProjects = PROJECTREPOSITORY.getNonFinalProjects();
            }
        }

        return nonFinalProjects;
    }*/


    //Hier sind die gemeinsamen Methode mit EmployeeService und BookkeeperServices
    public  ArrayList<Employee> getAllUser(String username) {

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee!=null){

            return EMPLOYEEREPOSITORY.getAllUser();

        }else {

            return null;
        }
    }

    public  ArrayList<ArrayList<Employee>> getAllUserSortByRole(String username) {

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        ArrayList<ArrayList<Employee>> employeesSortByRolle = new ArrayList<>();

        if (employee!=null) {

            ArrayList<Employee> employees = new ArrayList<>();
            ArrayList<Employee> administrators = new ArrayList<>();
            ArrayList<Employee> bookkeepers = new ArrayList<>();

            for (Employee employee1: EMPLOYEEREPOSITORY.getAllUser()) {

                if (employee1.getPower() == Power.Employee){
                    employees.add(employee1);
                }
                if (employee1.getPower() == Power.Administrator){
                    administrators.add(employee1);
                }
                if (employee1.getPower() == Power.Bookkeeper){
                    //assert employee instanceof Bookkeeper;
                    bookkeepers.add(employee1);
                }
            }

            employeesSortByRolle.add(Helper.sortEmployeeAlphabeticaly(employees));
            employeesSortByRolle.add(Helper.sortEmployeeAlphabeticaly(administrators));
            employeesSortByRolle.add(Helper.sortEmployeeAlphabeticaly(bookkeepers));

            return employeesSortByRolle;
        }else {

            return null;
        }
    }

    public  Employee getMyAccountData(String username) {

        return EMPLOYEEREPOSITORY.findByUsername(username);
    }

    public  void setMyAccountData(String username, String oldPassword, String newPassword) {

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee!=null) {

            if (employee.getPassword().equals(oldPassword)) {

                EMPLOYEEREPOSITORY.setPasswordOfEmployee(employee, newPassword);
            }
        }
    }

    public  ArrayList<Object> getMyAllWorkHour(String username) {

        ArrayList<Object> data = new ArrayList<>();
        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee!= null) {

            ArrayList<Project> projectsWithFinalHour = PROJECTREPOSITORY.findProjectsWithFinalHourByEmployeeNummer(employee);
            ArrayList<Project> projectsWithNonFinalHour = PROJECTREPOSITORY.findProjectsWithNonFinalHourByEmployeeNummer(employee);

            float myFinalHour = 0;
            float myNonFinalHour = 0;
            float allMyWorkHour;

            for (Project project: projectsWithFinalHour) {
                myFinalHour = myFinalHour + project.getWorkhour();
            }

            for (Project project: projectsWithNonFinalHour) {
                myNonFinalHour = myNonFinalHour + project.getWorkhour();
            }

            allMyWorkHour = myFinalHour + myNonFinalHour;

            data.add(employee);
            data.add(myFinalHour);
            data.add(myNonFinalHour);
            data.add(allMyWorkHour);

            return data;
        }else {

            return null;
        }
    }

    public  void addProjectWithNonFinalHour(Project project, String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee!= null) {

            PROJECTREPOSITORY.addProjectWithNonFinalHour(project);
        }
    }

    public  void setMyWorkHour(Project project) {

        PROJECTREPOSITORY.setHourOfNonFinalProject(project);
    }

    public  void finaliseAllMyWorkHourOfMount(String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null){

            PROJECTREPOSITORY.finaliseAllMyWorkHourOfMouth(employee);
        }
    }

    public  ArrayList<Object> getMyWorkOverAndUndertime(String username) {

        ArrayList<Object> data = new ArrayList<>();

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null){

            ArrayList<Project> projectsWithFinalHour = PROJECTREPOSITORY.findProjectsWithFinalHourByEmployeeNummer(employee);
            ArrayList<Project> projectsWithNonFinalHour = PROJECTREPOSITORY.findProjectsWithNonFinalHourByEmployeeNummer(employee);

            float myFinalOverTime = 0;
            float myNonFinalOverTime = 0;
            float allOvertime;
            float myFinalUnderTime = 0;
            float myNonFinalUnderTime = 0;
            float allUndertime;

            for (Project project: projectsWithFinalHour) {
                myFinalOverTime = myFinalOverTime + project.getOvertime();
                myFinalUnderTime = myFinalUnderTime + project.getUndertime();
            }

            for (Project project: projectsWithNonFinalHour) {
                myNonFinalOverTime = myNonFinalOverTime + project.getOvertime();
                myNonFinalUnderTime = myNonFinalUnderTime + project.getUndertime();
            }

            allOvertime = myFinalOverTime + myNonFinalOverTime;
            allUndertime = myFinalUnderTime + myNonFinalUnderTime;

            data.add(employee);
            data.add(myFinalOverTime);
            data.add(myNonFinalOverTime);
            data.add(allOvertime);
            data.add(myFinalUnderTime);
            data.add(myNonFinalUnderTime);
            data.add(allUndertime);

            return data;
        }else {

            return null;
        }
    }

    public  ArrayList<ArrayList<Project>> getMyProjects(String username) {

        ArrayList<ArrayList<Project>> projects = new ArrayList<>();

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null){

            ArrayList<Project> finalProjects = PROJECTREPOSITORY.findProjectsWithFinalHourByEmployeeNummer(employee);
            ArrayList<Project> nonFinalProjects = PROJECTREPOSITORY.findProjectsWithNonFinalHourByEmployeeNummer(employee);

            projects.add(finalProjects);
            projects.add(nonFinalProjects);

        }

        return projects;
    }

    public  Employee getMyHolidays(String username) {

        return EMPLOYEEREPOSITORY.findByUsername(username);
    }

    public  Employee getMyRestHoliday(String username) {

        return EMPLOYEEREPOSITORY.findByUsername(username);
    }

    public  void requestOfHolidays(RequestOfHoliday requestOfHoliday){

        REQUESTOFHOLIDAYSREPOSITORY.addRequestOfHoliday(requestOfHoliday);
    }

    public  void setUsedAndRestHolydays(Employee employee){

        EMPLOYEEREPOSITORY.setUsedAndRestHolidays(employee);
    }

    public  void deleteProjectsWithFinalHour(){

        PROJECTREPOSITORY.deleteProjectsWithFinalHour();
    }

    public  void deleteProjectsWithNonFinalHour(){

        PROJECTREPOSITORY.deleteProjectsWithNonFinalHour();
    }

    public  void deleteNonFinalProjectFromProjects(long employeeNummer){

        PROJECTREPOSITORY.dropByEmployeeNummer(employeeNummer, PROJECTREPOSITORY.getPROJECTWITHNONFINALHOUR());
    }

}
