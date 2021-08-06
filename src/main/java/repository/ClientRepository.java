package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientRepository extends Repository {

    private final String VORNAME = "Firstname";
    private final String LASTNAME = "Lastname";
    private final String CLIENTID = "ClientId";
    private final String ENTERPRISE = "Enterprise";
    private final String TABLENAME = "Clients";




    @Override
    public void createDatabase() {

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "+ TABLENAME +
                    " ( "+ CLIENTID + " LONG, "+ VORNAME + " TEXT, "+ LASTNAME + " TEXT, "+  ENTERPRISE + " TEXT) ;");

            preparedStatement.executeUpdate();
            getLogger().info("Table Projects created !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void addInDatabaseElement() {

    }

    @Override
    public void findBy() {

    }

    @Override
    public void exist() {

    }

    @Override
    public ArrayList<Object> getAllElementOfDatabase() {
        return null;
    }

    @Override
    public void deleteElementBy() {

    }
}
