import business.BusinessFacade;
import presentation.Controller;
import presentation.console.ConsoleController;

public class Main {
    private static final BusinessFacade businessFacade = new BusinessFacade();
    private static final Controller consoleController = new ConsoleController(businessFacade);

    public static void main(String[] args)  {
        consoleController.start();
    }
}
