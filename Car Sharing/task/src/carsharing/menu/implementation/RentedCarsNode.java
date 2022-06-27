package carsharing.menu.implementation;

import carsharing.commons.Car;
import carsharing.commons.Company;
import carsharing.commons.Customer;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CarDao;
import carsharing.persistence.company.implementation.CarDaoImpl;

import java.util.Map;

public class RentedCarsNode extends AbstractMenuNode
{

    private final Customer customer;

    public RentedCarsNode(Map<String, String> args, Customer customer)
    {
        super(args);
        this.customer = customer;
    }

    @Override
    public AbstractMenuNode performAction()
    {
        if (customer.getCarId() == null)
        {
            System.out.println("You didn't rent a car!");
        } else
        {
            CarDao dao = new CarDaoImpl();
            Map.Entry<Company, Car> result = dao.getCompanyOfCar(args, customer.getCarId());
            System.out.println(String.format("You rented car:%n%s%nCompany:%n%s",
                    result.getValue().getName(),
                    result.getKey().getName()));
        }
        return getParent();
    }

    @Override
    public String describeNode()
    {
        return "My rented car";
    }
}
