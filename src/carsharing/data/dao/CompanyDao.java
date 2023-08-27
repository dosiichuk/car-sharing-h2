package carsharing.data.dao;

import carsharing.data.ConnectionFactory;
import carsharing.data.dao.interfaces.ICompanyDao;
import carsharing.data.entities.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao implements ICompanyDao {
    private ConnectionFactory factory;

    public CompanyDao(String fileName) {
        this.factory = new ConnectionFactory(fileName);
    }

    public void dropCompanyTable() {
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql =  "DROP TABLE IF EXISTS COMPANY";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void createCompanyTable() {
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql =  "CREATE TABLE IF NOT EXISTS COMPANY " +
                    "(ID INT AUTO_INCREMENT, " +
                    " name VARCHAR(255) UNIQUE NOT NULL, PRIMARY KEY (ID));";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void addCompany(String companyName, int id) {
        try (
                Connection connection = this.factory.getConn();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO COMPANY (name, ID) VALUES (?, ?);");
        ) {
            stmt.setString(1, companyName);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql = "SELECT * FROM COMPANY";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String companyName = resultSet.getString("name");
                int id = resultSet.getInt("ID");
                companies.add(new Company(companyName, id));
                Company.currId = id;
            }
            return companies;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

}
