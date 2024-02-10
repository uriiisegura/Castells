package presentation.console;

import models.castellers.Casteller;
import models.colles.Colla;
import models.diades.CastellLineUp;
import models.diades.Diada;
import models.diades.RenglaLineUp;
import presentation.UiManager;
import relationships.CastellDiada;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ConsoleUiManager implements UiManager {
	@Override
	public void start() {}

	@Override
	public void showDiades(List<Diada> diades) {
		for (Diada diada : diades) {
			System.out.printf("- %s\n", diada.getNom());
			for (Map.Entry<Colla, Vector<CastellDiada>> hm : diada.getCastellsDictionary().entrySet()) {
				System.out.printf("\t- %s\n", hm.getKey().getCurrentNom());
				for (CastellDiada cd : hm.getValue()) {
					System.out.printf("\t\t- %s (%s)\n", cd.getNotacio(), cd.getResultat().text());
					for (CastellLineUp clu : cd.getLineUps()) {
						if (clu instanceof RenglaLineUp rlu) {
							System.out.printf("\t\t\t- %s\n", rlu.getRenglaNom());
							for (Casteller c : rlu.getCastellers())
								System.out.printf("\t\t\t\t- %s\n", c.getMalnomAtCollaAtTime(cd.getColla(), LocalDate.from(diada.getInici())));
						}
					}
				}
			}
		}
	}
}
