package carsharing.data.entities;

public class Company {

    private String name;
    private int id;
    public static int currId = 0;

    public Company(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
