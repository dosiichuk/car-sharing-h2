package carsharing.services;

import carsharing.services.enums.ManagerMenu;
import carsharing.services.enums.TopMenu;

import java.util.Arrays;
import java.util.Scanner;

public class MenuService {

    private Scanner scanner;
    private CompanyService companyService;

    public MenuService(CompanyService companyService, Scanner scanner) {
        this.companyService = companyService;
        this.scanner = scanner;
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
                    case COMPANY_LIST -> companyService.showCompanyList();
                    case BACK -> takeTopMenuCommand();
                }
            }
        }
    }
}
