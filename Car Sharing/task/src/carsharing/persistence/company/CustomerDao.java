package carsharing.persistence.company;

import carsharing.commons.Customer;
import carsharing.utils.DatabaseManipulator;

import java.util.List;
import java.util.Map;

public interface CustomerDao extends DatabaseManipulator
{

    Integer returnCarOfCustomer(Map<String, String> args, int customerId);

    Customer getCustomerWithId(Map<String, String> args, int customerId);

    Integer customerRentsCar(Map<String, String> args, int customerId, int carId);

    List<Customer> getAllCustomers(Map<String, String> args);

    Integer createCustomer(Map<String, String> args, String name);
}
