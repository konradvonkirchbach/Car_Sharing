package carsharing.persistence.company.implementation;

import carsharing.commons.Company;
import carsharing.persistence.company.CompanyDao;
import carsharing.utils.DatabaseManipulator;

import java.security.cert.CertificateParsingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CompanyDaoImpl implements CompanyDao, DatabaseManipulator
{

    private static final Logger LOGGER = Logger.getLogger(CompanyDaoImpl.class.getName());

    private static final String INSERT_INTO_COMPANY = "INSERT INTO COMPANY (name) VALUES (?);";

    private static final String GET_ALL_COMPANIES = "SELECT ID, NAME FROM COMPANY ORDER BY ID;";

    @Override
    public List<Company> getAllCompanies(Map<String, String> args)
    {
        List<Company> result = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(GET_ALL_COMPANIES)) {
                LOGGER.info("Querying for all companies");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Company company = new Company();
                    company.setId(Integer.parseInt(resultSet.getString(1)));
                    company.setName(resultSet.getString(2));
                    result.add(company);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer createCompany(Map<String, String> args, String name)
    {
        Integer affected = null;
        try {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Connection to database");
            try (Connection conn = DriverManager.getConnection(DB_URL + args.get(DATABASE_FILE_NAME));
                 PreparedStatement statement = conn.prepareStatement(INSERT_INTO_COMPANY)) {
                statement.setString(1, name);
                LOGGER.info("Inserting into company table");
                affected = statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return affected;
    }
}
