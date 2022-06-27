package carsharing.menu.implementation;

import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CompanyDao;
import carsharing.persistence.company.implementation.CompanyDaoImpl;

import java.util.Map;
import java.util.Scanner;

public class CreateCompanyNode extends AbstractMenuNode
{

    public CreateCompanyNode(Map<String, String> args) {
        super(args);
    }

    @Override
    public AbstractMenuNode performAction() {
        System.out.println("Enter the company name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        CompanyDao dao = new CompanyDaoImpl();
        dao.createCompany(args, name);
        return parent;
    }

    @Override
    public String describeNode()
    {
        return "Create a company";
    }
}
