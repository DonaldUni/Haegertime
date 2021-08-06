package App;

import model.Employee;
import model.Project;
import model.RequestOfHoliday;
import model.enumeration.RequestStatus;
import services.AdminServices;
import services.BookkeeperServices;
import services.LogInAndLogOutService;

import java.util.ArrayList;

public class ApplicationForBookkeeper {

    private static final BookkeeperServices bookkeeperServices = new BookkeeperServices();

    public static void main(String[] args) {

        String username = "Mzugkenberg";  //"password5"
        String password = "password5";
        String newPassword = "password05";
        String username1 = "Robama";

        //bk00 : login als Bookkeeper
        //loginForBookkeeper(username, password);
        //logoutForBookkeeper(username);

        //bk01 : Alle Nutzer anzeigen lassen
        //getAllUserForBookkeeper(username);
        //getAllUserSortByRolleForBookkeeper(username);

        //bk03: Alle Employees, die im Urlaub sind, anzeigen
        //getAllEmployeesInHoliday(username);

        //bk04: Alle beantragten Urlaubstage anzeigen
        //getAllRequestOfHolidaysForBookkeeper(username);

        //bk05: Beantragte Urlaubstage bestätigen/ablehnen
        /*long requestId1 = 6;
        long requestId2 = 7;
        /*long requestId3 = 3;
        long requestId4 = 4;
        long requestId5 = 5;
        long requestId6 = 6;
        long requestId7 = 7;
        long requestId8 = 8;
        long requestId9 = 9;
        long requestId10 = 10;
        RequestStatus answer1 = RequestStatus.Confirm;
        RequestStatus answer2 = RequestStatus.Reject;
        RequestStatus answer3 = RequestStatus.Waiting;

        replyToRequest(username, requestId1, answer1 );
        replyToRequest(username, requestId2, answer1 );*/
        /*replyToRequest(username, requestId3, answer1 );
        replyToRequest(username, requestId4, answer1 );
        replyToRequest(username, requestId5, answer2 );
        replyToRequest(username, requestId6, answer1 );
        replyToRequest(username, requestId7, answer1 );
        replyToRequest(username, requestId8, answer2 );
        replyToRequest(username, requestId9, answer1 );
        replyToRequest(username, requestId10, answer3 );*/


        // RequestOfHolidays entfernen
        //dropRequestOfHolidaysforBookkeeper(username);

        //bk08: Meine Account-Daten anzeigen
        //getMyAccountDataForBookkeeper(username);

        //bk09: Meine Account-Daten ändern
        //setMyAccountDataForBookkeeper(username, password, newPassword); //Password ändern

        //bk10: finalisierte/unfinalisierte Stunden von einzelnen Employees anzeigen
        //getFinalAndNonFinalWorkhourForEachEmployeeForBookkeeper(username);

        //bk11: Über-/Unter-Stunden einzelner Employees anzeigen
        //getFinalAndNonFinalOverAndUndertimeForBookkeeper1(username);




    }


    //spezifichen Methode für Bookkeeper
    /*private static void getFinalAndNonFinalWorkhourForEachEmployeeForBookkeeper(String username){

        ArrayList<Project> finalProjects = BookkeeperService.getFinalProjects(username);
        ArrayList<Project> nonFinalProjects = BookkeeperService.getNonFinalProjects(username);
        ArrayList<Employee> employees = BookkeeperService.getAllUser(username);

        if (employees != null){
            for (Employee employee: employees) {

                long employeenummer = employee.getEmployeeNummer();
                float myFinalWorkhour = 0;
                float myNonFinalWorkhour = 0;
                float allWorkhour = 0;

                for (Project project: finalProjects) {

                    if (project.getEmployeeNummer() == employeenummer){

                        myFinalWorkhour = myFinalWorkhour + project.getWorkhour();
                    }
                }

                for (Project project: nonFinalProjects) {

                    if (project.getEmployeeNummer() == employeenummer){

                        myNonFinalWorkhour = myNonFinalWorkhour + project.getWorkhour();
                    }
                }

                allWorkhour = myFinalWorkhour + myNonFinalWorkhour;

                logger.info("Sie Sind "+ employee.getLastname() +" "+ employee.getFirstname()+"\n"+
                        " Ihr(e) unfinalisierten Arbeitsstunden sind:" + myNonFinalWorkhour+ ". \n"+
                        " Ihr(e) finalisierten Arbeitsstunden sind:" + myFinalWorkhour +". \n"+
                        " Insgesampt haben Sie "+ allWorkhour +" Arbeitsstunden.");
            }
        }
    }*/   // Was in Kommentar ist eine Version der unteren Methode aber mit komplizierter Implementierung
    private static void getFinalAndNonFinalWorkhourForEachEmployeeForBookkeeper(String username) {

        ArrayList<Employee> employees = bookkeeperServices.getAllUser(username);

        if (employees != null) {
            for (Employee employee : employees) {

                ArrayList<Object> data = bookkeeperServices.getMyAllWorkHour(employee.getUserName());

                if (data != null){

                    float myFinalHour = (float) data.get(1);
                    float myNonFinalHour = (float) data.get(2);
                    float allMyWorkHour = (float) data.get(3);

                    System.out.println("Sie Sind "+ employee.getLastname() +" "+ employee.getFirstname()+"\n"+
                            " Ihr(e) unfinalisierten Arbeitstunden sind:" + myNonFinalHour+ ". \n"+
                            " Ihr(e) finalisierten Arbeitstunden sind:" + myFinalHour +". \n"+
                            " Insgesampt haben Sie "+ allMyWorkHour +" Arbeitsstunden.");
                }
            }
        }
    }

