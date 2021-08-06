package services;

import Helper.Helper;
import repository.EmployeeRepository;
import repository.ProjektRepository;
import repository.RequestRepository;
import model.*;
import model.enumeration.Power;

import java.util.ArrayList;

public class EmployeeServices {

    private  final EmployeeRepository EMPLOYEEREPOSITORY = new EmployeeRepository();
    private  final ProjektRepository PROJECTREPOSITORY = new ProjektRepository();
    private  final RequestRepository REQUESTOFHOLIDAYSREPOSITORY = new RequestRepository();


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

    public  boolean setMyAccountData(String username, String oldPassword, String newPassword) {

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        boolean setted = false;

        if (employee!=null) {

            if (employee.getPassword().equals(oldPassword)) {

                EMPLOYEEREPOSITORY.setPasswordOfEmployee(employee, newPassword);
                setted = true;

            }
        }

        return setted;
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

    public  void addRequestOfHolidays(String username, RequestOfHoliday requestOfHoliday){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);

        if (employee != null){

            REQUESTOFHOLIDAYSREPOSITORY.addRequestOfHoliday(requestOfHoliday);
        }
    }

    public  void setUsedAndRestHolidays(Employee employee){

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