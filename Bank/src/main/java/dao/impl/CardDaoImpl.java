package main.java.dao.impl;

import main.java.dao.CardDao;
import main.java.entity.Card;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDaoImpl implements CardDao {
    private JdbcTemplate jdbcTemplate;
    private static final String GET = "SELECT * FROM Card WHERE cardNumber=? AND pin=?";


    @Override
    public Card get(long cardNum, int pin) {
        //return jdbcTemplate.queryForObject(GET, mapper, cardNum, pin);
       return null;
    }

    @Override
    public boolean ifExists(long cardNum, int pin) {

        return false;
    }

    private RowMapper<Card> mapper = new RowMapper<Card>() {
        public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
            Card card = new Card();
            card.setCardNumber(rs.getLong("number"));
            card.setPin(rs.getInt("pin"));
            card.setSum(rs.getDouble("sum"));
            card.setUser(rs.getString("user"));
            return null;
        }
    };
}
