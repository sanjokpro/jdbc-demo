package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolingExample {
	private static final String jdbcUrl = "jdbc:mysql://localhost:3306/your_database_name";
	private static final String username = "your_username";
	private static final String password = "your_password";

	private static final int MAX_POOL_SIZE = 5;
	private static final List<Connection> connectionPool = new ArrayList<>();

	static {
		// Step 2: Initialize the connection pool during class initialization
		initializeConnectionPool();
	}

	private static void initializeConnectionPool() {
		try {
			// Step 3: Create and add connections to the connection pool
			for (int i = 0; i < MAX_POOL_SIZE; i++) {
				Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
				connectionPool.add(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Step 4: Borrow a connection from the pool
	public static Connection getConnectionFromPool() {
		if (connectionPool.isEmpty()) {
			System.err.println("Connection pool is empty. Unable to get a connection.");
			return null;
		}

		// Get the first connection from the pool and remove it from the list
		Connection connection = connectionPool.remove(0);
		return connection;
	}

	// Step 5: Release a connection back to the pool
	public static void releaseConnection(Connection connection) {
		if (connection != null) {
			try {
				// Check if the connection is not already closed
				if (!connection.isClosed()) {
					// Add the connection back to the pool for reuse
					connectionPool.add(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// Step 6: Borrow a connection from the pool
		try (Connection connection1 = getConnectionFromPool()) {
			System.out.println("Connection 1 Hash Code: " + connection1.hashCode());
			executeQuery(connection1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Borrow another connection from the pool
		try (Connection connection2 = getConnectionFromPool()) {
			System.out.println("Connection 2 Hash Code: " + connection2.hashCode());
			executeQuery(connection2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void executeQuery(Connection connection) throws SQLException {
		// Same executeQuery method as in the previous example
		// ...
	}

}
