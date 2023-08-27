package carsharing;

import carsharing.services.CompanyService;
import carsharing.services.MenuService;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = args.length < 2 ? "carsharing" : args[1];
        Scanner scanner = new Scanner(System.in);
        CompanyService companyService = new CompanyService(fileName, scanner);
        MenuService menuService = new MenuService(companyService, scanner);
        while(true) {
            menuService.takeTopMenuCommand();
        }
    }
}