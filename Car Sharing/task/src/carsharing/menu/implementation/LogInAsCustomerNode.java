package carsharing.menu.implementation;

import carsharing.commons.Customer;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CustomerDao;
import carsharing.persistence.company.implementation.CustomerDaoImpl;

import java.util.List;
import java.util.Map;

public class LogInAsCustomerNode extends AbstractMenuNode
{
    public LogInAsCustomerNode(Map<String, String> args)
    {
        super(args);
    }

    @Override
    public void listActions()
    {
        System.out.println("Customer list:");
        super.listActions();
    }

    @Override
    public void update()
    {
        menuNodes.clear();
        CustomerDao dao = new CustomerDaoImpl();
        List<Customer> customers = dao.getAllCustomers(args);
        for (Customer customer : customers)
        {
            CustomerNode customerNode = new CustomerNode(args, customer);
            customerNode.setParent(this);
            menuNodes.add(customerNode);
        }
    }

    @Override
    public AbstractMenuNode performAction()
    {
        System.out.println("The customer list is empty!");
        return getParent();
    }

    @Override
    public String describeNode()
    {
        return "Log in as a customer";
    }
}
