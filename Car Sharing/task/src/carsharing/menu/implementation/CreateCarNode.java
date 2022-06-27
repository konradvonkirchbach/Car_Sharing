package carsharing.menu.implementation;

import carsharing.commons.Company;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CarDao;
import carsharing.persistence.company.implementation.CarDaoImpl;

import java.util.Map;
import java.util.Scanner;

public class CreateCarNode extends AbstractMenuNode
{

    private Company company;

    public CreateCarNode(Map<String, String> args, Company company) {
        super(args);
        this.company = company;
    }

    @Override
    public AbstractMenuNode performAction()
    {
        System.out.println("Enter the car name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        CarDao dao = new CarDaoImpl();
        dao.createCar(args, name, company.getId());
        return parent;
    }

    @Override
    public String describeNode()
    {
        return "Create a car";
    }
}
