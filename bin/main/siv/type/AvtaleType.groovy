package siv.type

enum AvtaleType {
    INGEN("Ingen"),
    HARD("Hard"),
    LOS("Løs"),
    HAR_HATT_HARD("Har hatt hard"),
    HAR_HATT_LOS("Har hatt løs")

    private final String guiName;

    AvtaleType(String guiName) {
        this.guiName = guiName;
    }

    String getGuiName() {
        return guiName;
    }

    String toString() {
        return guiName;
    }

    String getKey() {
        return name();
    }
}