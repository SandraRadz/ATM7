//import com.radzievska.domain.Bank;

public class Card {
    private static long cardNumber;
    private static int pin;
    private static double sum;
    private static Bank bank;
    private static User user;

    public static int getPin() {
        return pin;
    }

    public static void setPin(int pin) {
        Card.pin = pin;
    }

    public static long getCardNumber() {
        return cardNumber;
    }

    //  public static void setCardNumber(long cardNumber) {
    //      Card.cardNumber = cardNumber;
    //  }

    public static double getSum() {
        return sum;
    }

    public static Bank getBank() {
        return bank;
    }

    public static void setBank(Bank bank) {
        Card.bank = bank;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Card.user = user;
    }

    public static void setSum(double sum) {
        Card.sum = sum;
    }

}
