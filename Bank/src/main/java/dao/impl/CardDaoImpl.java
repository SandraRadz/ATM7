package dao.impl;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

import dao.CardDao;

import java.sql.*;

public class CardDaoImpl implements CardDao {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/bankATM";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;

    public CardDaoImpl(){
            // opening database connection to MySQL server
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @Override
    public boolean ifExists(String cardNum, String pin) {
        boolean res=false;
        String query ="SELECT COUNT(*) FROM card AS exist WHERE number = ? AND pin = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)){
            preparedStatement.setString(1, cardNum);
            preparedStatement.setString(2, pin);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
               res = (rs.getInt(1)==0?false:true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public double getSum(String cardNum) {
        double res=0;
        String query ="SELECT sum FROM card WHERE number = ?;";
        try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, cardNum);
            //preparedStatement.setString(2, pin);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                res = (rs.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void changeCash(String cardNum, double sum) {
        double oldSum = getSum(cardNum);
        double newsum=oldSum+sum;
        String query ="UPDATE card set sum = ? WHERE number = ?;";
        try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setDouble(1, newsum);
            preparedStatement.setString(2, cardNum);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void close() {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
    }

}
