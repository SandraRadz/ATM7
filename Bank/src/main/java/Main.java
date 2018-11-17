import controller.Controller;
import service.impl.CardServiceImpl;

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
        controller.start();
    }
}