    /*private static void getFinalAndNonFinalOverAndUndertimeForBookkeeper(String username) {

        ArrayList<Project> finalProjects = BookkeeperService.getFinalProjects(username);
        ArrayList<Project> nonFinalProjects = BookkeeperService.getNonFinalProjects(username);
        ArrayList<Employee> employees = BookkeeperService.getAllUser(username);

        if (employees != null) {
            for (Employee employee : employees) {

                long employeenummer = employee.getEmployeeNummer();
                float myFinalOverTime = 0;
                float myNonFinalOverTime = 0;
                float allOvertime = 0;
                float myFinalUnderTime = 0;
                float myNonFinalUnderTime = 0;
                float allUndertime = 0;

                for (Project project : finalProjects) {

                    if (project.getEmployeeNummer() == employeenummer) {

                        myFinalOverTime = myFinalOverTime + project.getOvertime();
                        myFinalUnderTime = myFinalUnderTime + project.getUndertime();
                    }
                }

                for (Project project : nonFinalProjects) {

                    if (project.getEmployeeNummer() == employeenummer) {

                        myNonFinalOverTime = myNonFinalOverTime + project.getOvertime();
                        myNonFinalUnderTime = myNonFinalUnderTime + project.getUndertime();
                    }
                }

                allOvertime = myFinalOverTime + myNonFinalOverTime;
                allUndertime = myFinalUnderTime + myNonFinalUnderTime;

                logger.info("Sie Sind " + employee.getLastname() + " " + employee.getFirstname() + "\n" +
                        " Ihr(e) unfinalisierten Überstunden sind:" + myNonFinalOverTime + ". \n" +
                        " Ihr(e) finalisierten Überstunden sind:" + myFinalOverTime + ". \n" +
                        " Insgesampt haben Sie " + allOvertime + " Überstunden." + "\n" +
                        " Ihr(e) unfinalisierten Unterstunden sind:" + myNonFinalUnderTime + ". \n" +
                        " Ihr(e) finalisierten Unterstunden sind:" + myFinalUnderTime + ". \n" +
                        " Insgesampt haben Sie " + allUndertime + " Unterstunden.");
            }
        }
    }*/  // Was in Kommentar ist eine Version der unteren Methode aber mit komplizierter Implementierung
    private static void getFinalAndNonFinalOverAndUndertimeForBookkeeper1(String username) {

        ArrayList<Employee> employees = bookkeeperServices.getAllUser(username);

        if (employees != null) {
            for (Employee employee : employees) {

                ArrayList<Object> data = bookkeeperServices.getMyWorkOverAndUndertime(employee.getUserName());

                if (data != null){

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
        }
    }

    private static void getAllRequestOfHolidaysForBookkeeper(String username){

        ArrayList<RequestOfHoliday> requests = bookkeeperServices.getAllRequestOfHolidays(username);

        if (requests != null){

            System.out.println("RequestID,\t EmployeeNummer,\t NumberOfRequestedHolidays,\t Startdate,\t finishdate,\t Status");

            for (RequestOfHoliday request: requests) {

                System.out.println(request.getRequestID() + ",\t "+ request.getEmployeeNummer()+",\t "+ request.getNumberOfRequestedDay()+
                        ",\t "+ request.getStartDate() +",\t  "+ request.getFinishDate() +",\t "+ request.getStatus());
            }
        }
    }

    private static void replyToRequest(String username, long requestId, RequestStatus answerFromBookkeeper){

        boolean confirmed = (answerFromBookkeeper == RequestStatus.Confirm);
        boolean rejected = (answerFromBookkeeper ==RequestStatus.Reject);

        if (confirmed || rejected){

            bookkeeperServices.replyToRequest(username, requestId, answerFromBookkeeper);
        }else{

            System.out.println("Sie sollen entweder die Anfrage bestätigen oder ablehnen aber nicht zum Status "+
                    RequestStatus.Waiting + " setzen.");
        }
    }

    private static void getAllEmployeesInHoliday(String username){

        ArrayList<Employee> employeesInHolidays = bookkeeperServices.getAllEmployeesInHoliday(username);

        if (employeesInHolidays != null){

            if (employeesInHolidays.size() == 0){

                System.out.println("Es gibt kein Employee in Urlaubs.");
            }else {

                System.out.println("\n \t Liste von Employee in Urlaub:");
                for (int i = 0; i < employeesInHolidays.size(); i++) {

                    Employee employee = employeesInHolidays.get(i);

                    System.out.println(i +") ID: "+ employee.getId() +", Employeenummer: "+ employee.getEmployeeNummer() +", Lastname: "+ employee.getLastname()
                            +", Firstname: "+ employee.getFirstname() +", Username: "+ employee.getUserName() +", Email: "+ employee.getEmail() +
                            ", Power: "+ employee.getPower());

                }
            }
        }else {

            System.out.println("Der username: "+ username +" ist kein Username von Bookkeeper");
        }



    }

    private static void dropRequestOfHolidaysforBookkeeper(String username){

        bookkeeperServices.dropRequestOfHolidays(username);
    }





    // gemeinsamen Methode mit ApplictionForEmployee
    private static void loginForBookkeeper(String username, String password){

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

    public static void logoutForBookkeeper(String username){

        boolean logged = LogInAndLogOutService.logout(username);

        if (!logged){

            System.out.println("Sie sind abgemeldet");
        }else {

            System.out.println("Sie wurden nicht abgemeldet. Der Username stimmt nicht.");
        }
    }

    private static void getAllUserForBookkeeper(String username){

        ArrayList<Employee> employees = bookkeeperServices.getAllUser(username);

        if (employees != null){

            for (Employee employee: employees) {

                System.out.println(employee.getId() +", "+ employee.getEmployeeNummer() +", "+ employee.getLastname()
                        +", "+ employee.getFirstname() +", "+ employee.getUserName() +", "+ employee.getEmail() +", "+ employee.getPower());
            }
        }
    }

    private static void getAllUserSortByRolleForBookkeeper(String username){

        ArrayList<ArrayList<Employee>> employeesSortByRole = bookkeeperServices.getAllUserSortByRole(username);

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

    private static void getMyAccountDataForBookkeeper(String username){

        Employee employee = bookkeeperServices.getMyAccountData(username);

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

    private static void setMyAccountDataForBookkeeper(String username, String oldPassword, String newPassword){


        bookkeeperServices.setMyAccountData(username, oldPassword, newPassword);

        //        boolean setted = bookkeeperServices.setMyAccountData(username, oldPassword, newPassword);
//        if (setted){
//
//            System.out.println("Das Passwort wurde geändert.");
//        }else {
//
//            System.out.println("Das alte Passwort is falsch. Versuchen Sie erneut bitte.");
//        }
    }

    private static void getMyAllWorkHourForBookkeeper(String username){

        ArrayList<Object> data = bookkeeperServices.getMyAllWorkHour(username);

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

    private static void addProjectWithNonFinalHourForBookkeeper(Project project, String username){

        bookkeeperServices.addProjectWithNonFinalHour(project, username);

        //        boolean added = bookkeeperServices.addProjectWithNonFinalHour(project, username);
//
//        if (added){
//
//            System.out.println("Die Stunden wurden richtig eingetragen.");
//        }else {
//
//            System.out.println("Die Stunden wurden nicht eingetragen.");
//        }
    }

    private static void setMyWorkHourForBookkeeper(Project project){

        bookkeeperServices.setMyWorkHour(project);

        //        boolean revise = bookkeeperServices.setMyWorkHour(project);
//
//        if (!revise){
//            System.out.println("Der Projekt kann nicht überarbeitet werden, "
//                    +"da er noch nicht eingetragen wurde. Bitte tragen Sie erstmal das Projekt ein.");
//        }else {
//
//            System.out.println("Das Projrkt und die Stunden wurden richtig überarbeitet");
//        }
    }

    private static void finaliseAllMyWorkHourOfMountForBookkeeper(String username){

        bookkeeperServices.finaliseAllMyWorkHourOfMount(username);
        //boolean added = bookkeeperServices.finaliseAllMyWorkHourOfMount(username);

        System.out.println("Alle Stunde von "+ username+ " wurde finalisiert");
    }

    private static void getMyWorkOverAndUndertimeForBookkeeper(String username)  {

        ArrayList<Object> data = bookkeeperServices.getMyWorkOverAndUndertime(username);

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

    private static void getMyProjectsForBookkeeper(String username){

        ArrayList<ArrayList<Project>> projects = bookkeeperServices.getMyProjects(username);

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

    private static void getMyHolidaysForBookkeeper(String username) {

        Employee employee = bookkeeperServices.getMyHolidays(username);

        if (employee != null){

            System.out.println("Sie haben insgesamt "+ employee.getNUMBEROFHOLIDAY() +" Urlaubstage aber Sie haben nur "
                    + employee.getNumberOfUsedHoliday() +" Urlaubstage davon genommen.");
        }
    }

    private static void getMyRestHolidayForBookkeeper(String username) {

        Employee employee = bookkeeperServices.getMyRestHoliday(username);

        System.out.println("Sie haben insgesamt "+ employee.getNUMBEROFHOLIDAY() +" Urlaubstage aber Sie bleibt nur "
                + employee.getNumberOfRestHoliday() +" Urlaubstage davon frei.");
    }

    private static void requestOfHolidaysForBookkeeper(RequestOfHoliday requestOfHoliday){

        bookkeeperServices.requestOfHolidays(requestOfHoliday);

        System.out.println("Die Anfrage von EmployeeNummer :"+ requestOfHoliday.getEmployeeNummer() + " wurde in Datenbank gespeichert.");

        //        boolean added = bookkeeperServices.requestOfHolidays(requestOfHoliday);
//
//        if (added){
//
//            System.out.println("Die Anfrage von EmployeeNummer :"+ requestOfHoliday.getEmployeeNummer() + " wurde in Datenbank gespeichert.");
//        }
    }

    private static void setUsedAndRestHolydaysForBookkeeper(Employee employee){

        bookkeeperServices.setUsedAndRestHolydays(employee);

        //        boolean setted = bookkeeperServices.setUsedAndRestHolydays(employee);
//
//        if (setted){
//
//            System.out.println("Die Daten wurden geändert.");
//        }else {
//
//            System.out.println("Die Daten wurden nicht geändert.");
//        }
    }

    private static void deleteProjectsWithFinalHourForBookkeeper(){

        bookkeeperServices.deleteProjectsWithFinalHour();

        //        boolean deleted = bookkeeperServices.deleteProjectsWithFinalHour();
//
//        if (deleted){
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit unfinalisierten Stunden wurden nicht entfernt.");
//        }
    }

    private static void deleteProjectsWithNonFinalHourForBookkeeper(){

        bookkeeperServices.deleteProjectsWithNonFinalHour();

        //        boolean deleted = bookkeeperServices.deleteProjectsWithNonFinalHour();
//
//        if (deleted){
//
//            System.out.println("Die Projekte mit finalisierten Stunden wurden entfernt.");
//        }else {
//
//            System.out.println("Die Projekte mit finalisierten Stunden wurden nicht entfernt.");
//        }
    }

    private static void deleteNonFinalProjectFromProjectsForBookkeeper(long employeeNummer){

        bookkeeperServices.deleteNonFinalProjectFromProjects(employeeNummer);

        //        boolean droped = bookkeeperServices.deleteNonFinalProjectFromProjects(employeeNummer);
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
