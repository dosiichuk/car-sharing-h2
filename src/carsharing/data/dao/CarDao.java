package carsharing.data.dao;

import carsharing.data.ConnectionFactory;
import carsharing.data.entities.Car;
import carsharing.data.entities.Company;
import carsharing.data.interfaces.ICarDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao implements ICarDao {
    private ConnectionFactory factory;

    public CarDao(ConnectionFactory connectionFactory) {
        this.factory = connectionFactory;
    }

    public void dropCarTable() {
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql =  "DROP TABLE IF EXISTS CAR";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void createCarTable() {
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql =  "CREATE TABLE IF NOT EXISTS CAR " +
                    "(ID INT AUTO_INCREMENT, " +
                    " name VARCHAR(255) UNIQUE NOT NULL," +
                    " COMPANY_ID INT NOT NULL," +
                    " PRIMARY KEY (ID)," +
                    " FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                    " ON DELETE CASCADE);";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try (
                Connection connection = this.factory.getConn();
                Statement stmt = connection.createStatement();
        ) {
            String sql = "SELECT * FROM CAR";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String carName = resultSet.getString("name");
                int id = resultSet.getInt("ID");
                int companyId = resultSet.getInt("COMPANY_ID");
                cars.add(new Car(id, carName, companyId));
                Car.currId = id;
            }
            return cars;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public List<Car> findByCompanyId(int companyId) {
        List<Car> cars = new ArrayList<>();
        try (
                Connection connection = this.factory.getConn();
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CAR WHERE COMPANY_ID = ?");
        ) {
            stmt.setInt(1, companyId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String carName = resultSet.getString("name");
                int id = resultSet.getInt("ID");
                cars.add(new Car(id, carName, companyId));
                Car.currId = id;
            }
            return cars;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void addCar(String carName, int id, int companyId) {
        try (
                Connection connection = this.factory.getConn();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO CAR (name, ID, COMPANY_ID) VALUES (?, ?, ?);");
        ) {
            stmt.setString(1, carName);
            stmt.setInt(2, id);
            stmt.setInt(3, companyId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

}
