package dao.impl;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

import dao.CardDao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class CardDaoImpl implements CardDao {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/bankATM";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public CardDaoImpl(){
            // opening database connection to MySQL server
        try {
            con = DriverManager.getConnection(url, user, password);
          // getting Statement object to execute query
            stmt = con.createStatement();
        } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @Override
    public boolean ifExists(String cardNum, String pin) {
        boolean res=false;
        try {
            String query ="SELECT COUNT(*) FROM card AS exist WHERE number = '" + cardNum+"' AND pin = '"+ pin+"';";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
               res = (rs.getInt(1)==0?false:true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public double getSum(String cardNum, String pin) {
        double res=0;
        try {
            String query ="SELECT sum FROM card AS exist WHERE number = '" + cardNum+"' AND pin = '"+ pin+"';";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                res = (rs.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void takeCash(String cardNum, String pin, double sum) {
        double oldSum = getSum(cardNum, pin);
        double newsum=oldSum-sum;
        
    }

    @Override
    public void makeTransaction(String cardNumFrom, String pin, double sum, String cardNumTo) {

    }

        public void close() {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
    }

}
