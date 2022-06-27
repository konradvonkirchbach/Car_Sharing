package carsharing;

import carsharing.menu.Menu;
import carsharing.utils.*;

import java.util.Map;

public class Main
{

    public static void main(String[] args)
    {
        Map<String, String> arguments = ArgumentExtractor.extractArguments(args);
        try
        {
            CompanyTableCreator.createCompanyTable(arguments);
            UpdateCompanyTable.updateCompanyTable(arguments);
            CarTableCreator.createCarTable(arguments);
            CustomerTableCreator.createCarTable(arguments);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Menu menu = new Menu(arguments);
        menu.run();

    }
}