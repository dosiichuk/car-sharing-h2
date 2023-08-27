package carsharing.services.enums;

public enum TopMenu {
    LOG_IN_AS_MANAGER("1. Log in as a manager", 1),
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
