package App;

import model.Employee;
import model.Project;
import model.RequestOfHoliday;
import model.enumeration.Power;
import model.enumeration.Status;
import services.AdminServices;
import services.EmployeeServices;
import services.LogInAndLogOutService;

import java.util.ArrayList;

public class ApplicationForAdmin {

    private static final AdminServices adminServices = new AdminServices();

    public static void main(String[] args) {

        String username = "Smerkel";  //"password03"
        String username1 = "Robama";

        // addiert neuen Employee in Datenbaank
        /*for (Employee employee: Helper.createEmployees()) {
            addEmployeeForAdmin(employee, username);

        }*/

        // addiert neuen Employee in Datenbaank wenn die Datenbank leer ist
        /*for (Employee employee: Helper.createEmployees()) {
            addEmployeeForAdminIfDatabaseEmpty(employee);

        }*/

        //login
        //loginForAdmin(username, "password3");

        //adm04 "Bestehender Nutzer löschen"
        //deleteUserByEmployeeNummerForAdmin(2);
        //deleteUserByUsernameForAdmin("schwarz");

        //adm05 "Username eines Nutzer ändern"
        //setUsernameOfUserByOldUsernameForAdmin(username,"schwarz", "Weiss");
        //setUsernameOfUserByEmployeeNummerForAdmin(username,2,"schwarz");

        //adm06 "Befügnisse eines Nutzer ändern"
        //setPowerOfUserByUsernameForAdmin(username, "schwarz", Power.Bookkeeper);
        //setPowerOfUserByEmployeeNummerForAdmin(username,2, Power.Employee);

        //adm07 "Nutzer deaktivieren/Einfrieren"
        //setStatusOfUserByUsernameForAdmin(username, "schwarz", Status.deactived);
        //setStatusOfUserByEmployeeNummerForAdmin(username,2, Status.actived);

        //adm08 "Neuen Nutzer anlegen"
        //Administrator admin = new Administrator("Anl", "Busch", 11, "Abusch", "password11", "anl.busch@gmail.com");
        //loginForAdmin(username, "password03");
        //addEmployeeForAdmin(admin, username);


    }

    // spezifischen Methoden von Administratoren
    // adm04
    public static void deleteUserByUsernameForAdmin(String username){

        adminServices.deleteUserByUsername(username);

        //        boolean deleted = adminServices.deleteUserByUsername(username);
//
//        if (deleted){
//
//            System.out.println("Der Employee mit der Username:"+ username +"wurde gelöscht.");
//        }else {
//
//            System.out.println("Der Employee mit der Username:"+ username +"wurde nicht gelöscht.");
//        }
    }

    public static void deleteUserByEmployeeNummerForAdmin(long employeeNummer){

        adminServices.deleteUserByEmployeeNummer(employeeNummer);

        //        boolean deleted = adminServices.deleteUserByEmployeeNummer(employeeNummer);
//
//        if (deleted){
//
//            System.out.println("Der Employee mit dem Employeenummer:"+ employeeNummer +"wurde gelöscht.");
//        }else {
//
//            System.out.println("Der Employee mit dem Employeenummer:"+ employeeNummer +"wurde nicht gelöscht.");
//        }
    }

    public static void dropEmployeesForAdmin(){

        adminServices.dropEmployees();

        //        boolean dropped = adminServices.dropEmployees();
//
//        if (dropped){
//
//            System.out.println("Alle Employee wurden von datenbank gelöscht.");
//        }else {
//
//            System.out.println("Die Employee wurden von Datenbank nicht gelöscht.");
//        }
    }

