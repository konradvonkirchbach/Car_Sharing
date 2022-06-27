package carsharing.menu.implementation;

import carsharing.commons.Car;
import carsharing.commons.Company;
import carsharing.commons.Customer;
import carsharing.menu.AbstractMenuNode;
import carsharing.persistence.company.CarDao;
import carsharing.persistence.company.CompanyDao;
import carsharing.persistence.company.CustomerDao;
import carsharing.persistence.company.implementation.CarDaoImpl;
import carsharing.persistence.company.implementation.CompanyDaoImpl;
import carsharing.persistence.company.implementation.CustomerDaoImpl;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RentACarNode extends AbstractMenuNode
{

    private final Customer customer;

    public RentACarNode(Map<String, String> args, Customer customer)
    {
        super(args);
        this.customer = customer;
    }

    @Override
    public AbstractMenuNode performAction()
    {
        CompanyDao companyDao = new CompanyDaoImpl();
        List<Company> companies = companyDao.getAllCompanies(args);

        if (companies.isEmpty())
        {
            System.out.println("The company list is empty!");
            return getParent();
        }

        if (customer.getCarId() != null)
        {
            System.out.println("You've already rented a car!");
            return getParent();
        }

        int index = 1;
        for (Company company : companies)
        {
            System.out.println(String.format("%d. %s", index++, company.getName()));
        }
        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);
        int companyIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (companyIndex == -1)
        {
            return getParent();
        }

        CarDao carDao = new CarDaoImpl();
        List<Car> cars = carDao.getAllFreeCarsOfCompany(args, companies.get(companyIndex).getId());

        index = 1;
        for (Car car : cars)
        {
            System.out.println(String.format(String.format("%d. %s", index++, car.getName())));
        }
        System.out.println("0. Back");

        int carIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (carIndex == -1)
        {
            return getParent();
        }

        CustomerDao customerDao = new CustomerDaoImpl();
        customerDao.customerRentsCar(args, customer.getId(), cars.get(carIndex).getId());

        System.out.println(String.format("You rented '%s'", cars.get(carIndex).getName()));

        return getParent();
    }

    @Override
    public String describeNode()
    {
        return "Rent a car";
    }
}
