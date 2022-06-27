package carsharing.menu.implementation;

import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CustomerDao;
import carsharing.persistence.company.implementation.CustomerDaoImpl;

import java.util.Map;
import java.util.Scanner;

public class CreateCustomerNode extends AbstractMenuNode
{

    public CreateCustomerNode(Map<String, String> args)
    {
        super(args);
    }

    @Override
    public AbstractMenuNode performAction()
    {
        System.out.println("Enter the customer name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        CustomerDao dao = new CustomerDaoImpl();
        dao.createCustomer(args, name);

        System.out.println("The customer was added!");

        return getParent();
    }

    @Override
    public String describeNode()
    {
        return "Create a customer";
    }
}
