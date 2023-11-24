package enums;

public enum PisosT {
    TRES,
    QUATRE,
    CINC,
    SIS,
    SET,
    VUIT,
    NOU,
    DEU;

    public int toInt() {
        return ordinal() + 3;
    }
}
