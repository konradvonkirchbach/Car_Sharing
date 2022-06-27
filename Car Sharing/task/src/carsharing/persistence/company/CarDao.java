package carsharing.persistence.company;

import carsharing.commons.Car;
import carsharing.commons.Company;

import java.util.List;
import java.util.Map;

public interface CarDao
{

    Map.Entry<Company, Car> getCompanyOfCar(Map<String, String> args, int carId);

    List<Car> getAllCarsForCompany(Map<String, String> args, int companyId);

    List<Car> getAllFreeCarsOfCompany(Map<String, String> args, int companyId);

    Integer createCar(Map<String, String> args, String name, int company);
}
