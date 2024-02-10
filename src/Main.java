import business.BusinessFacade;
import presentation.UiController;

public class Main {
    private static final BusinessFacade businessFacade = new BusinessFacade();
    private static final UiController uiController = new UiController(businessFacade);

    public static void main(String[] args)  {
        uiController.start();
    }
}
