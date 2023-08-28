package carsharing.services;

import carsharing.data.ConnectionFactory;
import carsharing.data.dao.CustomerDao;
import carsharing.data.entities.Car;
import carsharing.data.entities.Company;
import carsharing.data.entities.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService {

    private Scanner scanner;
    private final CustomerDao customerDao;

    public CustomerService(ConnectionFactory connectionFactory, Scanner scanner) {
        customerDao = new CustomerDao(connectionFactory);
        this.scanner = scanner;
        customerDao.dropCustomerTable();
        customerDao.createCustomerTable();
    }

    public void createCustomer() {
        System.out.println("Enter the customer name:");
        String userInput = scanner.nextLine();
        try {
            Customer.currId += 1;
            customerDao.addCustomer(userInput, Customer.currId);
            System.out.println("The customer was added!");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomerList() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerDao.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer findCustomerById(int customerId) {
        Customer customer = null;
        try {
            customer = customerDao.findOneById(customerId);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void rentCar(Car car, Customer customer) {
        try {
            customerDao.updateRentedCarId(car.getId(), customer.getId());
            System.out.println(String.format("You rented '%s'", car.getName()));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public Integer getRentedCarId(Customer customer) {
        Customer dbCustomer = null;
        try {
            dbCustomer = customerDao.findOneById(customer.getId());
            System.out.println("ID" + dbCustomer.getCarId());
            if (dbCustomer.getCarId() == 0) {
                System.out.println("You didn't rent a car!");
                return null;
            } else {
                return dbCustomer.getCarId();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
