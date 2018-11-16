package service;

public interface CardService {
    String isCardExist(String cardNum, String pin);
    String getSum (String cardNum, String pin);
    String getCash (String cardNum, String pin, double sum);
    String makeTransaction (String cardNumFrom, String pin, double sum, String cardNumTo);
}
