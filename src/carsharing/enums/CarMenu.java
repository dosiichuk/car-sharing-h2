package carsharing.enums;

import carsharing.services.CarService;

public enum CarMenu {
    CAR_LIST("1. Car list", 1),
    CREATE_CAR("2. Create a car", 2),
    BACK("0. Back", 0);

    private String itemText;
    private int itemIndex;

    CarMenu(String itemText, int itemIndex) {
        this.itemText = itemText;
        this.itemIndex = itemIndex;
    }

    public String getItemText() {
        return itemText;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public static CarMenu getCommandFromUserInput(int userInput) {
        for (CarMenu carMenu: CarMenu.values()) {
            if (carMenu.getItemIndex() == userInput) {
                return carMenu;
            }
        }
        return null;
    }
}
