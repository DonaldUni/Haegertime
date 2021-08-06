package repository;

import Exceptions.CustomException;
import model.RequestOfHoliday;
import model.enumeration.Answer;
import model.enumeration.RequestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class RequestRepository {

    private final String REQUESTHOLIDAYS = "RequestHolidays";
    private final String REQUESTID = "RequestID";
    private final String EMPLOYEENUMMER = "Employeenummer";
    private final String NUMBEROFREQUESTEDHOLIDAY = "NumberOfRequestedHoliday";
    private final String STARTDAY = "StartDay";
    private final String FINISHDAY = "FinishDay";
    private final String STATUS = "Status";
    private final String DATUM = "Datum";
    private final String HOUR = "Hour";

    private final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);
    private final String DATABASEPATH = "jdbc:mysql://root@127.0.0.1:3306/haegertime-db?serverTimezone=UTC" ;//"jdbc:h2:file:C:\\\\Users\\\\Donald Samo\\\\Documents\\\\Git Projekte\\\\Haegertime_donald\\\\Datenbank\\\\Datenbank-JDBC";

    private final String user = "root";
    private final String password = "Root21";
    private Connection connection;

    public RequestRepository() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(DATABASEPATH, user, password);

            createRequestHolidays();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createRequestHolidays(){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+ REQUESTHOLIDAYS +
                    " ( "+ REQUESTID + " LONG, "+ EMPLOYEENUMMER + " LONG, "+ NUMBEROFREQUESTEDHOLIDAY + " FLOAT, "+  STARTDAY + " TEXT, "+
                    FINISHDAY + " TEXT, "+ STATUS +" TEXT, "+ DATUM + " TEXT, "+ HOUR + " TEXT );");

            preparedStatement.executeUpdate();
            logger.info("Table Projects created !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existRequestOfHolidayById(long reqestID){

        boolean exist = false;
        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ REQUESTHOLIDAYS +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if (set.getLong(REQUESTID) == reqestID){
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
        return exist;
    }

    /*private boolean existRequestOfHolidayByEmployeenummer(long employeenummer){

        boolean exist = false;
        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ REQUESTHOLIDAYS +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                if (set.getLong(EMPLOYEENUMMER) == employeenummer){
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
        return exist;
    }*/

    public void addRequestOfHoliday(RequestOfHoliday requestOfHoliday){

        openConnection();
        createRequestHolidays();

        // wenn eine Anfrage schon existiert  und noch nicht angewortet ist, wird die überschrieben. Außerdem wird die addiert
        if(existRequestOfHolidayById(requestOfHoliday.getRequestID())){
            openConnection();

            RequestOfHoliday requestOfHoliday1 = getRequestOfHolidayById(requestOfHoliday.getRequestID());

            if (requestOfHoliday1 != null){
                if (requestOfHoliday1.getStatus() == RequestStatus.Waiting){

                    setRequestOfHoliday(requestOfHoliday);
                }
            }
        }else {

            openConnection();

            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO "+ REQUESTHOLIDAYS +
                    " ( "+ REQUESTID +", "+ EMPLOYEENUMMER +", "+ NUMBEROFREQUESTEDHOLIDAY +", "+ STARTDAY + ", "+ FINISHDAY +", "+ STATUS+", "+
                    DATUM+", "+ HOUR +" ) VALUES(?,?,?,?,?,?,?,?) ;")){

                pstmt.setLong(1,requestOfHoliday.getRequestID());
                pstmt.setLong(2,requestOfHoliday.getEmployeeNummer());
                pstmt.setFloat(3, requestOfHoliday.getNumberOfRequestedDay());
                pstmt.setString(4, requestOfHoliday.getStartDate());
                pstmt.setString(5, requestOfHoliday.getFinishDate());
                pstmt.setString(6, requestOfHoliday.getStatus().name());
                pstmt.setString(7, requestOfHoliday.getDatum());
                pstmt.setString(8, requestOfHoliday.getHour());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                try {
                    throw new CustomException("The Request has not been added.", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }
            closeConnection();
        }

    }

    public void setRequestOfHoliday(RequestOfHoliday requestOfHoliday){

        if (existRequestOfHolidayById(requestOfHoliday.getRequestID())){

            openConnection();
            try {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ REQUESTHOLIDAYS + " SET "+ EMPLOYEENUMMER +" = ? ,"+ NUMBEROFREQUESTEDHOLIDAY +" = ? , "
                        + STARTDAY +" = ?, "+ FINISHDAY +" = ?, "+ DATUM +" = ?, "+ HOUR +" = ? WHERE "+ REQUESTID + " = ? ;" );

                pstmt.setLong(1,requestOfHoliday.getEmployeeNummer());
                pstmt.setFloat(2, requestOfHoliday.getNumberOfRequestedDay());
                pstmt.setString(3, requestOfHoliday.getStartDate());
                pstmt.setString(4,requestOfHoliday.getFinishDate());
                pstmt.setString(5,requestOfHoliday.getDatum());
                pstmt.setString(6,requestOfHoliday.getHour());
                pstmt.setLong(7,requestOfHoliday.getRequestID());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                try {
                    throw new CustomException("The Request has not been changed.", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }

            closeConnection();
        }

    }

    public void setStatusOfRequestOfHoliday(long requestId, RequestStatus answerFromBookkeeper){

        if (existRequestOfHolidayById(requestId)){

            openConnection();
            try {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE "+ REQUESTHOLIDAYS + " SET "+ STATUS +" = ? WHERE "+ REQUESTID + " = ? ;" );
                pstmt.setString(1, answerFromBookkeeper.name());
                pstmt.setLong(2, requestId);

                pstmt.executeUpdate();
                logger.info("Das Status des Anfrages von Urlaub mit der Id "+ requestId +" wurde zu "+ answerFromBookkeeper.name()
                        +" gesetzt.");
            } catch (SQLException e) {
                try {
                    throw new CustomException("The status of the request has not been changed.", e);
                } catch (CustomException customException) {
                    customException.printStackTrace();
                }
            }

            closeConnection();
        }
    }

    public RequestOfHoliday getRequestOfHolidayById(long requestId){

        RequestOfHoliday resultRequest = null;
        ArrayList<RequestOfHoliday> requests = getAllRequestOfHoliday();

        for (RequestOfHoliday request: requests) {

            if (request.getRequestID() == requestId){

                resultRequest = request;
                break;
            }
        }

        return resultRequest;
    }

    public ArrayList<RequestOfHoliday> getAllRequestOfHoliday(){

        ArrayList<RequestOfHoliday> requestOfHolidays = new ArrayList<>();
        openConnection();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM  "+ REQUESTHOLIDAYS +" ;")) {

            ResultSet set = pstmt.executeQuery();
            while (set.next()) {
                RequestOfHoliday request = new RequestOfHoliday(set.getLong(REQUESTID), set.getLong(EMPLOYEENUMMER),
                        set.getFloat(NUMBEROFREQUESTEDHOLIDAY), set.getString(STARTDAY), set.getString(FINISHDAY), convertStringToRequestStatus(set.getString(STATUS)),
                        set.getString(DATUM), set.getString(HOUR));
                requestOfHolidays.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

        return requestOfHolidays;
    }

    public RequestStatus convertStringToRequestStatus(String name){

        return switch (name){
            case "Confirm" -> RequestStatus.Confirm;
            case "Reject"  -> RequestStatus.Reject;
            default -> RequestStatus.Waiting;
        };
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

    public void deleteRequestOfHolidays(){

        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(
                "DROP TABLE "+ REQUESTHOLIDAYS +" ;")) {

            pstmt.executeUpdate();

            logger.info("Database RequestOfHolidays deleted");
            createRequestHolidays();
        } catch (SQLException e) {
            try {
                throw new CustomException("The request database has not been deleted.", e);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        }

        closeConnection();
    }
}