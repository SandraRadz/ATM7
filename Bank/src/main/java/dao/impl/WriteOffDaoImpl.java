package dao.impl;

import dao.WriteOffDao;

import java.sql.*;

public class WriteOffDaoImpl implements WriteOffDao {

    private static final String url = "jdbc:mysql://localhost:3306/bankATM";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;

    public WriteOffDaoImpl(){
        // opening database connection to MySQL server
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        int res=0;
        String query = "SELECT COUNT(*) FROM WriteOff AS num;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void addWriteOff(int id, double sum, String cardFrom, String cardTo) {
        try (Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO writeoff (charge_id, sum, card_from_id, card_to_id) Values (?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, sum);
            preparedStatement.setString(3, cardFrom);
            preparedStatement.setString(4, cardTo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
    }
}
