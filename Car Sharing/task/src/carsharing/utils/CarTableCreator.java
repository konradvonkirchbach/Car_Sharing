package carsharing.utils;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

public final class CarTableCreator implements DatabaseManipulator
{

    private static final Logger LOGGER = Logger.getLogger(CarTableCreator.class.getName());

    private static final String QUERY_TABLE_EXISTS = "SELECT TABLE_NAME FROM " +
            "information_schema.TABLES " +
            "WHERE TABLE_NAME = 'CAR';";

    private static final String CREATE_TABLE_CAR = "CREATE TABLE CAR (" +
            "ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR UNIQUE NOT NULL, " +
            "COMPANY_ID INT NOT NULL," +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID));";

    public static void createCarTable(Map<String, String> args)
    {
        if (carTableExists(args))
        {
            LOGGER.info("Car table already exists");
            return;
        }

        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 Statement statement = conn.createStatement())
            {
                LOGGER.info("Creating car table");
                statement.execute(CREATE_TABLE_CAR);
                LOGGER.info("Car table created");
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean carTableExists(Map<String, String> args)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(QUERY_TABLE_EXISTS))
            {
                LOGGER.info("Checking if car table exists");
                return statement.executeQuery().next();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
