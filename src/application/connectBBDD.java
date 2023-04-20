package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connectBBDD {
	public static Connection connect() {
        // MariaDB connection string
        String url = "jdbc:mariadb://195.235.211.197:3306/prifridgedeck";
        String username = "pri_fridgedeck";
        String password = "fridgedeck1";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
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