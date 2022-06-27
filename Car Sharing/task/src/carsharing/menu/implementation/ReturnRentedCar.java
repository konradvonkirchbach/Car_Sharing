package carsharing.menu.implementation;

import carsharing.commons.Customer;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CustomerDao;
import carsharing.persistence.company.implementation.CustomerDaoImpl;

import java.util.Map;

public class ReturnRentedCar extends AbstractMenuNode
{

    private final Customer customer;

    public ReturnRentedCar(Map<String, String> args, Customer customer)
    {
        super(args);
        this.customer = customer;
    }

    @Override
    public AbstractMenuNode performAction()
    {
        if (customer.getCarId() == null)
        {
            System.out.println("You didn't rent a car!");
        } else
        {
            CustomerDao dao = new CustomerDaoImpl();
            dao.returnCarOfCustomer(args, customer.getId());
            System.out.println("You've returned a rented car!");
        }
        return getParent();
    }

    @Override
    public String describeNode()
    {
        return "Return a rented car";
    }
}
