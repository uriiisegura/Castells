package enums;

public enum ResultatsT {
    INTENT,
    INTENT_DESMUNTAT,
    CARREGAT,
    DESCARREGAT;

    public String text() {
        return this.toString().replace("_", " ").toLowerCase();
    }
}
