package carsharing.services;

import carsharing.data.entities.Company;
import carsharing.enums.CarMenu;
import carsharing.enums.CompanyMenu;
import carsharing.enums.ManagerMenu;
import carsharing.enums.TopMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuService implements Runnable {

    private Scanner scanner;
    private CompanyService companyService;
    private CarService carService;

    public MenuService(CompanyService companyService, CarService carService, Scanner scanner) {
        this.companyService = companyService;
        this.carService = carService;
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

    public void showCarMenu() {
        Arrays.stream(CarMenu.values())
                .sequential()
                .forEach(carMenu -> System.out.println(carMenu.getItemText()));
    }

    public void takeTopMenuCommand() {
        while(true) {
            showTopMenu();
            String userInput = scanner.nextLine();
            TopMenu command = TopMenu.getCommandFromUserInput(Integer.parseInt(userInput));
            if (command != null) {
                switch(command) {
                    case LOG_IN_AS_MANAGER -> takeManagerMenuCommand();
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



}
