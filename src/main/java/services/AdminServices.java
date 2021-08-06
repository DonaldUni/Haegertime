package services;

import Helper.Helper;
import model.Employee;
import model.Project;
import model.RequestOfHoliday;
import model.enumeration.Power;
import model.enumeration.RequestStatus;
import model.enumeration.Status;
import repository.EmployeeRepository;
import repository.ProjektRepository;
import repository.RequestRepository;

import java.util.ArrayList;

public class AdminServices {


    private  final EmployeeRepository EMPLOYEEREPOSITORY = new EmployeeRepository();
    private  final ProjektRepository PROJECTREPOSITORY = new ProjektRepository();
    private  final RequestRepository REQUESTOFHOLIDAYSREPOSITORY = new RequestRepository();


    //spezifischen Methoden von AdminServices
    //adm04
    public  void deleteUserByUsername(String username){

        EMPLOYEEREPOSITORY.deleteEmployeeByUsername(username);
    }

    public  void deleteUserByEmployeeNummer(long employeeNummer){

        EMPLOYEEREPOSITORY.deleteEmployeeByEmployeeNummer(employeeNummer);
    }

    public  void dropEmployees(){

        EMPLOYEEREPOSITORY.deleteEmployees();
    }

    //adm05
    public  void setUsernameOfUserByOldUsername(String usernameOfAdmin, String oldUsername, String newUsername){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(usernameOfAdmin);

        if (employee!= null){
            if (employee.getPower() == Power.Administrator){

                EMPLOYEEREPOSITORY.setUsernameOfUserByUsername(oldUsername, newUsername);
            }
        }
    }

    public  void setUsernameOfUserByEmployeeNummer(String usernameOfAdmin, long employeeNummer, String newUsername){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(usernameOfAdmin);

        if (employee!= null) {
            if (employee.getPower() == Power.Administrator) {

                EMPLOYEEREPOSITORY.setUsernameOfUserByEmployeeNummer(employeeNummer, newUsername);
            }
        }
    }

    //adm06
    public  void setPowerOfUserByUsername(String usernameOfAdmin, String username, Power newPower){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(usernameOfAdmin);

        if (employee!= null) {
            if (employee.getPower() == Power.Administrator) {

                EMPLOYEEREPOSITORY.setPowerOfUserByUsername(username, newPower);
            }
        }
    }

    public  void setPowerOfUserByEmployeeNummer(String usernameOfAdmin, long  employeeNummer, Power newPower){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(usernameOfAdmin);

        if (employee!= null) {
            if (employee.getPower() == Power.Administrator) {

                EMPLOYEEREPOSITORY.setPowerOfUserByEmployeeNummer(employeeNummer, newPower);
            }
        }
    }

    //adm07
    public  void setStatusOfUserByUsername(String usernameOfAdmin, String username, Status newStatus){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(usernameOfAdmin);

        if (employee!= null) {
            if (employee.getPower() == Power.Administrator) {

                EMPLOYEEREPOSITORY.setStatusOfUserByUsername(username, newStatus);
            }
        }

    }

    public  void setStatusOfUserByEmployeeNummer(String usernameOfAdmin, long  employeeNummer, Status newStatus){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(usernameOfAdmin);

        if (employee!= null) {
            if (employee.getPower() == Power.Administrator) {

                EMPLOYEEREPOSITORY.setStatusOfUserByEmployeeNummer(employeeNummer, newStatus);
            }
        }
    }

    //adm08
    public  void addEmployee(Employee employee, String username){

        Employee employee1 = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee1!= null){
            if (employee1.getPower()==(Power.Administrator)){

                EMPLOYEEREPOSITORY.addEmployee(employee);
            }
        }
    }

    public  void addEmployeeIfDatabaseEmpty(Employee employee){

        EMPLOYEEREPOSITORY.addEmployee(employee);

    }



    //Hier sind die gemeisamen Methode mit EmployeeService und BookkeeperServices
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

    public  ArrayList<Object> getMyAllworkHour(String username) {

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

    public   ArrayList<RequestOfHoliday> getAllRequestOfHolidays(String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        ArrayList<RequestOfHoliday> requestOfHolidays = null;

        if (employee != null){

            if (employee.getPower() == Power.Bookkeeper){

                requestOfHolidays = new ArrayList<>();
                ArrayList<RequestOfHoliday> allRequests = REQUESTOFHOLIDAYSREPOSITORY.getAllRequestOfHoliday();

                for (RequestOfHoliday request: allRequests) {

                    if (request.getStatus() == RequestStatus.Waiting){

                        requestOfHolidays.add(request);
                    }
                }
            }
        }

        return requestOfHolidays;
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
