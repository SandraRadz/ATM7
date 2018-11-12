package main.java.dao.impl;

import main.java.dao.CardDao;
import main.java.entity.Card;

public class CardDaoImpl implements CardDao {

    @Override
    public Card get(long cardNum, int pin) {
        return null;
    }

    @Override
    public boolean ifExists(long catdId) {
        return false;
    }
}
