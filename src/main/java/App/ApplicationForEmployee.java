package App;

import model.Employee;
import model.Project;
import model.RequestOfHoliday;
import services.EmployeeServices;
import services.LogInAndLogOutService;

import java.util.ArrayList;

public class ApplicationForEmployee {

    private static final EmployeeServices employeeService = new EmployeeServices();

    public static void main(String[] args) {

        String username = "Smerkel";  //"password03"
        String username1 = "Robama";

        // emp00: logging
        //loginForEmployee(username1, "password1");

        // emp01: getAllUser
        //getAllUserForEmployee(username1);
        //getAllUserSortByRoleForEmployee(username1);

        //emp02: Meine Account Daten anzeigen
        //getMyAccountDataForEmployee(username);

        //emp03: Meine Account Daten ändern
        //setMyAccountDataForEmployee(username,"password3", "password03");


        //emp04: Meine Stunden anzeigen
        //getMyAllworkHourForEmployee(username1);

        //emp05: Meine Stunden eintragen  (unfinalisierte Stunden)
        /*for (Project project: Helper.createProjectsWithNonFinalHour()) {

            addProjectWithNonFinalHourForEmployee(project, username1);
        }*/

        //emp06: Meine nicht finilisierten Stunden überarbeiten
        //setMyWorkHourForEmployee(Helper.reviseHour());

        //emp07: Meine Stunden am Ende des Monats finalisieren
        finaliseAllMyWorkHourOfMountForEmployee("Robama");

        //emp08: Meine Über-/Unter-Stunden anzeigen lassen
        //getMyWorkOverAndUndertimeForEmployee(username1);

        //emp09: Meine Projekte anzeigen lassen
        //getMyProjectsForEmployee("Robama");

        // entfernt ein Projekt aus der Projekt Tabelle mit unfinalisierten Arbeitstunden
        //deleteNonFinalProjectFromProjectsForEmployee(2);

        //entfernt Project Table mit finalisierten Arbeitstunden
        //deleteProjectsWithFinalHourForEmployee();

        //entfernt Project Table mit unfinalisierten Arbeitstunden
        //deleteProjectsWithNonFinalHourForEmployee();

        //emp10: Meine Urlaubstage anzeigen lassen
        //getMyHolidaysForEmployee("Robama");

        // Test von Methode, um die Used- und Rest-Holiday in database zu verändern
        /*Employee employee1 = new Employee("Richard", "Obama",1, "Robama", "password1", "richard_obama@gmail.com");
        employee1.setNumberOfUsedAndRestHoliday(14);
        setUsedAndRestHolydaysForEmployee(employee1);*/

        //emp11: Meine Urlaubstage beantragen
        /*for (RequestOfHoliday request: Helper.createRequestsOfHolidays()) {
            requestOfHolidaysForEmployee(username, request);
        }*/

        //emp12: Meine Resturlaubs anzeigen lassen
        //getMyRestHolidayForEmployee("Robama");

    }


    // Methode für Employee

    private static void loginForEmployee(String username, String password){

        ArrayList<Object> loginData = LogInAndLogOutService.login(username, password);
        boolean logged = (boolean) loginData.get(0);
        boolean freezed = (boolean) loginData.get(1);
        Employee employee = (Employee) loginData.get(2);

        if (logged){

            System.out.println("Sie sind richtig  als Herr/Frau "+ employee.getLastname() + " "+ employee.getFirstname() +" eingelogt.");
        }else {
            if (freezed){

                System.out.println("Ihr Konto wurde einfriert. Sie können sie sich nicht einloggen. :(");
            }else {

                System.out.println("Falscher Benutzername oder Passwort eingegeben. Versuchen Sie erneut.");
            }
        }
    }

    public static void logoutForEmployee(String username){

        boolean logged = LogInAndLogOutService.logout(username);

        if (!logged){

            System.out.println("Sie sind abgemeldet");
        }else {

            System.out.println("Sie wurden nicht abgemeldet. Der Username stimmt nicht.");
        }
    }

    private static void getAllUserForEmployee(String username){

        ArrayList<Employee> employees = employeeService.getAllUser(username);

        if (employees != null){

            for (Employee employee: employees) {

                System.out.println(employee.getId() +", "+ employee.getEmployeeNummer() +", "+ employee.getLastname()
                        +", "+ employee.getFirstname() +", "+ employee.getUserName() +", "+ employee.getEmail() +", "+ employee.getPower());
            }
        }
    }

