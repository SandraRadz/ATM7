package dao.impl;

import dao.WriteOffDao;
import entity.Card;
import entity.WriteOff;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WriteOffImpl implements WriteOffDao {
    private JdbcTemplate jdbcTemplate;
    private static final String GET = "SELECT * FROM WriteOff WHERE charge_id=?";
    private static final String INSERT = "SELECT * FROM WriteOff WHERE charge_id=?";

    @Override
    public WriteOff get(long id) {
        return null;
    }

    @Override
    public int insert(WriteOff writeOff) {
        return jdbcTemplate.update(INSERT, writeOff.getId(), writeOff.getSum(), writeOff.getCardSender(), writeOff.getCardReceiver());
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
            cardFrom.setCardNumber(rs.getLong("numberFrom"));
            cardFrom.setPin(rs.getInt("pinFrom"));
            cardFrom.setSum(rs.getDouble("sumFrom"));
            cardFrom.setUser(rs.getString("userFrom"));
            Card cardTo = new Card();
            cardTo.setCardNumber(rs.getLong("numberTo"));
            cardTo.setCardNumber(rs.getLong("numberTo"));
            cardTo.setPin(rs.getInt("pinTo"));
            cardTo.setSum(rs.getDouble("sumTo"));
            cardTo.setUser(rs.getString("userTo"));
            return null;
        }
    };
}
