package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {
	private static final String jdbcUrl = "jdbc:mysql://localhost:3306/your_database_name";
	private static final String username = "your_username";
	private static final String password = "your_password";

	public static void main(String[] args) {
		TransactionExample example = new TransactionExample();
		example.updateCourseDetailsInTransaction();
	}

	public void updateCourseDetailsInTransaction() {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			connection.setAutoCommit(false); // Disable auto-commit for manual transaction handling

			try {
				// Perform multiple operations within the transaction
				updateCourseDetails(connection, 1, "4 years", "Computer Science (Updated)");
				updateCourseDetails(connection, 2, "3 years", "Information Technology (Updated)");

				// Commit the transaction if all operations are successful
				connection.commit();
				System.out.println("Transaction committed successfully.");
			} catch (SQLException e) {
				// Rollback the transaction if any operation fails
				connection.rollback();
				System.out.println("Transaction rolled back.");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateCourseDetails(Connection connection, int courseId, String newDuration, String newDescription)
			throws SQLException {
		String updateQuery = "UPDATE course SET duration = ?, description = ? WHERE course_id = ?";
		try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
			updateStatement.setString(1, newDuration);
			updateStatement.setString(2, newDescription);
			updateStatement.setInt(3, courseId);
			updateStatement.executeUpdate();
		}
	}

}
