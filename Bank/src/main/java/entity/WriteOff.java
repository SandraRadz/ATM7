package entity;

import java.sql.Time;
import java.util.Date;
import entity.Card;

public class WriteOff {
    private long id;
    private double sum;
    private Card cardSender;
    private Card cardReceiver;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public  Card getCardSender() {
        return cardSender;
    }

    public  void setCardSender(Card cardSender) {
        this.cardSender = cardSender;
    }



    public  Card getCardReceiver() {
        return cardReceiver;
    }

    public  void setCardReceiver(Card cardReceiver) {
        this.cardReceiver = cardReceiver;
    }
}
