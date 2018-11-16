import controller.Controller;
import service.impl.CardServiceImpl;

public class Main {

    public static void main(String[] ar) {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        CardServiceImpl cs = new CardServiceImpl();
        System.out.println(cs.isCardExist("2222222222222223","7777"));
        System.out.println(cs.getSum("2222222222222223","7777"));
        Controller contr = new Controller(port);

    }
}
