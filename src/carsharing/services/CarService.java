package carsharing.services;

import carsharing.data.ConnectionFactory;
import carsharing.data.dao.CarDao;
import carsharing.data.dao.CompanyDao;
import carsharing.data.entities.Car;
import carsharing.data.entities.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarService {

    private final CarDao carDao;
    private Scanner scanner;

    public CarService(ConnectionFactory connectionFactory, Scanner scanner) {
        carDao = new CarDao(connectionFactory);
        this.scanner = scanner;
        carDao.dropCarTable();
        carDao.createCarTable();
    }

    public void createCar(Company company) {
        System.out.println("Enter the car name:");
        String userInput = scanner.nextLine();
        try {
            Car.currId += 1;
            carDao.addCar(userInput, Car.currId, company.getId());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("The car was added!");
    }

    public void showCars(Company company) {
        List<Car> cars = carDao.findByCompanyId(company.getId());
        if (cars.size() == 0) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            int i = 1;
            for (Car car: cars) {
                System.out.printf("%d. %s\n", i, car.getName());
                i++;
            }
        }
    }
}
