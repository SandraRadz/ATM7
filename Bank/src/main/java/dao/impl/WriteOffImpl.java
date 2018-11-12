package main.java.dao.impl;

import main.java.dao.WriteOffDao;
import main.java.entity.Card;
import main.java.entity.WriteOff;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WriteOffImpl implements WriteOffDao {
    private JdbcTemplate jdbcTemplate;
    private static final String GET = "SELECT * FROM WriteOff WHERE charge_id=?";

    @Override
    public WriteOff get(long id) {
        return null;
    }

    @Override
    public int insert(WriteOff user) {
        //return jdbcTemplate.update(INSERT, user.getLogin(), user.getPswd(), user.getAuthRole(), user.getEnabled(), user.getLogin());
        return 0;
    }

    @Override
    public void update(WriteOff user) {

    }

    private RowMapper<WriteOff> mapper = new RowMapper<WriteOff>() {
        public WriteOff mapRow(ResultSet rs, int rowNum) throws SQLException {
            WriteOff writeOff = new WriteOff();
            writeOff.setId(rs.getInt("id"));
            writeOff.setSum(rs.getDouble("sum"));
            Card cardFrom = new Card();
            cardFrom.setCardNumber(rs.getLong("number"));
            Card cardTo = new Card();
            cardTo.setCardNumber(rs.getLong("number"));
            return null;
        }
    };
}
