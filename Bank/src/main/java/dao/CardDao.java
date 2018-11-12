package main.java.dao;

import main.java.entity.Card;

public interface CardDao {
    Card get(long cardNum, int pin);
    boolean ifExists(long catdId);
}
