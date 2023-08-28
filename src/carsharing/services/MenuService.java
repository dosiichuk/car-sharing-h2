package carsharing.services;

import carsharing.data.entities.Car;
import carsharing.data.entities.Company;
import carsharing.data.entities.Customer;
import carsharing.enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuService implements Runnable {

    private Scanner scanner;
    private CompanyService companyService;
    private CarService carService;
    private CustomerService customerService;

    public MenuService(CompanyService companyService, CarService carService, CustomerService customerService, Scanner scanner) {
        this.companyService = companyService;
        this.carService = carService;
        this.customerService = customerService;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while(true) {
            takeTopMenuCommand();
        }
    }

    public void showTopMenu() {
        Arrays.stream(TopMenu.values())
                .forEach(topMenu -> System.out.println(topMenu.getItemText()));
    }

    public void showManagerMenu() {
        Arrays.stream(ManagerMenu.values())
                .sequential()
                .forEach(managerMenu -> System.out.println(managerMenu.getItemText()));
    }

    public void showCompanyMenu(List<Company> companies) {
        if (companies.size() == 0) {
            System.out.println("The company list is empty!");
            return;
        } else {
            System.out.println("Choose the company:");
            companies.stream()
                    .forEach(company -> System.out.printf("%d. %s\n", company.getId(), company.getName()));
        }
        Arrays.stream(CompanyMenu.values())
                .sequential()
                .forEach(companyMenu -> System.out.println(companyMenu.getItemText()));
    }

    public void showCustomerMenu(List<Customer> customers) {
        if (customers.size() == 0) {
            System.out.println("The customer list is empty!");
            return;
        } else {
            System.out.println("Customer list:");
            customers.stream()
                    .forEach(customer -> System.out.printf("%d. %s\n", customer.getId(), customer.getName()));
        }
        Arrays.stream(CompanyMenu.values())
                .sequential()
                .forEach(companyMenu -> System.out.println(companyMenu.getItemText()));
    }

    public void showCarMenu() {
        Arrays.stream(CarMenu.values())
                .sequential()
                .forEach(carMenu -> System.out.println(carMenu.getItemText()));
    }

    public void showCarRentMenu() {
        Arrays.stream(CarRentMenu.values())
                .sequential()
                .forEach(carRentMenu -> System.out.println(carRentMenu.getItemText()));
    }

    public void takeTopMenuCommand() {
        while(true) {
            showTopMenu();
            String userInput = scanner.nextLine();
            TopMenu command = TopMenu.getCommandFromUserInput(Integer.parseInt(userInput));
            if (command != null) {
                switch(command) {
                    case LOG_IN_AS_MANAGER -> takeManagerMenuCommand();
                    case CREATE_CUSTOMER -> customerService.createCustomer();
                    case LOG_IN_AS_CUSTOMER -> takeCustomerMenuCommand();
                    case EXIT -> System.exit(0);
                }
            }
        }
    }

    public void takeManagerMenuCommand() {
        while(true) {
            showManagerMenu();
            String userInput = scanner.nextLine();
            ManagerMenu command = ManagerMenu.getCommandFromUserInput(Integer.parseInt(userInput));
            if (command != null) {
                switch(command) {
                    case CREATE_COMPANY -> companyService.createCompany();
                    case COMPANY_LIST -> takeCompanyMenuCommand();
                    case BACK -> takeTopMenuCommand();
                }
            }
        }
    }

    public void takeCompanyMenuCommand() {
        while (true) {
            List<Company> companies = companyService.getCompanyList();
            showCompanyMenu(companies);
            if (companies.size() == 0) {
                takeManagerMenuCommand();
                return;
            }
            String userInput = scanner.nextLine();
            int selectedOption = Integer.parseInt(userInput);
            CompanyMenu command = CompanyMenu.getCommandFromUserInput(selectedOption);
            if (command == null) {
                Company company = companyService.findCompanyById(selectedOption);
                if (company == null) {
                    takeManagerMenuCommand();
                } else {
                    takeCarMenuCommand(company);
                }
            } else {
                switch(command) {
                    case BACK -> takeManagerMenuCommand();
                }
            }

        }
    }

    public void takeCustomerMenuCommand() {
        while (true) {
            List<Customer> customers = customerService.getCustomerList();
            showCustomerMenu(customers);
            if (customers.size() == 0) {
                takeTopMenuCommand();
                return;
            }
            String userInput = scanner.nextLine();
            int selectedOption = Integer.parseInt(userInput);
            CompanyMenu command = CompanyMenu.getCommandFromUserInput(selectedOption);
            if (command == null) {
                Customer customer = customerService.findCustomerById(selectedOption);
                if (customer == null) {
                    takeManagerMenuCommand();
                } else {
                    takeCarRentCommand(customer);
                }
            } else {
                switch(command) {
                    case BACK -> takeManagerMenuCommand();
                }
            }

        }
    }

    public void takeCarMenuCommand(Company company) {
        System.out.println(String.format("'%s' company", company.getName()));
        while(true) {
            showCarMenu();
            String userInput = scanner.nextLine();
            CarMenu command = CarMenu.getCommandFromUserInput(Integer.parseInt(userInput));
            if (command != null) {
                switch(command) {
                    case CREATE_CAR -> carService.createCar(company);
                    case CAR_LIST -> carService.showCars(company);
                    case BACK -> takeManagerMenuCommand();
                }
            }
        }
    }

    public void takeCarRentCommand(Customer customer) {
        while(true) {
            showCarRentMenu();
            String userInput = scanner.nextLine();
            CarRentMenu command = CarRentMenu.getCommandFromUserInput(Integer.parseInt(userInput));
            if (command != null) {
                switch(command) {
                    case RENT_CAR -> takeCarRentChooseCompanyCommand(customer);
                    case RETURN_CAR -> {

                    }
                    case CAR_INFO -> {
                        Integer carId = customerService.getRentedCarId(customer);
                        Company company = companyService.findCompanyByCarId(carId);
                        carService.getRentedCarInfo(carId, company);
                    }
                    case BACK -> takeManagerMenuCommand();
                }
            }
        }
    }

    public void takeCarRentChooseCompanyCommand(Customer customer) {
        while (true) {
            List<Company> companies = companyService.getCompanyList();
            showCompanyMenu(companies);
            if (companies.size() == 0) {
                takeCarRentCommand(customer);
                return;
            }
            String userInput = scanner.nextLine();
            int selectedOption = Integer.parseInt(userInput);
            CompanyMenu command = CompanyMenu.getCommandFromUserInput(selectedOption);
            if (command == null) {
                Company company = companyService.findCompanyById(selectedOption);
                if (company == null) {
                    takeCarRentCommand(customer);
                } else {
                    takeCarRentChooseCarCommand(company, customer);
                }
            } else {
                switch(command) {
                    case BACK -> takeCarRentCommand(customer);
                }
            }

        }
    }

    public void takeCarRentChooseCarCommand(Company company, Customer customer) {
        System.out.println("Choose a car:");
        carService.showCars(company);
        showCompanyMenu(new ArrayList<>());
        while (true) {
            List<Car> cars = carService.getAllCars(company);
            if (cars.size() == 0) {
                takeCarRentChooseCompanyCommand(customer);
                return;
            }
            String userInput = scanner.nextLine();
            int selectedOption = Integer.parseInt(userInput);
            CompanyMenu command = CompanyMenu.getCommandFromUserInput(selectedOption);
            if (command == null) {
                Car car = carService.findCarById(selectedOption);
                if (car == null) {
                    takeCarRentChooseCompanyCommand(customer);
                } else {
                    customerService.rentCar(car, customer);
                }
            } else {
                switch(command) {
                    case BACK -> takeCarRentChooseCompanyCommand(customer);
                }
            }

        }
    }



}
