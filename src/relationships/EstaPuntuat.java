package relationships;

import config.Config;
import models.Castell;
import models.CastellUniversitari;

import java.time.LocalDate;

public class EstaPuntuat extends Periode {
    private final Castell castell;
    private final int grup;
    private final int subgrup;
    private final int carregat;
    private final int descarregat;

    public EstaPuntuat(Castell castell, LocalDate desDe, LocalDate finsA, int grup, int subgrup, int carregat, int descarregat) {
        super(desDe, finsA);
        this.castell = castell;
        this.grup = grup;
        this.subgrup = subgrup;
        this.carregat = carregat;
        this.descarregat = descarregat;
    }

    public boolean isUniversitari() {
        return castell instanceof CastellUniversitari;
    }

    @Override
    public String toString() {
        return String.format("(%s / %s) %d.%d %s : %d | %d", Config.parseDate(getDesDe()), Config.parseDate(getFinsA()),
                grup, subgrup, castell.toString(), carregat, descarregat);
    }
}
