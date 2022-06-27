package carsharing.persistence.company;

import carsharing.commons.Company;

import java.util.List;
import java.util.Map;

public interface CompanyDao
{
    List<Company> getAllCompanies(Map<String, String> args);

    Integer createCompany(Map<String, String> args, String name);
}
