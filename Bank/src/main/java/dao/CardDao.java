package dao;

import entity.Card;

public interface CardDao {
    Card get(long cardNum, int pin);
    boolean ifExists(long catdId, int pin);
}
