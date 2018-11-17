package service.impl;

import dao.impl.CardDaoImpl;
import dao.impl.WriteOffDaoImpl;
import entity.Card;
import service.CardService;

public class CardServiceImpl implements CardService {

    @Override
    public boolean isCardExist(String cardNum, String pin) {
        CardDaoImpl cd = new CardDaoImpl();
        boolean exist = cd.ifExists(cardNum, pin);
        cd.close();
        return exist;
    }

    @Override
    public double getSum(String cardNum, String pin) {
        CardDaoImpl cd = new CardDaoImpl();
        double sum = cd.getSum(cardNum);
        cd.close();
        return sum;
    }

    @Override
    public void getCash(String cardNum, String pin, double sum) {
        CardDaoImpl cd = new CardDaoImpl();
        if(cd.getSum(cardNum)>=sum) {
            cd.changeCash(cardNum, -sum);
            WriteOffDaoImpl wd = new WriteOffDaoImpl();
            wd.addWriteOff((wd.getCount()+1), sum, cardNum, null);
            wd.close();
        }
        cd.close();
    }

    @Override
    public void makeTransaction(String cardNumFrom, String pin, double sum, String cardNumTo) {
        CardDaoImpl cd = new CardDaoImpl();
        if(cd.getSum(cardNumFrom)>=sum) {
            cd.changeCash(cardNumFrom, -sum);
            cd.changeCash(cardNumTo, sum);
            WriteOffDaoImpl wd = new WriteOffDaoImpl();
            wd.addWriteOff((wd.getCount()+1), sum, cardNumFrom, cardNumTo);
            wd.close();
        }
        cd.close();
    }
}
