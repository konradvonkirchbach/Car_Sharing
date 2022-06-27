package carsharing.menu.implementation;

import carsharing.menu.AbstractMenuNode;
import carsharing.menu.NavigationReturnNode;

import java.util.Map;

public final class MenuEntryPoint extends AbstractMenuNode
{
    public MenuEntryPoint(Map<String, String> args)
    {
        super(args);
        addMenuEntry(new ExitNode());
        addMenuEntry(new LogInAsManagerNode(args));
        addMenuEntry(new LogInAsCustomerNode(args));
        addMenuEntry(new CreateCustomerNode(args));
    }

    @Override
    public void listActions()
    {
        for (int index = 1; index < menuNodes.size(); index++)
        {
            AbstractMenuNode menuNode = menuNodes.get(index);
            String description = null;
            if (menuNode instanceof NavigationReturnNode)
            {
                description = String.format("%d. %s", 0, menuNode.describeNode());
            } else
            {
                description = String.format("%d. %s", index, menuNode.describeNode());
            }
            System.out.println(description);
        }
        System.out.println("0. Exit");
    }

    @Override
    public String describeNode()
    {
        return "Root";
    }
}
