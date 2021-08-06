package repository;

import Exceptions.CustomException;
import Helper.Helper;
import model.Employee;
import model.enumeration.Power;
import model.enumeration.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeRepository {

    private final String EMPLOYEES = "Employees";
    private final String ID = "Id";
    private final String FIRSTNAME = "Firsname";
    private final String LASTNAME = "Lastname";
    private final String EMPLOYEENUMMER = "EmployeeNummer";
    private final String USERNAME = "UserName";
    private final String PASSWORD = "Password";
    private final String EMAIL = "Email";
    private final String POWER = "Power";
    private final String STATUS = "Status";
    private final String NUMBEROFUSEDHOLIDAY = "NumberOfUsedHoliday";
    private final String NUMBEROFRESTHOLIDAY = "NumberOfRestHoliday";
    private final String NUMBEROFSICKDAY = "NumberOfSickDay";
    private final String  NUMBEROFHOLIDAY = "NumberOfHoliday";
    private final String LOGGED = "Logged";

    private final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);
    private final String DATABASEPATH = "jdbc:mysql://root@127.0.0.1:3306/haegertime-db?serverTimezone=UTC"  ; //"jdbc:h2:file:C:\\\\Users\\\\Donald Samo\\\\Documents\\\\Git Projekte\\\\Haegertime_donald\\\\Datenbank\\\\Datenbank-JDBC";
    private final String user = "root";
    private final String password = "Root21";
    private Connection connection;

    public EmployeeRepository() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(DATABASEPATH, user, password);

            createEmployees();

        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

    public void createEmployees(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+ EMPLOYEES +
                    " ( "+ ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, "+ EMPLOYEENUMMER + " LONG, "+ FIRSTNAME + " TEXT, "+  LASTNAME + " TEXT, "+
                      USERNAME + " TEXT, "+ PASSWORD +" TEXT, "+ EMAIL + " TEXT, "+
                     POWER + " TEXT, "+ STATUS + " TEXT, "+ NUMBEROFUSEDHOLIDAY + " FLOAT, "+
                    NUMBEROFRESTHOLIDAY + " FLOAT, "+ NUMBEROFSICKDAY + " FLOAT, "+ NUMBEROFHOLIDAY+ " INTEGER, "+ LOGGED + " TEXT );");

            preparedStatement.executeUpdate();
            logger.info("Table Employees created !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection(){
        try {
            if (connection.isClosed()){
                connection = DriverManager.getConnection(DATABASEPATH, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existEmployee(Employee employee){
        boolean exist = false;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ EMPLOYEES +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if (set.getLong(EMPLOYEENUMMER) == employee.getEmployeeNummer()){

                    logger.info("Das Element mit der EmployeeNummer : "+ set.getLong(EMPLOYEENUMMER) +
                            " existiert schon und kann nicht zusätzlich im Datenbank hinzugefügt!");
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public boolean existUsername(String username){

        boolean exist = false;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ EMPLOYEES +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if (set.getString(USERNAME).equals(username)){

                    exist = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public boolean existEmployeeNummer(long employeeNummer){

        boolean exist = false;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ EMPLOYEES +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if (set.getLong(EMPLOYEENUMMER) == employeeNummer){

                    logger.info("Der EmployeeNummer : "+ employeeNummer +
                            " ist schon vergeben! Suchen se es einen Neuen bitte!");
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public void addEmployee(Employee employee)  {

        openConnection();
        createEmployees();
        if (!(existUsername(employee.getUserName()) || existEmployeeNummer(employee.getEmployeeNummer()))){
            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO "+ EMPLOYEES +
                    " ("+ EMPLOYEENUMMER +", "+ FIRSTNAME +", "+ LASTNAME + ", "+ USERNAME +", "+ PASSWORD+", "+
                    EMAIL+", "+POWER+ ", "+ STATUS +", "+ NUMBEROFUSEDHOLIDAY +", "+ NUMBEROFRESTHOLIDAY+ ", "+
                    NUMBEROFSICKDAY+", "+ NUMBEROFHOLIDAY +", "+ LOGGED +") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ;")){

                pstmt.setLong(1, employee.getEmployeeNummer());
                pstmt.setString(2, employee.getFirstname());
                pstmt.setString(3, employee.getLastname());
                pstmt.setString(4, employee.getUserName());
                pstmt.setString(5, employee.getPassword());
                pstmt.setString(6, employee.getEmail());

                if (employee.getPower()!=null){
                    pstmt.setString(7, employee.getPower().toString());
                }else {
                    pstmt.setString(7, "");
                }

                if (employee.getStatus()!=null){
                    pstmt.setString(8, employee.getStatus().toString());
                }else {
                    pstmt.setString(8, "");
                }

                pstmt.setFloat(9, employee.getNumberOfUsedHoliday());
                pstmt.setFloat(10, employee.getNumberOfRestHoliday());
                pstmt.setFloat(11, employee.getNumberOfSickDay());
                pstmt.setInt(12, employee.getNUMBEROFHOLIDAY());
                pstmt.setString(13, employee.getLogged());

                pstmt.executeUpdate();
                logger.info("Der Employee mit Username "+ employee.getUserName() + " wurde in Datenbank gespeichert.");

            } catch (SQLException e) {
                try {
                    throw new CustomException("Employee "+ employee.getLastname() +" ist not added.", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }
        }

        closeConnection();
    }

    public Employee findByUsername(String username){

        Employee employee = null;

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ EMPLOYEES + " ;");   //" WHERE "+ USERNAME + " = "+ username +
            ResultSet set = pstmt.executeQuery();
            while (set.next()){
                if (set.getString(USERNAME).equals(username)){
                    employee = new Employee(set.getLong(ID), set.getString(FIRSTNAME), set.getString(LASTNAME),
                            set.getLong(EMPLOYEENUMMER), set.getString(USERNAME), set.getString(PASSWORD), set.getString(EMAIL),
                            Helper.convertStringToPower(set.getString(POWER)), Helper.convertStringToStatus(set.getString(STATUS)),set.getFloat(NUMBEROFUSEDHOLIDAY),set.getFloat(NUMBEROFRESTHOLIDAY),
                            set.getFloat(NUMBEROFSICKDAY),set.getString(LOGGED));

                    break;
                }
            }

        } catch (SQLException e) {
            logger.info("Falscher Username eingegeben!");
            e.printStackTrace();
        }
        closeConnection();

        return employee;
    }

    public Employee findByEmployeenummer(long employeenummer){

        Employee employee = null;

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ EMPLOYEES + " ;");   //" WHERE "+ USERNAME + " = "+ username +
            ResultSet set = pstmt.executeQuery();
            while (set.next()){
                if (set.getLong(EMPLOYEENUMMER) == employeenummer){
                    employee = new Employee(set.getLong(ID), set.getString(FIRSTNAME), set.getString(LASTNAME),
                            set.getLong(EMPLOYEENUMMER), set.getString(USERNAME), set.getString(PASSWORD), set.getString(EMAIL),
                            Helper.convertStringToPower(set.getString(POWER)), Helper.convertStringToStatus(set.getString(STATUS)),set.getFloat(NUMBEROFUSEDHOLIDAY),set.getFloat(NUMBEROFRESTHOLIDAY),
                            set.getFloat(NUMBEROFSICKDAY),set.getString(LOGGED));

                    break;
                }
            }

        } catch (SQLException e) {
            logger.info("Falscher Username eingegeben!");
            e.printStackTrace();
        }
        closeConnection();

        return employee;
    }

    public ArrayList<Employee> getAllUser(){

        ArrayList<Employee> employees = new ArrayList<>();
        openConnection();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ EMPLOYEES +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                Employee employee = new Employee(set.getLong(ID), set.getString(FIRSTNAME), set.getString(LASTNAME),
                        set.getLong(EMPLOYEENUMMER), set.getString(USERNAME), set.getString(PASSWORD), set.getString(EMAIL),
                        Helper.convertStringToPower(set.getString(POWER)), Helper.convertStringToStatus(set.getString(STATUS)),set.getFloat(NUMBEROFUSEDHOLIDAY),set.getFloat(NUMBEROFRESTHOLIDAY),
                        set.getFloat(NUMBEROFSICKDAY));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return employees;
    }

    public void setPasswordOfEmployee(Employee employee, String newPassword){

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ PASSWORD +" = ? WHERE "+ EMPLOYEENUMMER + " = ? ;" );
            pstmt.setString(1, newPassword);
            pstmt.setLong(2,employee.getEmployeeNummer());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("Employee "+ employee.getLastname() +" ist not added.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }        }

        closeConnection();
    }

    public void setUsedAndRestHolidays(Employee employee){

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ NUMBEROFUSEDHOLIDAY +" = ? , "
                            + NUMBEROFRESTHOLIDAY +" = ? WHERE "+ EMPLOYEENUMMER + " = ? ;" );
            pstmt.setFloat(1, employee.getNumberOfUsedHoliday());
            pstmt.setFloat(2, employee.getNumberOfRestHoliday());
            pstmt.setLong(3,employee.getEmployeeNummer());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("Used Holidays and Rest Holdays of employee "+ employee.getLastname() +" ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public void deleteEmployees(){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DROP TABLE "+ EMPLOYEES +" ;")) {

            pstmt.executeUpdate();
            logger.info("Database Employees deleted");
        } catch (SQLException e) {
            try {
                throw new CustomException("Database not deleted. ", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            };
        }

        createEmployees();

        closeConnection();

    }

    public void deleteEmployeeByUsername(String userName ){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM "+ EMPLOYEES +" WHERE "+ USERNAME +" = ? ;")) {

            pstmt.setString(1,userName);
            pstmt.executeUpdate();

            logger.info("Zeile mit dem Employeenummer "+ userName +" wurde von Datenbank"+ EMPLOYEES +"entfernt");
        } catch (SQLException e) {
            try {
                throw new CustomException("Empoyee ist not deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();

    }

    public void deleteEmployeeByEmployeeNummer(long employeeNummer ){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM "+ EMPLOYEES +" WHERE "+ EMPLOYEENUMMER +" = ? ;")) {

            pstmt.setLong(1,employeeNummer);
            pstmt.executeUpdate();

            logger.info("Zeile mit Employeenummer "+ employeeNummer +" wurde von Datenbank "+ EMPLOYEES +" entfernt");
        } catch (SQLException e) {
            try {
                throw new CustomException("Employee with employeenummer "+ employeeNummer +" ist not deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();

    }

    public void setUsernameOfUserByUsername(String oldUsername, String newUsername){

        openConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ USERNAME +" = ? WHERE "+ USERNAME + " = ? ;" );
            pstmt.setString(1, newUsername);
            pstmt.setString(2,oldUsername);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("Employee with old username"+ oldUsername +" ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }        }

        closeConnection();

    }

    public void setUsernameOfUserByEmployeeNummer(long employeeNummer, String newUsername){

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ USERNAME +" = ? WHERE "+ EMPLOYEENUMMER + " = ? ;" );
            pstmt.setString(1, newUsername);
            pstmt.setLong(2,employeeNummer);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("Employee with employeenummer"+ employeeNummer +" ist modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public void setPowerOfUserByUsername(String username, Power newPower){

        openConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ POWER +" = ? WHERE "+ USERNAME + " = ? ;" );
            pstmt.setString(1, newPower.toString() );
            pstmt.setString(2,username);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("The Power of Employee "+ username +" ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public void setPowerOfUserByEmployeeNummer(long employeeNummer, Power newPower){

        openConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ POWER +" = ? WHERE "+ EMPLOYEENUMMER + " = ? ;" );
            pstmt.setString(1, newPower.toString());
            pstmt.setLong(2,employeeNummer);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("The Power of the employee with the employeenummer "+ employeeNummer +" ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public void setStatusOfUserByUsername(String username, Status newStatus){

        openConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ STATUS +" = ? WHERE "+ USERNAME + " = ? ;" );
            pstmt.setString(1, newStatus.toString() );
            pstmt.setString(2,username);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("The Status of the employee "+ username +" ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }        }

        closeConnection();
    }

    public void setStatusOfUserByEmployeeNummer(long employeeNummer, Status newStatus){

        openConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ STATUS +" = ? WHERE "+ EMPLOYEENUMMER + " = ? ;" );
            pstmt.setString(1, newStatus.toString() );
            pstmt.setLong(2,employeeNummer);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            try {
                throw new CustomException("The Status of the employee with the employeenummer "+ employeeNummer +" ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public void setLoggedValue(Employee employee, boolean logged){

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ EMPLOYEES + " SET "+ LOGGED +" = ? WHERE "+ EMPLOYEENUMMER + " = ? ;" );

            pstmt.setString(1, Helper.convertBooleantoString(logged));
            pstmt.setLong(2, employee.getEmployeeNummer());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("The logged Value ist not modified.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public Connection getConnection() {
        return connection;
    }
}
