package carsharing.services.enums;

public enum ManagerMenu {
    COMPANY_LIST("1. Company list", 1),
    CREATE_COMPANY("2. Create a company", 2),
    BACK("0. Back", 0);

    private String itemText;
    private int itemIndex;

    ManagerMenu(String itemText, int itemIndex) {
        this.itemText = itemText;
        this.itemIndex = itemIndex;
    }

    public String getItemText() {
        return itemText;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public static ManagerMenu getCommandFromUserInput(int userInput) {
        for (ManagerMenu managerMenu: ManagerMenu.values()) {
            if (managerMenu.getItemIndex() == userInput) {
                return managerMenu;
            }
        }
        return null;
    }
}
