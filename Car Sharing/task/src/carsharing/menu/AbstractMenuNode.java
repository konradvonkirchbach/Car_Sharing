package carsharing.menu;

import carsharing.menu.implementation.MenuEntryPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractMenuNode
{
    protected AbstractMenuNode parent;

    protected Map<String, String> args;

    protected final List<AbstractMenuNode> menuNodes = new ArrayList<>();

    protected String description;

    public AbstractMenuNode() {};

    public AbstractMenuNode(Map<String, String> args) {
        this.args = args;
    }

    public void listActions() {
        for (int index = 0; index < menuNodes.size(); index++) {
            AbstractMenuNode menuNode = menuNodes.get(index);
            String description = null;
            if (menuNode instanceof NavigationReturnNode) {
                description = String.format("%d. %s", 0, menuNode.describeNode());
            } else {
                description = String.format("%d. %s", index + 1, menuNode.describeNode());
            }
            System.out.println(description);
        }
        System.out.println("0. Back");
    }

    public AbstractMenuNode chooseAction(Integer i) {
        if (i == null || i < 0 || i > menuNodes.size()) {
            System.out.println(i + " is not a valid action");
            return getParent();
        } else if (i == 0 && !(this instanceof MenuEntryPoint)) {
            return getParent();
        }
        return menuNodes.get((this instanceof MenuEntryPoint) ? i : i - 1);
    }

    public AbstractMenuNode performAction() {
        return this;
    }

    public abstract String describeNode();

    public void setParent(AbstractMenuNode parent)
    {
        this.parent = parent;
    }

    public AbstractMenuNode getParent() {
        return parent;
    }

    protected void addMenuEntry(AbstractMenuNode entry) {
        menuNodes.add(entry);
        menuNodes.get(menuNodes.size() - 1).setParent(this);
    }

    public boolean isLeaf() {
        return menuNodes.isEmpty();
    }

    public void update() {

    }

}
