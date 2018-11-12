package main.java.entity;

import java.sql.Time;
import java.util.Date;

public class WriteOff {
    private static long id;
    private static double sum;
    private static Date date;
    private static Time time;
    private static Card cardSender;
    //private static ATM atm;
    private static Card cardReceiver;

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        WriteOff.id = id;
    }

    public static double getSum() {
        return sum;
    }

    public static void setSum(double sum) {
        WriteOff.sum = sum;
    }

    public static Date getDate() {
        return date;
    }

    public static void setDate(Date date) {
        WriteOff.date = date;
    }

    public static Time getTime() {
        return time;
    }

    public static void setTime(Time time) {
        WriteOff.time = time;
    }

    public static Card getCardSender() {
        return cardSender;
    }

    public static void setCardSender(Card cardSender) {
        WriteOff.cardSender = cardSender;
    }

   /* public static ATM getAtm() {
        return atm;
    }

    public static void setAtm(ATM atm) {
        entity.WriteOff.atm = atm;
    }
*/
    public static Card getCardReceiver() {
        return cardReceiver;
    }

    public static void setCardReceiver(Card cardReceiver) {
        WriteOff.cardReceiver = cardReceiver;
    }
}
