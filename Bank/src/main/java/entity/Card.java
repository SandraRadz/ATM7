package main.java.entity;//import com.radzievska.domain.entity.Bank;

    public class Card {
    private static long cardNumber;
    private static int pin;
    private static double sum;
    private static String user;

   public Card(){
       cardNumber=0;
       pin=0;
       sum=0;
       user="";
   }

    public static int getPin() {
        return pin;
    }

    public static void setPin(int pin) {
        Card.pin = pin;
    }

    public static long getCardNumber() {
        return cardNumber;
    }

    public static void setCardNumber(long cardNumber) {
        Card.cardNumber = cardNumber;
    }

    public static double getSum() {
        return sum;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Card.user = user;
    }

    public static void setSum(double sum) {
        Card.sum = sum;
    }

}
