package carsharing.utils;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

public final class UpdateCompanyTable implements DatabaseManipulator
{

    private static final Logger LOGGER = Logger.getLogger(UpdateCompanyTable.class.getName());

    private static final String QUERY_UPDATE_ALREADY_EXECUTED = "SELECT * FROM " +
            "information_schema.table_constraints " +
            "WHERE TABLE_NAME = 'COMPANY'; ";// +
    //"AND constraint_type = 'PRIMARY_KEY';";
    private static final String UPDATE_COMPANY_TABLE_ID_AUTO_INCREMENT = "ALTER TABLE COMPANY " +
            "ALTER ID INT AUTO_INCREMENT;";

    private static final String UPDATE_COMPANY_TABLE_ID_PK = "ALTER TABLE COMPANY " +
            "ADD CONSTRAINT pk_id PRIMARY KEY (ID);";
    private static final String UPDATE_COMPANY_TABLE_NAME_NOTNULL = "ALTER TABLE COMPANY " +
            "ALTER NAME VARCHAR(40) NOT NULL;";

    private static final String UPDATE_COMPANY_TABLE_NAME_UNIQUE = "ALTER TABLE COMPANY " +
            "ADD UNIQUE (NAME);";

    public static void updateCompanyTable(Map<String, String> args)
    {
        if (checkIfContraintExists(args))
        {
            LOGGER.info("Company constraint already exist");
            return;
        }

        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 Statement statement = conn.createStatement())
            {
                LOGGER.info("Making ID auto increment");
                statement.execute(UPDATE_COMPANY_TABLE_ID_AUTO_INCREMENT);
                LOGGER.info("ID is auto incremented");

                LOGGER.info("Making ID primary key");
                statement.execute(UPDATE_COMPANY_TABLE_ID_PK);
                LOGGER.info("ID primary key");

                LOGGER.info("Making NAME not null");
                statement.execute(UPDATE_COMPANY_TABLE_NAME_NOTNULL);
                LOGGER.info("Name is not null");

                LOGGER.info("Making NAME unique");
                statement.execute(UPDATE_COMPANY_TABLE_NAME_UNIQUE);
                LOGGER.info("Name is unique");

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean checkIfContraintExists(Map<String, String> args)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(QUERY_UPDATE_ALREADY_EXECUTED))
            {
                LOGGER.info("Checking if company constraint already exists");
                ResultSet result = statement.executeQuery();
                if (!result.next())
                {
                    return false;
                }
                return "PK_ID".equals(result.getString(3));
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
