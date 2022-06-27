package carsharing.menu.implementation;

import carsharing.commons.Car;
import carsharing.commons.Company;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CarDao;
import carsharing.persistence.company.implementation.CarDaoImpl;

import java.util.List;
import java.util.Map;

public class ListCars extends AbstractMenuNode
{

    private Company company;

    public ListCars(Map<String, String> args, Company company) {
        super(args);
        this.company = company;
    }
    @Override
    public AbstractMenuNode performAction() {
        CarDao dao = new CarDaoImpl();
        List<Car> cars = dao.getAllCarsForCompany(args, company.getId());
        int index = 1;
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            for(Car car : cars) {
                System.out.println(String.format("%d. %s", index++, car.getName()));
            }
        }
        System.out.println();
        return parent;
    }
    @Override
    public String describeNode()
    {
        return "Car list";
    }
}
