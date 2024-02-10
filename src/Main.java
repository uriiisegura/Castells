import business.BusinessFacade;
import presentation.UiManager;
import presentation.console.ConsoleUiManager;

public class Main {
    private static final BusinessFacade businessFacade = new BusinessFacade();
    private static final UiManager uiManager = new ConsoleUiManager();

    public static void main(String[] args)  {
        businessFacade.loadAll();
        uiManager.showDiades(businessFacade.getDiades());
    }
}
