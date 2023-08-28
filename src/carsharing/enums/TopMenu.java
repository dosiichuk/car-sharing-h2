package carsharing.enums;

public enum TopMenu {
    LOG_IN_AS_MANAGER("1. Log in as a manager", 1),
    LOG_IN_AS_CUSTOMER("2. Log in as a customer", 2),
    CREATE_CUSTOMER("3. Create a customer", 3),
    EXIT("0. Exit", 0);

    private String itemText;
    private int itemIndex;

    TopMenu(String itemText, int itemIndex) {
        this.itemText = itemText;
        this.itemIndex = itemIndex;
    }

    public String getItemText() {
        return itemText;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public static TopMenu getCommandFromUserInput(int userInput) {
        for (TopMenu topMenu: TopMenu.values()) {
            if (topMenu.getItemIndex() == userInput) {
                return topMenu;
            }
        }
        return null;
    }
}
