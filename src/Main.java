import business.BusinessFacade;
import presentation.Controller;
import presentation.console.ConsoleController;
import presentation.swing.SwingController;

public class Main {
    private static final BusinessFacade businessFacade = new BusinessFacade();
    private static final Controller controller = new ConsoleController(businessFacade);

    public static void main(String[] args)  {
        controller.start();
    }
}