    public static void setUsernameOfUserByOldUsernameForAdmin(String usernameOfAdmin, String oldUsername, String newUsername){

        adminServices.setUsernameOfUserByOldUsername(usernameOfAdmin, oldUsername,newUsername);

        //        boolean setted = adminServices.setUsernameOfUserByOldUsername(usernameOfAdmin, oldUsername,newUsername);
//
//        if (setted){
//
//            System.out.println("Der alte Username "+ oldUsername + "wurde zu "+ newUsername +"geändert.");
//        }else{
//
//            System.out.println("Der alte Username "+ oldUsername + "wurde nicht geändert. Etwas ist schief gelaufen.");
//        }
    }

    public static void setUsernameOfUserByEmployeeNummerForAdmin(String usernameOfAdmin, long employeeNummer, String newUsername){

        adminServices.setUsernameOfUserByEmployeeNummer(usernameOfAdmin, employeeNummer,newUsername);

        //        boolean setted = adminServices.setUsernameOfUserByEmployeeNummer(usernameOfAdmin, employeeNummer,newUsername);
//
//        if (setted){
//
//            System.out.println("Der Username von der Employee mit der Employeenummer "+ employeeNummer + "wurde zu "+ newUsername +"geändert.");
//        }else{
//
//            System.out.println("Der Username von der Employee mit der Employeenummer "+ employeeNummer + "wurde nicht geändert. Etwas ist schief gelaufen.");
//        }
    }

    public static void setPowerOfUserByUsernameForAdmin(String usernameOfAdmin, String username, Power newPower){

        adminServices.setPowerOfUserByUsername(usernameOfAdmin, username,newPower);

        //        boolean setted = adminServices.setPowerOfUserByUsername(usernameOfAdmin, username,newPower);
//
//        if (setted){
//
//            System.out.println("Der Power von der Username "+ username + "wurde zu "+ newPower +"geändert.");
//        }else{
//
//            System.out.println("Der Power von der Username "+ username + "wurde nicht geändert. Etwas ist schief gelaufen.");
//        }
    }

    public static void setPowerOfUserByEmployeeNummerForAdmin(String usernameOfAdmin, long  employeeNummer, Power newPower){

        adminServices.setPowerOfUserByEmployeeNummer(usernameOfAdmin, employeeNummer,newPower);

        //        boolean setted = adminServices.setPowerOfUserByEmployeeNummer(usernameOfAdmin, employeeNummer,newPower);
//
//        if (setted){
//
//            System.out.println("Der Power von des Username mit dem Employeenummer "+ employeeNummer + "wurde zu "+ newPower +"geändert.");
//        }else{
//
//            System.out.println("Der Power von des Username mit dem Employeenummer "+ employeeNummer + "wurde nicht geändert. Etwas ist schief gelaufen.");
//        }
    }

    public static void setStatusOfUserByUsernameForAdmin(String usernameOfAdmin, String username, Status newStatus){

        adminServices.setStatusOfUserByUsername(usernameOfAdmin, username,newStatus);

        //        boolean setted = adminServices.setStatusOfUserByUsername(usernameOfAdmin, username,newStatus);
//
//        if (setted){
//
//            System.out.println("Der Status  des Usernames "+ username + "wurde zu "+ newStatus +"geändert.");
//        }else{
//
//            System.out.println("Der Status  des Usernames "+ username + "wurde nicht geändert. Etwas ist schief gelaufen.");
//        }
    }

    public static void setStatusOfUserByEmployeeNummerForAdmin(String usernameOfAdmin, long  employeeNummer, Status newStatus){

        adminServices.setStatusOfUserByEmployeeNummer(usernameOfAdmin, employeeNummer,newStatus);

        //        boolean setted = adminServices.setStatusOfUserByEmployeeNummer(usernameOfAdmin, employeeNummer,newStatus);
//
//        if (setted){
//
//            System.out.println("Der Status  des Usernames mit dem Employeenummer "+ employeeNummer + "wurde zu "+ newStatus +"geändert.");
//        }else{
//
//            System.out.println("Der Status  des Usernames mit dem Employeenummer "+ employeeNummer + "wurde nicht geändert. Etwas ist schief gelaufen.");
//        }
    }

