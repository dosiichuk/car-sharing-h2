package carsharing.services;

import carsharing.data.dao.CompanyDao;

public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService(String fileName) {
        companyDao = new CompanyDao(fileName);
//        companyDao.dropCompanyTable();
        companyDao.createCompanyTable();
    }
}
