package dao;

import entity.Card;

public interface CardDao {
    boolean ifExists (String cardNum, String pin);
    double getSum(String cardNum, String pin);
    void takeCash (String cardNum, String pin, double sum);
    void makeTransaction (String cardNumFrom, String pin, double sum, String cardNumTo);
}
