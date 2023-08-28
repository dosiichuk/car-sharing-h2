package carsharing.enums;

public enum CompanyMenu {
    BACK("0. Back", 0);

    private String itemText;
    private int itemIndex;

    CompanyMenu(String itemText, int itemIndex) {
        this.itemText = itemText;
        this.itemIndex = itemIndex;
    }

    public String getItemText() {
        return itemText;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public static CompanyMenu getCommandFromUserInput(int userInput) {
        for (CompanyMenu companyMenu: CompanyMenu.values()) {
            if (companyMenu.getItemIndex() == userInput) {
                return companyMenu;
            }
        }
        return null;
    }
}
