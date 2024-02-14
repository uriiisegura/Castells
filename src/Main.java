import business.BusinessFacade;
import presentation.Controller;
import presentation.console.ConsoleController;
import presentation.swing.SwingController;

public class Main {
    private static final BusinessFacade businessFacade = new BusinessFacade();
    private static final Controller consoleController = new ConsoleController(businessFacade);
    private static final Controller swingController = new SwingController(businessFacade);

    public static void main(String[] args)  {
        swingController.start();
    }
}
