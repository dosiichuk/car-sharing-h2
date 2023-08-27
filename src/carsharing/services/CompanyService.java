package carsharing.services;

import carsharing.data.dao.CompanyDao;
import carsharing.data.entities.Company;

import java.util.List;
import java.util.Scanner;

public class CompanyService {
    private final CompanyDao companyDao;
    private Scanner scanner;

    public CompanyService(String fileName, Scanner scanner) {
        companyDao = new CompanyDao(fileName);
        this.scanner = scanner;
        companyDao.dropCompanyTable();
        companyDao.createCompanyTable();
    }

    public void createCompany() {
        System.out.println("Enter the company name:");
        String userInput = scanner.nextLine();
        try {
            Company.currId += 1;
            companyDao.addCompany(userInput, Company.currId);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        System.out.println("The company was created!");
    }

    public void showCompanyList() {
        try {
            List<Company> companies = companyDao.findAll();
            if (companies.size() == 0) {
                System.out.println("The company list is empty!");
            } else {
                companies.stream()
                        .forEach(company -> System.out.printf("%d. %s\n", company.getId(), company.getName()));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
