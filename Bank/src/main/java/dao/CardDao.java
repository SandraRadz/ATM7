package dao;

import entity.Card;

public interface CardDao {
    boolean ifExists (String cardNum, String pin);
    double getSum(String cardNum);
    void changeCash (String cardNum, double sum);
}
