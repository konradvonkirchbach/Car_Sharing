package carsharing.persistence.company.implementation;

import carsharing.commons.Customer;
import carsharing.persistence.company.CustomerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class CustomerDaoImpl implements CustomerDao
{

    private static final Logger LOGGER = Logger.getLogger(CustomerDaoImpl.class.getName());

    private static final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER (NAME) " +
            "VALUES ?;";

    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER;";

    private static final String RETURN_CAR_WITH_CUSTOMER_ID = "UPDATE CUSTOMER " +
            "SET RENTED_CAR_ID = NULL " +
            "WHERE ID = ?;";

    private static final String RENT_CAR = "UPDATE CUSTOMER " +
            "SET RENTED_CAR_ID = ? " +
            "WHERE ID = ?";

    private static final String GET_CUSTOMER_WITH_ID = "SELECT * FROM CUSTOMER " +
            "WHERE ID = ?;";

    @Override
    public Integer returnCarOfCustomer(Map<String, String> args, int customerId)
    {
        Integer affected = null;
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(RETURN_CAR_WITH_CUSTOMER_ID))
            {
                statement.setInt(1, customerId);
                LOGGER.info("Returning car of customer with id " + customerId);
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

    @Override
    public Customer getCustomerWithId(Map<String, String> args, int customerId)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_WITH_ID))
            {
                statement.setInt(1, customerId);
                LOGGER.info("Querying for customer with id " + customerId);
                ResultSet rs = statement.executeQuery();
                if (!rs.next())
                {
                    LOGGER.warning("No customer found!");
                    return null;
                }
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(rs.getString(1)));
                customer.setName(rs.getString(2));
                String carId = rs.getString(3);
                if (carId != null)
                {
                    customer.setCarId(Integer.parseInt(carId));
                }
                return customer;
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
    public Integer customerRentsCar(Map<String, String> args, int customerId, int carId)
    {
        Integer affected = null;
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(RENT_CAR))
            {
                statement.setInt(1, carId);
                statement.setInt(2, customerId);
                LOGGER.info(String.format("Customer %d is renting car $d", customerId, carId));
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

    @Override
    public List<Customer> getAllCustomers(Map<String, String> args)
    {
        List<Customer> customers = new ArrayList<>();
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(GET_ALL_CUSTOMERS))
            {
                LOGGER.info("Querying for all customers");
                ResultSet rs = statement.executeQuery();
                while (rs.next())
                {
                    Customer customer = new Customer();
                    customer.setId(Integer.parseInt(rs.getString(1)));
                    customer.setName(rs.getString(2));
                    String carId = rs.getString(3);
                    if (carId != null)
                    {
                        customer.setCarId(Integer.parseInt(carId));
                    }
                    customers.add(customer);
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Integer createCustomer(Map<String, String> args, String name)
    {
        Integer affected = null;
        try
        {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connecting to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(CREATE_CUSTOMER))
            {
                statement.setString(1, name);
                LOGGER.info("Inserting into customer table");
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
