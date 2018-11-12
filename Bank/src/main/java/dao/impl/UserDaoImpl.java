package main.java.dao.impl;

//DAO - DATA ACCESS OBJECT
import main.java.dao.UserDao;
import main.java.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private static final String GET = "SELECT * FROM User WHERE id=?";


    public User get(int id) {
        //logger.info("DAO: grabbing object Car from DB");
        //return jdbcTemplate.queryForObject(GET, mapper, id);
        return new User();
    }

    private RowMapper<User> mapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setIdentityNumber(rs.getInt("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            return user;
        }
    };
}
