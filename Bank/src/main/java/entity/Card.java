package entity;//import com.radzievska.domain.entity.Bank;

    public class Card {
    private long cardNumber;
    private int pin;
    private double sum;
    private String user;

   public Card(){
       cardNumber=0;
       pin=0;
       sum=0;
       user="";
   }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getSum() {
        return sum;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void printInfo(){
       System.out.println("card num: "+cardNumber);
        System.out.println("card pin: "+pin);
        System.out.println("card owner: "+user);
        System.out.println("card sum: "+sum);

    }

}
