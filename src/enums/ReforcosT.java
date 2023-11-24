package enums;

public enum ReforcosT {
    CAP,
    F,
    SF,
    FM,
    SM,
    FMP;

    public String string() {
        if (this.equals(CAP)) return "";
        return toString().toLowerCase();
    }
}
