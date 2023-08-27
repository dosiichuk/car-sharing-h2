package carsharing.data.dao.interfaces;

public interface ICompanyDao {
    void createCompanyTable();
    void dropCompanyTable();
    void addCompany(String companyName, int id);
}
