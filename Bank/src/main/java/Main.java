import controller.Controller;
import service.impl.CardServiceImpl;

import java.util.ArrayList;

public class Main {

    public static void main(String[] ar) {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        /*CardServiceImpl cs = new CardServiceImpl();
        //cs.getCash("2222222222222223", "7777", -2000);
        System.out.println(cs.isCardExist("2222222222222223","7777"));
        System.out.println(cs.getSum("2222222222222223","7777"));
        cs.getCash("2222222222222223", "7777", 123);
        cs.makeTransaction("2222222222222222", "7777", 300, "2222222222222223");
        System.out.println("==================");
        System.out.println(cs.getSum("2222222222222223","7777"));
        System.out.println(cs.getSum("2222222222222222","7878"));
        boolean e= true;
        System.out.println(String.valueOf(e));
*/
        Controller controller = new Controller(port);
       // ArrayList<String> arr = new ArrayList<>();
        //arr.add("0");
       // arr.add("2222222222222222");
       // arr.add("5a1169e8477b61d184da62aeebae067002503a6c54e1b3d35a6fdb504e7dc0a31b8108b95a1fefbb86f5c0276a53126a83d82605c77a875e0e596e90a0916e9c");
       // System.out.println(controller.doQuery(arr));
        controller.start();

    }
}