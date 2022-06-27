package carsharing.utils;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

public final class CustomerTableCreator implements DatabaseManipulator
{

    private static final Logger LOGGER = Logger.getLogger(CustomerTableCreator.class.getName());

    private static final String QUERY_TABLE_EXISTS = "SELECT TABLE_NAME FROM " +
            "information_schema.TABLES " +
            "WHERE TABLE_NAME = 'CUSTOMER';";

    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE CUSTOMER (" +
            "ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR UNIQUE NOT NULL, " +
            "RENTED_CAR_ID INT," +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID));";

    public static void createCarTable(Map<String, String> args)
    {
        if (customerTableExists(args))
        {
            LOGGER.info("Customer table already exists");
            return;
        }

        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 Statement statement = conn.createStatement())
            {
                LOGGER.info("Creating customer table");
                statement.execute(CREATE_TABLE_CUSTOMER);
                LOGGER.info("Customer table created");
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean customerTableExists(Map<String, String> args)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(QUERY_TABLE_EXISTS))
            {
                LOGGER.info("Checking if customer table exists");
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
