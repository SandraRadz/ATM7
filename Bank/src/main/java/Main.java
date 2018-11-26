import controller.Controller;
import service.impl.CardServiceImpl;

import java.util.ArrayList;

public class Main {

    static Controller controller;

    public static void main(String[] ar) {
        int port = 6666;
        controller = new Controller(port);
        Thread myThready = new Thread(controller);	//Создание потока "myThready"
        myThready.start();

    }
}