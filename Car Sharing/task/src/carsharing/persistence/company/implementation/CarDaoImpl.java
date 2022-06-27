package carsharing.persistence.company.implementation;

import carsharing.commons.Car;
import carsharing.commons.Company;
import carsharing.persistence.company.CarDao;
import carsharing.utils.DatabaseManipulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CarDaoImpl implements CarDao, DatabaseManipulator
{
    private static final Logger LOGGER = Logger.getLogger(CarDaoImpl.class.getName());

    private static final String GET_ALL_CARS = "SELECT * FROM CAR " +
            "WHERE COMPANY_ID = ? " +
            "ORDER BY ID;";

    private static final String GET_ALL_FREE_CARS = "SELECT CAR.ID as carId, " +
            "CAR.NAME as carName " +
            "FROM CAR " +
            "WHERE (CAR.ID NOT IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL)) " +
            "AND COMPANY_ID = ? " +
            "ORDER BY ID; ";

    private static final String CREATE_CAR = "INSERT INTO CAR (name, company_id) " +
            "VALUES (?, ?);";

    private static final String GET_COMPANY_OF_CAR = "SELECT " +
            "COMPANY.ID as companyId, " +
            "COMPANY.NAME as companyName, " +
            "CAR.ID as carId, " +
            "CAR.NAME as carName " +
            "FROM COMPANY " +
            "INNER JOIN CAR ON CAR.COMPANY_ID = COMPANY.ID " +
            "WHERE CAR.ID = ?;";


    @Override
    public Map.Entry<Company, Car> getCompanyOfCar(Map<String, String> args, int carId)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(GET_COMPANY_OF_CAR))
            {
                statement.setInt(1, carId);
                LOGGER.info("Querying for copmany");
                ResultSet rs = statement.executeQuery();
                if (!rs.next())
                {
                    LOGGER.warning("No found for car with id " + carId);
                    return null;
                }
                Company company = new Company();
                company.setId(Integer.parseInt(rs.getString("companyId")));
                company.setName(rs.getString("companyName"));

                Car car = new Car();
                car.setId(Integer.parseInt(rs.getString("carId")));
                car.setName(rs.getString("carName"));
                return Map.entry(company, car);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> getAllCarsForCompany(Map<String, String> args, int companyId)
    {
        List<Car> result = new ArrayList<>();
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(GET_ALL_CARS))
            {
                statement.setInt(1, companyId);
                LOGGER.info("Querying for all cars");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    Car car = new Car();
                    car.setId(Integer.parseInt(resultSet.getString(1)));
                    car.setName(resultSet.getString(2));
                    result.add(car);
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Car> getAllFreeCarsOfCompany(Map<String, String> args, int companyId)
    {
        List<Car> result = new ArrayList<>();
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(GET_ALL_FREE_CARS))
            {
                statement.setInt(1, companyId);
                LOGGER.info("Querying for all free cars");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    Car car = new Car();
                    car.setId(Integer.parseInt(resultSet.getString(1)));
                    car.setName(resultSet.getString(2));
                    result.add(car);
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer createCar(Map<String, String> args, String name, int company)
    {
        Integer affected = null;
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(CREATE_CAR))
            {
                statement.setString(1, name);
                statement.setInt(2, company);
                LOGGER.info("Inserting into car table");
                affected = statement.executeUpdate();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return affected;
    }
}
