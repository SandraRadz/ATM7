package service.impl;

import dao.impl.CardDaoImpl;
import entity.Card;
import service.CardService;

public class CardServiceImpl implements CardService {

    @Override
    public String isCardExist(String cardNum, String pin) {
        CardDaoImpl cd = new CardDaoImpl();
        boolean exist = cd.ifExists(cardNum, pin);
        cd.close();
        return (exist==true?"TRUE":"FALSE");
    }

    @Override
    public String getSum(String cardNum, String pin) {
        CardDaoImpl cd = new CardDaoImpl();
        double sum = cd.getSum(cardNum, pin);
        cd.close();
        return ("Sum "+sum);
    }

    @Override
    public String getCash(String cardNum, String pin, double sum) {
        return null;
    }

    @Override
    public String makeTransaction(String cardNumFrom, String pin, double sum, String cardNumTo) {
        return null;
    }
}
