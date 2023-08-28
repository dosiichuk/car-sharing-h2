package carsharing.services;

import carsharing.data.ConnectionFactory;
import carsharing.data.dao.CompanyDao;
import carsharing.data.entities.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyService {
    private final CompanyDao companyDao;
    private Scanner scanner;

    public CompanyService(ConnectionFactory connectionFactory, Scanner scanner) {
        companyDao = new CompanyDao(connectionFactory);
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

    public List<Company> getCompanyList() {
        List<Company> companies = new ArrayList<>();
        try {
            companies = companyDao.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public Company findCompanyById(int companyId) {
        Company company = null;
        try {
            company = companyDao.findOneById(companyId);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return company;
    }
}
