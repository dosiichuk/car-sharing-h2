package carsharing.data.entities;

public class Customer {
    private String name;
    private int id;
    private Integer carId;
    public static int currId = 0;

    public Customer(String name, int id, int carId) {
        this.name = name;
        this.id = id;
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Integer getCarId() {
        return carId;
    }
}
