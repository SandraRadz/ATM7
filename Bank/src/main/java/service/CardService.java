package service;

public interface CardService {
    boolean isCardExist(String cardNum, String pin);
    double getSum (String cardNum, String pin);
    void getCash (String cardNum, String pin, double sum);
    void makeTransaction (String cardNumFrom, String pin, double sum, String cardNumTo);
}
