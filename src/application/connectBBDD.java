package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class connectBBDD {
    public static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:bbdd/bbdd.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }/*
	public int selectLogin(String login, String passwd) {
		int id = -1;
		String type;
		String sql = "SELECT id, type FROM users where username ='" + login + "' and password ='" + passwd + "';";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                id = rs.getInt("id");
                type = rs.getString("type");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
	}
		*/
	}  