package repository;

import Exceptions.CustomException;
import model.Employee;
import model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class ProjektRepository {

    private final String PROJECTSWITHFINALHOUR = "ProjectsWitFinalHour";
    private final String EMPLOYEENUMMER = "EmployeeNummer";
    private final String IDPROJECT = "IdEmployee";
    private final String PROJECTNAME = "Projektname";
    private final String WORKHOUR = "Workhour";
    private final String OVERTIME = "Overtime";
    private final String UNDERTIME = "Undertime";
    private final String PERIOD = "Period";
    private final String PROJECTWITHNONFINALHOUR = "ProjejectWithNonFinalHour";

    private final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);
    private final String DATABASEPATH = "jdbc:mysql://root@127.0.0.1:3306/haegertime-db?serverTimezone=UTC" ;//"jdbc:h2:file:C:\\\\Users\\\\Donald Samo\\\\Documents\\\\Git Projekte\\\\Haegertime_donald\\\\Datenbank\\\\Datenbank-JDBC";

    private final String user = "root";
    private final String password = "Root21";
    private Connection connection;

    public ProjektRepository() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(DATABASEPATH, user, password);

            createProjectsWithFinalHour();
            createProjectsWithNonFinalHour();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createProjectsWithFinalHour(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+ PROJECTSWITHFINALHOUR +
                    " ( "+ IDPROJECT + " INTEGER, "+ PROJECTNAME + " TEXT, "+ EMPLOYEENUMMER + " LONG, "+  WORKHOUR + " FLOAT, "+
                    OVERTIME + " FLOAT, "+ UNDERTIME +" FLOAT, "+ PERIOD + " TEXT );");

            preparedStatement.executeUpdate();
            logger.info("Table Projects created !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createProjectsWithNonFinalHour(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+ PROJECTWITHNONFINALHOUR +
                    " ( "+ IDPROJECT + " INTEGER, "+ PROJECTNAME + " TEXT, "+ EMPLOYEENUMMER + " LONG, "+  WORKHOUR + " FLOAT, "+
                    OVERTIME + " FLOAT, "+ UNDERTIME +" FLOAT, "+ PERIOD + " TEXT );");

            preparedStatement.executeUpdate();
            logger.info("Table Projects created !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openConnection(){
        try {
            if (connection.isClosed()){
                connection = DriverManager.getConnection(DATABASEPATH, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existHour(Project project, String tableName){

        boolean exist = false;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ tableName +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if ((set.getString(PERIOD).equals(project.getPeriod())&& set.getString(PROJECTNAME).equals(project.getProjectName())
                    && (set.getLong(EMPLOYEENUMMER) == project.getEmployeeNummer()))){

                    exist = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;

    }

    public void setHourOfFinalProject(Project project){

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ PROJECTSWITHFINALHOUR + " SET "+ WORKHOUR +" = ?,"+
                    OVERTIME +" = ?,"+ UNDERTIME +" = ? WHERE "+ EMPLOYEENUMMER + " = ? AND "+PROJECTNAME +" = ? ;" );
            pstmt.setFloat(1, project.getWorkhour());
            pstmt.setFloat(2, project.getOvertime());
            pstmt.setFloat(3, project.getUndertime());
            pstmt.setLong(4, project.getEmployeeNummer());
            pstmt.setString(5, project.getProjectName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new CustomException("The Projekt ist not added ", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();

    }

    public void addProjectWithFinalHour(Project project){

        openConnection();
        createProjectsWithFinalHour();

        if (!existHour(project, PROJECTSWITHFINALHOUR)){
            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO "+ PROJECTSWITHFINALHOUR +
                    " ( "+ IDPROJECT +", "+ PROJECTNAME +", "+ EMPLOYEENUMMER + ", "+ WORKHOUR +", "+ OVERTIME+", "+
                    UNDERTIME+", "+ PERIOD +" ) VALUES(?,?,?,?,?,?,?) ;")){

                pstmt.setLong(1,project.getId_Projeckt());
                pstmt.setString(2, project.getProjectName());
                pstmt.setLong(3, project.getEmployeeNummer());
                pstmt.setFloat(4, project.getWorkhour());
                pstmt.setFloat(5, project.getOvertime());
                pstmt.setFloat(6, project.getUndertime());
                pstmt.setString(7, project.getPeriod());

                pstmt.executeUpdate();
                logger.info("Der Projekt   "+ project.getProjectName() + " wurde in Datenbank "+ PROJECTSWITHFINALHOUR +" gespeichert.");
            } catch (SQLException e) {
                try {
                    throw new CustomException("The Projekt ist not added ", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }
        }else {
            setHourOfFinalProject(project);

        }

        closeConnection();
    }

    public void addProjectWithNonFinalHour(Project project){

        openConnection();
        createProjectsWithFinalHour();

        if (!existHour(project, PROJECTWITHNONFINALHOUR)){

            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO "+ PROJECTWITHNONFINALHOUR +
                    " ( "+ IDPROJECT +", "+ PROJECTNAME +", "+ EMPLOYEENUMMER + ", "+ WORKHOUR +", "+ OVERTIME+", "+
                    UNDERTIME+", "+ PERIOD +" ) VALUES(?,?,?,?,?,?,?) ;")){

                pstmt.setLong(1,project.getId_Projeckt());
                pstmt.setString(2, project.getProjectName());
                pstmt.setLong(3, project.getEmployeeNummer());
                pstmt.setFloat(4, project.getWorkhour());
                pstmt.setFloat(5, project.getOvertime());
                pstmt.setFloat(6, project.getUndertime());
                pstmt.setString(7, project.getPeriod());

                pstmt.executeUpdate();
                logger.info("Der Projekt   "+ project.getProjectName() + " wurde in Datenbank "+ PROJECTSWITHFINALHOUR +"gespeichert.");
            } catch (SQLException e) {
                try {
                    throw new CustomException("The Projekt ist not added ", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }
        }else {
            setHourOfNonFinalProject(project);
        }

        closeConnection();

    }

    public Project findProjectByID(long projectId, String tableName){

        Project project = null;

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ tableName + " ;");
            ResultSet set = pstmt.executeQuery();
            while (set.next()){
                if (set.getInt(IDPROJECT) == projectId){
                     project = new Project(set.getInt(IDPROJECT),set.getString(PROJECTNAME),set.getLong(EMPLOYEENUMMER),
                            set.getFloat(WORKHOUR), set.getFloat(OVERTIME), set.getFloat(UNDERTIME), set.getString(PERIOD));

                }
            }

        } catch (SQLException e) {
            logger.info("Falscher Username eingegeben!");
            e.printStackTrace();
        }
        closeConnection();

        return project;
    }


    public ArrayList<Project> findProjectsWithNonFinalHourByEmployeeNummer(Employee employee){

        ArrayList<Project> projects = new ArrayList<>();

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ PROJECTWITHNONFINALHOUR + " ;");
            ResultSet set = pstmt.executeQuery();
            while (set.next()){
                if (set.getFloat(EMPLOYEENUMMER) == (employee.getEmployeeNummer())){
                    Project project = new Project(set.getLong(IDPROJECT),set.getString(PROJECTNAME),set.getLong(EMPLOYEENUMMER),
                            set.getFloat(WORKHOUR), set.getFloat(OVERTIME), set.getFloat(UNDERTIME), set.getString(PERIOD));
                    projects.add(project);

                }
            }

        } catch (SQLException e) {
            logger.info("Falscher Username eingegeben!");
            e.printStackTrace();
        }
        closeConnection();

        return projects;
    }

    public ArrayList<Project> findProjectsWithFinalHourByEmployeeNummer(Employee employee){

        ArrayList<Project> projects = new ArrayList<>();

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ PROJECTSWITHFINALHOUR + " ;");
            ResultSet set = pstmt.executeQuery();
            while (set.next()){
                if (set.getFloat(EMPLOYEENUMMER) == (employee.getEmployeeNummer())){
                    Project project = new Project(set.getLong(IDPROJECT),set.getString(PROJECTNAME),set.getLong(EMPLOYEENUMMER),
                            set.getFloat(WORKHOUR), set.getFloat(OVERTIME), set.getFloat(UNDERTIME), set.getString(PERIOD));
                    projects.add(project);
                }
            }

        } catch (SQLException e) {
            logger.info("Falscher Username eingegeben!");
            e.printStackTrace();
        }
        closeConnection();

        return projects;
    }

    public boolean existProject(Project project){

        boolean exist = false;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ PROJECTWITHNONFINALHOUR +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if (set.getString(PROJECTNAME).equals(project.getProjectName())){
                    exist = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public void setHourOfNonFinalProject(Project project){

        if (existProject(project)){

            openConnection();
            try {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ PROJECTWITHNONFINALHOUR + " SET "+ WORKHOUR +" = ?,"+
                        OVERTIME +" = ?,"+ UNDERTIME +" = ?,"+ PERIOD +" = ? WHERE "+ EMPLOYEENUMMER + " = ? AND "+PROJECTNAME +" = ? ;" );
                pstmt.setFloat(1, project.getWorkhour());
                pstmt.setFloat(2, project.getOvertime());
                pstmt.setFloat(3, project.getUndertime());
                pstmt.setString(4, project.getPeriod());
                pstmt.setLong(5, project.getEmployeeNummer());
                pstmt.setString(6, project.getProjectName());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                try {
                    throw new CustomException("The time has not been changed.", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }

            closeConnection();
        }
    }

    public void finaliseAllMyWorkHourOfMouth(Employee employee){

        ArrayList<Project> projects = findProjectsWithNonFinalHourByEmployeeNummer(employee);

        for (Project project: projects) {
            addProjectWithFinalHour(project);
        }

        dropByEmployeeNummer(employee.getEmployeeNummer(), PROJECTWITHNONFINALHOUR);

    }

    public void dropByEmployeeNummer(long employeeNummer, String databaseName){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM "+ databaseName +" WHERE "+ EMPLOYEENUMMER +" = ? ;")) {

            pstmt.setLong(1,employeeNummer);
            pstmt.executeUpdate();

            logger.info("Zeile mit Employeenummer "+ employeeNummer +" wurde entfernt");
        } catch (SQLException e) {
            try {
                throw new CustomException("The employee with the nummer "+ employeeNummer +"has not been deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();

    }

    public void deleteProjectsWithFinalHour(){
        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DROP TABLE "+ PROJECTSWITHFINALHOUR +" ;")) {

            pstmt.executeUpdate();
            logger.info("Database "+ PROJECTSWITHFINALHOUR +" deleted");
            createProjectsWithFinalHour();
        } catch (SQLException e) {
            try {
                throw new CustomException("The Projekt has not been deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();

    }

    public void deleteProjectsWithNonFinalHour(){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DROP TABLE "+ PROJECTWITHNONFINALHOUR +" ;")) {

            pstmt.executeUpdate();
            logger.info("Database "+PROJECTWITHNONFINALHOUR +" deleted");
            createProjectsWithNonFinalHour();
        } catch (SQLException e) {
            try {
                throw new CustomException("The Projekt with non final hour has not been deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }

    public ArrayList<Project> getFinalProjects(){

        ArrayList<Project> projects = new ArrayList<>();

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ PROJECTSWITHFINALHOUR + " ;");
            ResultSet set = pstmt.executeQuery();
            while (set.next()){

                Project project = new Project(set.getLong(IDPROJECT),set.getString(PROJECTNAME),set.getLong(EMPLOYEENUMMER),
                        set.getFloat(WORKHOUR), set.getFloat(OVERTIME), set.getFloat(UNDERTIME), set.getString(PERIOD));
                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return projects;
    }

    public ArrayList<Project> getNonFinalProjects(){

        ArrayList<Project> projects = new ArrayList<>();

        openConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "+ PROJECTWITHNONFINALHOUR + " ;");
            ResultSet set = pstmt.executeQuery();
            while (set.next()){

                Project project = new Project(set.getLong(IDPROJECT),set.getString(PROJECTNAME),set.getLong(EMPLOYEENUMMER),
                        set.getFloat(WORKHOUR), set.getFloat(OVERTIME), set.getFloat(UNDERTIME), set.getString(PERIOD));
                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return projects;
    }


    // getter und setter
    public String getPROJECTSWITHFINALHOUR() {
        return PROJECTSWITHFINALHOUR;
    }

    public String getPROJECTWITHNONFINALHOUR() {
        return PROJECTWITHNONFINALHOUR;
    }


}