    public static void addEmployeeForAdmin(Employee employee, String username){

        adminServices.addEmployee(employee, username);

        //        boolean added = adminServices.addEmployee(employee, username);
//
//        if (added){
//
//            System.out.println("Der Employee mit dem Username "+ employee.getUserName() + " wurde hinzugefügt.");
//        }else{
//
//            System.out.println("Der Employee mit dem Username "+ employee.getUserName() + " wurde nicht hinzugefügt. Etwas ist schief gelaufen.");
//        }
    }

    public static void addEmployeeForAdminIfDatabaseEmpty(Employee employee){

        adminServices.addEmployeeIfDatabaseEmpty(employee);

        //        boolean added = adminServices.addEmployeeIfDatabaseEmpty(employee);
//
//        if (added){
//
//            System.out.println("Der Employee mit dem Username "+ employee.getUserName() + " wurde hinzugefügt.");
//        }else{
//
//            System.out.println("Der Employee mit dem Username "+ employee.getUserName() + " wurde nicht hinzugefügt. Etwas ist schief gelaufen.");
//        }
    }


    private static void getAllRequestOfHolidaysForAdmin(String username){

        ArrayList<RequestOfHoliday> requests = adminServices.getAllRequestOfHolidays(username);

        if (requests != null){

            System.out.println("RequestID,\t EmployeeNummer,\t NumberOfRequestedHolidays,\t Startdate,\t finishdate,\t Status");

            for (RequestOfHoliday request: requests) {

                System.out.println(request.getRequestID() + ",\t "+ request.getEmployeeNummer()+",\t "+ request.getNumberOfRequestedDay()+
                        ",\t "+ request.getStartDate() +",\t  "+ request.getFinishDate() +",\t "+ request.getStatus());
            }
        }
    }



