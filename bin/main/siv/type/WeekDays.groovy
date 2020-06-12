package siv.type

public enum WeekDays {
    Sunday("Sunday"),
    Monday("Monday"),
    Tuesday("Tuesday"),
    Wednesday("Wednesday"),
    Thursday("Thursday"),
    Friday("Friday"),
    Saturday("Saturday");

    private final String guiName;
    WeekDays(String guiName) {
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