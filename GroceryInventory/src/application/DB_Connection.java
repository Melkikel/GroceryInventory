package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DB_Connection {
	public static Connection connection = null;

	public static void open() {
		try {

			Dotenv dotenv = Dotenv.configure().load();
			connection = DriverManager.getConnection(dotenv.get("DB_HOST") + dotenv.get("DB_NAME"),
					dotenv.get("DB_USER"), dotenv.get("DB_PASSWORD"));
			System.out.println("connected");
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	public static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

}
