import controller.Controller;
import service.impl.CardServiceImpl;

import java.util.ArrayList;

public class Main {

    public static void main(String[] ar) {
        int port = 6666;
        Controller controller = new Controller(port);
       // controller.getCash("2222222222222222", "2222", "132222112");
        controller.start();

    }
}