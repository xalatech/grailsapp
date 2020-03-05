package siv.type;

public enum Maalform {
    B("Bokmål"), N("Nynorsk");

    private final String guiName;


    Maalform(String guiName) {
        this.guiName = guiName;
    }


    public String getGuiName() {
        return guiName;
    }


    public String toString() {
        return guiName;
    }


    String getKey() {
        return name();
    }
}
