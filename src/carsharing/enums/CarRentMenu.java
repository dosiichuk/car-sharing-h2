package carsharing.enums;

public enum CarRentMenu {
    RENT_CAR("1. Rent a car", 1),
    RETURN_CAR("2. Return a rented car", 2),
    CAR_INFO("3. My rented car", 3),
    BACK("0. Back", 0);

    private String itemText;
    private int itemIndex;

    CarRentMenu(String itemText, int itemIndex) {
        this.itemText = itemText;
        this.itemIndex = itemIndex;
    }

    public String getItemText() {
        return itemText;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public static CarRentMenu getCommandFromUserInput(int userInput) {
        for (CarRentMenu carRentMenu: CarRentMenu.values()) {
            if (carRentMenu.getItemIndex() == userInput) {
                return carRentMenu;
            }
        }
        return null;
    }
}