    private static void getAllUserSortByRoleForEmployee(String username){

        ArrayList<ArrayList<Employee>> employeesSortByRole = employeeService.getAllUserSortByRole(username);

        if (employeesSortByRole != null){

            ArrayList<Employee> employees = employeesSortByRole.get(0);
            ArrayList<Employee> administrators = employeesSortByRole.get(1);
            ArrayList<Employee> bookkeepers = employeesSortByRole.get(2);

            // Alle Nutzer anzeigen
            System.out.println("Liste von Admininstratoren alphabetisch sortiert");
            for (Employee admin: administrators) {
                System.out.println(admin.getId()+", "+ admin.getEmployeeNummer()+", "+admin.getLastname()
                        +", "+admin.getFirstname()+", "+admin.getUserName()+", "+admin.getEmail()+", "+admin.getPower());
            }

            System.out.println("\n Liste von Bookkeepers alphabetisch sortiert");
            for (Employee bookkeeper: bookkeepers) {
                System.out.println(bookkeeper.getId()+", "+ bookkeeper.getEmployeeNummer()+", "+bookkeeper.getLastname()
                        +", "+bookkeeper.getFirstname()+", "+bookkeeper.getUserName()+", "+bookkeeper.getEmail()+", "+bookkeeper.getPower());
            }

            System.out.println("\n Liste von Employee alphabetisch sortiert");
            for (Employee employee1: employees) {
                System.out.println(employee1.getId()+", "+ employee1.getEmployeeNummer()+", "+employee1.getLastname()
                        +", "+employee1.getFirstname()+", "+employee1.getUserName()+", "+employee1.getEmail()+", "+employee1.getPower());
            }
        }

    }

    private static void getMyAccountDataForEmployee(String username){

        Employee employee = employeeService.getMyAccountData(username);

        if (employee != null){

            System.out.println(" Username: " + employee.getUserName() + "\n" +
                    " Nachname: " + employee.getLastname() + "\n" +
                    " Vorname:" + employee.getFirstname() + "\n" +
                    " Employeenummer:" + employee.getEmployeeNummer() + "\n" +
                    " Email:" + employee.getEmail() + "\n" +
                    " Power:" + employee.getPower() + "\n" +
                    " Anzahl von erlaubten Urlaub:" + employee.getNUMBEROFHOLIDAY() + "\n" +
                    " Anzahl von genommene Urlaubstage:" + employee.getNumberOfUsedHoliday() + "\n" +
                    " Anzahl von RestUrlaubstage:" + employee.getNumberOfRestHoliday()
            );
        }
    }

    private static void setMyAccountDataForEmployee(String username, String oldPassword, String newPassword){

        boolean setted = employeeService.setMyAccountData(username, oldPassword, newPassword);

        if (setted){

            System.out.println("Das Passwort wurde geändert.");
        }else {

            System.out.println("Das alte Passwort is falsch. Versuchen Sie erneut bitte.");
        }
    }

    private static void getMyAllworkHourForEmployee(String username){

        ArrayList<Object> data = employeeService.getMyAllWorkHour(username);

        if (data != null){

            Employee employee = (Employee) data.get(0);
            float myFinalHour = (float) data.get(1);
            float myNonFinalHour = (float) data.get(2);
            float allMyWorkHour = (float) data.get(3);

            System.out.println("Sie Sind "+ employee.getLastname() +" "+ employee.getFirstname()+"\n"+
                    " Ihr(e) unfinalisierten Arbeitstunden sind:" + myNonFinalHour+ ". \n"+
                    " Ihr(e) finalisierten Arbeitstunden sind:" + myFinalHour +". \n"+
                    " Insgesampt haben Sie "+ allMyWorkHour +" Arbeitsstunden.");

        }
    }

    private static void addProjectWithNonFinalHourForEmployee(Project project, String username){

        employeeService.addProjectWithNonFinalHour(project, username);

    }

    private static void setMyWorkHourForEmployee(Project project){

        employeeService.setMyWorkHour(project);

    }

