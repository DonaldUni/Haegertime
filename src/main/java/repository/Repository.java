package repository;

import Exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Repository {

    private final Logger logger = LoggerFactory.getLogger(Repository.class);
    private final String DATABASEPATH = "jdbc:mysql://root@127.0.0.1:3306/haegertime-db?serverTimezone=UTC"  ; //"jdbc:h2:file:C:\\\\Users\\\\Donald Samo\\\\Documents\\\\Git Projekte\\\\Haegertime_donald\\\\Datenbank\\\\Datenbank-JDBC";
    private final String user = "root";
    private final String password = "Root21";
    private Connection connection;

    public Repository() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(DATABASEPATH, user, password);

            createDatabase();

        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

    public abstract void createDatabase();

    public abstract void addInDatabaseElement();

    public abstract void findBy();

    public abstract void exist();

    public abstract ArrayList<Object> getAllElementOfDatabase();

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

    public abstract void deleteElementBy();

    public void deleteDatabase(String databaName){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DROP TABLE "+ databaName +" ;")) {

            pstmt.executeUpdate();
            logger.info("Database "+ databaName +" deleted.");
        } catch (SQLException e) {
            try {
                throw new CustomException("Database "+ databaName +"not deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            };
        }

        closeConnection();

    }


    //getter und setter
    public Logger getLogger() {
        return logger;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
