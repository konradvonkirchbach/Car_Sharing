package carsharing.menu.implementation;

import carsharing.commons.Customer;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CustomerDao;
import carsharing.persistence.company.implementation.CustomerDaoImpl;

import java.util.Map;

public class CustomerNode extends AbstractMenuNode
{

    private final Customer customer;

    public CustomerNode(Map<String, String> args, Customer customer)
    {
        super(args);
        this.customer = customer;
        addMenuEntry(new RentACarNode(args, customer));
        addMenuEntry(new ReturnRentedCar(args, customer));
        addMenuEntry(new RentedCarsNode(args, customer));
    }

    @Override
    public void update()
    {
        if (customer != null)
        {
            CustomerDao dao = new CustomerDaoImpl();
            Customer update = dao.getCustomerWithId(args, customer.getId());
            customer.setCarId(update.getCarId());
        }
    }

    @Override
    public String describeNode()
    {
        return customer.getName();
    }

    @Override
    public AbstractMenuNode getParent()
    {
        return parent.getParent();
    }
}
