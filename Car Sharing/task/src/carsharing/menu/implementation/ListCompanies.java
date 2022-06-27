package carsharing.menu.implementation;

import carsharing.commons.Company;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CompanyDao;
import carsharing.persistence.company.implementation.CompanyDaoImpl;

import java.util.List;
import java.util.Map;

public class ListCompanies extends AbstractMenuNode
{

    public ListCompanies(Map<String, String> args) {
        super(args);
    }

    @Override
    public void listActions()
    {
        System.out.println("Choose a company");
        super.listActions();
    }

    @Override
    public void update()
    {
        menuNodes.clear();
        CompanyDao dao = new CompanyDaoImpl();
        List<Company> companies = dao.getAllCompanies(args);
        for (Company company : companies) {
            CompanyNode companyNode = new CompanyNode(args, company);
            companyNode.setParent(this);
            menuNodes.add(companyNode);
        }
    }

    @Override
    public AbstractMenuNode performAction()
    {
        System.out.println("The company list is empty!");
        return parent;
    }

    @Override
    public String describeNode()
    {
        return "Company list";
    }
}
