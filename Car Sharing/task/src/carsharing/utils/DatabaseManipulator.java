package carsharing.utils;

public interface DatabaseManipulator
{
    String JDBC_DRIVER = "org.h2.Driver";

    String DB_URL = "jdbc:h2:./src/carsharing/db/";

    String DATABASE_FILE_NAME = "databaseFileName";
}
