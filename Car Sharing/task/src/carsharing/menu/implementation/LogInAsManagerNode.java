package carsharing.menu.implementation;

import carsharing.menu.AbstractMenuNode;

import java.util.Map;

public class LogInAsManagerNode extends AbstractMenuNode
{

    public LogInAsManagerNode(Map<String, String> args)
    {
        super(args);
        addMenuEntry(new ListCompanies(args));
        addMenuEntry(new CreateCompanyNode(args));
        addMenuEntry(new LogInAsCustomerNode(args));
        addMenuEntry(new CreateCustomerNode(args));
    }

    @Override
    public String describeNode()
    {
        return "Log in as a manager";
    }
}
