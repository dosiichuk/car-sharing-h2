package carsharing.data.entities;

public class Car {
    private int id;
    private String name;
    private int companyId;
    public static int currId = 0;

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }
}
