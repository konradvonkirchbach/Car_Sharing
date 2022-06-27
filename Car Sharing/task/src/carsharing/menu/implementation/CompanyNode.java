package carsharing.menu.implementation;

import carsharing.commons.Company;
import carsharing.menu.AbstractMenuNode;

import java.util.Map;

public class CompanyNode extends AbstractMenuNode
{

    private Company company;

    @Override
    public void listActions()
    {
        System.out.println(String.format("'%s' company", company.getName()));
        super.listActions();
    }

    public CompanyNode(Map<String, String> args, Company company) {
        super(args);
        this.company = company;
        addMenuEntry(new ListCars(args, company));
        addMenuEntry(new CreateCarNode(args, company));
    }

    @Override
    public String describeNode()
    {
        return company.getName();
    }

    @Override
    public AbstractMenuNode getParent() {
        return parent.getParent();
    }
}
