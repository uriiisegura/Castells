import business.BusinessFacade;
import presentation.UiController;
import presentation.console.ConsoleUiManager;

public class Main {
    private static final BusinessFacade businessFacade = new BusinessFacade();
    private static final UiController uiController = new UiController(businessFacade, new ConsoleUiManager());

    public static void main(String[] args)  {
        uiController.start();
    }
}