    private static void finaliseAllMyWorkHourOfMountForEmployee(String username){

        employeeService.finaliseAllMyWorkHourOfMount(username);

        System.out.println("Alle Stunde von "+ username+ " wurde finalisiert");
    }

    private static void getMyWorkOverAndUndertimeForEmployee(String username) {

        ArrayList<Object> data = employeeService.getMyWorkOverAndUndertime(username);

        if (data != null){

            Employee employee = (Employee) data.get(0);
            float myFinalOverTime = (float) data.get(1);
            float myNonFinalOverTime = (float) data.get(2);
            float allOvertime = (float) data.get(3);
            float myFinalUnderTime = (float) data.get(4);
            float myNonFinalUnderTime = (float) data.get(5);
            float allUndertime = (float) data.get(6);

            System.out.println("Sie Sind "+ employee.getLastname() +" "+ employee.getFirstname()+"\n"+
                    " Ihr(e) unfinalisierten Überstunden sind:" + myNonFinalOverTime+ ". \n"+
                    " Ihr(e) finalisierten Überstunden sind:" + myFinalOverTime +". \n"+
                    " Insgesampt haben Sie "+ allOvertime +" Überstunden." +"\n" +
                    " Ihr(e) unfinalisierten Unterstunden sind:" + myNonFinalUnderTime+ ". \n"+
                    " Ihr(e) finalisierten Unterstunden sind:" + myFinalUnderTime +". \n"+
                    " Insgesampt haben Sie "+ allUndertime +" Unterstunden.");
        }
    }

    private static void getMyProjectsForEmployee(String username){

        ArrayList<ArrayList<Project>> projects = employeeService.getMyProjects(username);

        ArrayList<Project> finalProjects = projects.get(0);
        ArrayList<Project> nonFinalProjects = projects.get(1);

        if (finalProjects.size() == 0){

            System.out.println("Sie haben kein finalisierte Projekt");
        }else {
            System.out.println("Die List Ihr finalisierten Projekte:");
            for (Project project: finalProjects) {
                System.out.println(project.getProjectName());
            }
        }

        if (nonFinalProjects.size() == 0){

            System.out.println("\nSie haben kein unfinalisierte Projekt");

        }else {

            System.out.println("\nDie List Ihr unfinalisierten Projekte:");
            for (Project project: nonFinalProjects) {
                System.out.println(project.getProjectName());
            }
        }
    }

    private static void getMyHolidaysForEmployee(String username) {

        Employee employee = employeeService.getMyHolidays(username);

        if (employee != null){

            System.out.println("Sie haben insgesamt "+ employee.getNUMBEROFHOLIDAY() +" Urlaubstage aber Sie haben nur "
                    + employee.getNumberOfUsedHoliday() +" Urlaubstage davon genommen.");
        }
    }

    private static void getMyRestHolidayForEmployee(String username) {

        Employee employee = employeeService.getMyRestHoliday(username);

        System.out.println("Sie haben insgesamt "+ employee.getNUMBEROFHOLIDAY() +" Urlaubstage aber Sie bleibt nur "
                + employee.getNumberOfRestHoliday() +" Urlaubstage davon frei.");
    }

    private static void requestOfHolidaysForEmployee(String username, RequestOfHoliday requestOfHoliday){

        employeeService.addRequestOfHolidays(username, requestOfHoliday);
    }

    private static void setUsedAndRestHolydaysForEmployee(Employee employee){

        employeeService.setUsedAndRestHolidays(employee);

        //        if (setted){
//
//            System.out.println("Die Daten wurden geändert.");
//        }else {
//
//            System.out.println("Die Daten wurden nicht geändert.");
//        }
    }

    private static void deleteProjectsWithFinalHourForEmployee(){

        employeeService.deleteProjectsWithFinalHour();

        //        if (deleted){
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden nicht entfernt.");
//        }
    }

    private static void deleteProjectsWithNonFinalHourForEmployee(){

        employeeService.deleteProjectsWithNonFinalHour();

        //        if (deleted){
//
//            System.out.println("Die Projekte mit finalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit finalisierten Stunden wurden nicht entfernt.");
//        }
    }

    private static void deleteNonFinalProjectFromProjectsForEmployee(long employeeNummer){

        employeeService.deleteNonFinalProjectFromProjects(employeeNummer);

        //        if (droped){
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden nicht entfernt.");
//        }
    }

}
