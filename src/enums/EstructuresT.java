package enums;

public enum EstructuresT {
    PILAR,
    DOS,
    TRES,
    QUATRE,
    CINC,
    SET,
    NOU,
    DEU,
    DOTZE;

    public String string() {
        if (this.equals(PILAR)) return "P";
        if (this.equals(DOS)) return "2";
        if (this.equals(TRES)) return "3";
        if (this.equals(QUATRE)) return "4";
        if (this.equals(CINC)) return "5";
        if (this.equals(SET)) return "7";
        if (this.equals(NOU)) return "9";
        if (this.equals(DEU)) return "10";
        return "12";
    }
}
