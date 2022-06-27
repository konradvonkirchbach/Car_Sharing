package carsharing.utils;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

public final class CompanyTableCreator implements DatabaseManipulator
{
    private static final Logger LOGGER = Logger.getLogger(CompanyTableCreator.class.getName());

    private static final String QUERY_TABLE_EXISTS = "SELECT TABLE_NAME FROM " +
            "information_schema.TABLES " +
            "WHERE TABLE_NAME = 'COMPANY';";
    private static final String CREATE_COMPANY_TABLE = "CREATE TABLE COMPANY (" +
            "ID INT, " +
            "NAME VARCHAR(50));";

    public static void createCompanyTable(Map<String, String> args)
    {
        if (checkIfTableExists(args))
        {
            LOGGER.info("Company table already exists");
            return;
        }

        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 Statement statement = conn.createStatement())
            {
                LOGGER.info("Creating company table");
                statement.execute(CREATE_COMPANY_TABLE);
                LOGGER.info("Company table created");
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean checkIfTableExists(Map<String, String> args)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(QUERY_TABLE_EXISTS))
            {
                LOGGER.info("Checking if company table exists");
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
