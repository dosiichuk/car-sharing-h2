package carsharing.data.dao;

import carsharing.data.ConnectionFactory;
import carsharing.data.entities.Company;
import carsharing.data.entities.Customer;
import carsharing.data.interfaces.ICustomerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements ICustomerDao {
    private ConnectionFactory factory;

    public CustomerDao(ConnectionFactory connectionFactory) {
        this.factory = connectionFactory;
    }


    public void dropCustomerTable() {
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql =  "DROP TABLE IF EXISTS CUSTOMER";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void createCustomerTable() {
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql =  "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    "(ID INT AUTO_INCREMENT, " +
                    " name VARCHAR(255) UNIQUE NOT NULL," +
                    " RENTED_CAR_ID INT DEFAULT NULL," +
                    " PRIMARY KEY (ID)," +
                    " FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                    " ON DELETE CASCADE);";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void addCustomer(String customerName, int id) {
        try (
                Connection connection = this.factory.getConn();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO CUSTOMER (name, ID) VALUES (?, ?);");
        ) {
            stmt.setString(1, customerName);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql = "SELECT * FROM CUSTOMER";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                int id = resultSet.getInt("ID");
                Optional<Integer> rentedCarIdOpt = Optional.ofNullable(resultSet.getInt("rented_car_id"));
                customers.add(new Customer(customerName, id, rentedCarIdOpt.orElse(null)));
                Company.currId = id;
            }
            return customers;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public Customer findOneById(int customerId) {
        Customer customer = null;
        try (
                Connection connection = this.factory.getConn();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ?");
        ) {
            stmt.setInt(1, customerId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                int id = resultSet.getInt("ID");
                Optional<Integer> rentedCarIdOpt = Optional.ofNullable(resultSet.getInt("rented_car_id"));
                customer = new Customer(customerName, id, rentedCarIdOpt.orElse(null));
                Customer.currId = id;
            }
            return customer;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void updateRentedCarId(int rentedCarId, int customerId) {
        try (
                Connection connection = this.factory.getConn();
                PreparedStatement stmt = connection.prepareStatement("UPDATE CAR SET RENTED_CAR_ID = ? WHERE ID = ?;");
        ) {
            stmt.setInt(1, rentedCarId);
            stmt.setInt(2, customerId);
            stmt.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

}
