package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnect {
	static final String url = "jdbc:mysql://localhost:3307/question_generator";
	static final String password = "sanjok";
	static final String user = "sanjok";

//	static Connection connection;

	public static void main(String[] args) {
		connect();
	}

	public static Connection connect() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);

			System.out.println("Database connected!");
			System.out.println("connection id is {}" + getCurrentConnectionId(connection));
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
		return connection;
	}

	public static int getCurrentConnectionId(Connection connection) {
		int connectionId = -1;
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT CONNECTION_ID()")) {
			if (resultSet.next()) {
				connectionId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connectionId;
	}
}
