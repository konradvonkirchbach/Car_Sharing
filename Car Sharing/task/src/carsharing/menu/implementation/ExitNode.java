package carsharing.menu.implementation;

import carsharing.menu.AbstractMenuNode;
import carsharing.menu.NavigationReturnNode;

import java.util.List;

public class ExitNode extends AbstractMenuNode implements NavigationReturnNode
{

    @Override
    public AbstractMenuNode performAction()
    {
         System.exit(0);
         return null;
    }

    @Override
    public String describeNode()
    {
        return "Exit";
    }
}