    // gemeinsamen Methoden mit Employee und Bookkeeper
    private static void loginForAdmin(String username, String password){

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

    public static void logoutForAdmin(String username){

        boolean logged = LogInAndLogOutService.logout(username);

        if (!logged){

            System.out.println("Sie sind abgemeldet");
        }else {

            System.out.println("Sie wurden nicht abgemeldet. Der Username stimmt nicht.");
        }
    }

    private static void getAllUserForAdmin(String username){

        ArrayList<Employee> employees = adminServices.getAllUser(username);

        if (employees != null){

            for (Employee employee: employees) {

                System.out.println(employee.getId() +", "+ employee.getEmployeeNummer() +", "+ employee.getLastname()
                        +", "+ employee.getFirstname() +", "+ employee.getUserName() +", "+ employee.getEmail() +", "+ employee.getPower());
            }
        }
    }

    private static void getAllUserSortByRolleForAdmin(String username){

        ArrayList<ArrayList<Employee>> employeesSortByRole = adminServices.getAllUserSortByRole(username);

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

    private static void getMyAccountDataForAdmin(String username){

        Employee employee = adminServices.getMyAccountData(username);

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

    private static void setMyAccountDataForAdmin(String username, String oldPassword, String newPassword){

        adminServices.setMyAccountData(username, oldPassword, newPassword);

        //        boolean setted = adminServices.setMyAccountData(username, oldPassword, newPassword);
//
//        if (setted){
//
//            System.out.println("Das Passwort wurde geändert.");
//        }else {
//
//            System.out.println("Das alte Passwort is falsch. Versuchen Sie erneut bitte.");
//        }
    }

    private static void getMyAllWorkHourForAdmin(String username){

        ArrayList<Object> data = adminServices.getMyAllworkHour(username);

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

    private static void addProjectWithNonFinalHourForAdmin(Project project, String username){

        adminServices.addProjectWithNonFinalHour(project, username);

        //        boolean added = adminServices.addProjectWithNonFinalHour(project, username);
//
//        if (added){
//
//            System.out.println("Die Stunden wurden richtig eingetragen.");
//        }else {
//
//            System.out.println("Die Stunden wurden nicht eingetragen.");
//        }
    }

    private static void setMyWorkHourForAdmin(Project project){

        adminServices.setMyWorkHour(project);

        //        boolean revise = adminServices.setMyWorkHour(project);
//
//        if (!revise){
//            System.out.println("Der Projekt kann nicht überarbeitet werden, "
//                    +"da er noch nicht eingetragen wurde. Bitte tragen Sie erstmal das Projekt ein.");
//        }else {
//
//            System.out.println("Das Projrkt und die Stunden wurden richtig überarbeitet");
//        }
    }

    private static void finaliseAllMyWorkHourOfMountForAdmin(String username){

        adminServices.finaliseAllMyWorkHourOfMount(username);

        //boolean added = adminServices.finaliseAllMyWorkHourOfMount(username);

        System.out.println("Alle Stunde von "+ username+ " wurde finalisiert");
    }

    private static void getMyWorkOverAndUndertimeForAdmin(String username) {

        ArrayList<Object> data = adminServices.getMyWorkOverAndUndertime(username);

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

    private static void getMyProjectsForAdmin(String username){

        ArrayList<ArrayList<Project>> projects = adminServices.getMyProjects(username);

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

    private static void getMyHolidaysForAdmin(String username) {

        Employee employee = adminServices.getMyHolidays(username);

        if (employee != null){

            System.out.println("Sie haben insgesamt "+ employee.getNUMBEROFHOLIDAY() +" Urlaubstage aber Sie haben nur "
                    + employee.getNumberOfUsedHoliday() +" Urlaubstage davon genommen.");
        }
    }

    private static void getMyRestHolidayForAdmin(String username) {

        Employee employee = adminServices.getMyRestHoliday(username);

        System.out.println("Sie haben insgesamt "+ employee.getNUMBEROFHOLIDAY() +" Urlaubstage aber Sie bleibt nur "
                + employee.getNumberOfRestHoliday() +" Urlaubstage davon frei.");
    }

    private static void requestOfHolidaysForAdmin(RequestOfHoliday requestOfHoliday){

        adminServices.requestOfHolidays(requestOfHoliday);

        //        boolean added = adminServices.requestOfHolidays(requestOfHoliday);
//
//        if (added){
//
//            System.out.println("Die Anfrage von EmployeeNummer :"+ requestOfHoliday.getEmployeeNummer() + " wurde in Datenbank gespeichert.");
//        }
    }

    private static void setUsedAndRestHolidaysForAdmin(Employee employee){

        adminServices.setUsedAndRestHolydays(employee);

        //        boolean setted = adminServices.setUsedAndRestHolydays(employee);
//
//        if (setted){
//
//            System.out.println("Die Daten wurden geändert.");
//        }else {
//
//            System.out.println("Die Daten wurden nicht geändert.");
//        }
    }

    private static void deleteProjectsWithFinalHourForAdmin(){

        adminServices.deleteProjectsWithFinalHour();

        //        boolean deleted = adminServices.deleteProjectsWithFinalHour();
//
//        if (deleted){
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden nicht entfernt.");
//        }
    }

    private static void deleteProjectsWithNonFinalHourForAdmin(){

        adminServices.deleteProjectsWithNonFinalHour();

        //        boolean deleted = adminServices.deleteProjectsWithNonFinalHour();
//
//        if (deleted){
//
//            System.out.println("Die Projekte mit finalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit finalisierten Stunden wurden nicht entfernt.");
//        }
    }

    private static void deleteNonFinalProjectFromProjectsForAdmin(long employeeNummer){

        adminServices.deleteNonFinalProjectFromProjects(employeeNummer);

        //        boolean droped = adminServices.deleteNonFinalProjectFromProjects(employeeNummer);
//
//        if (droped){
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden nicht entfernt.");
//        }
    }


}
