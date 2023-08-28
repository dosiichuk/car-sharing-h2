package carsharing.data.interfaces;

import carsharing.data.entities.Car;

import java.util.List;

public interface ICarDao {
    void createCarTable();
    void dropCarTable();
    List<Car> findAll();
    List<Car> findByCompanyId(int companyId);
    void addCar(String carName, int id, int companyId);

}
