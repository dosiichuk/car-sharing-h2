package carsharing;

import carsharing.data.ConnectionFactory;
import carsharing.services.CarService;
import carsharing.services.CompanyService;
import carsharing.services.CustomerService;
import carsharing.services.MenuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = args.length < 2 ? "carsharing" : args[1];
        Scanner scanner = new Scanner(System.in);
        ConnectionFactory connectionFactory = new ConnectionFactory(fileName);
        CompanyService companyService = new CompanyService(connectionFactory, scanner);
        CarService carService = new CarService(connectionFactory, scanner);
        CustomerService customerService = new CustomerService(connectionFactory, scanner);
        MenuService menuService = new MenuService(companyService, carService, customerService, scanner);
        menuService.run();
    }
}