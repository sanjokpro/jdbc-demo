package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Enhanced SQL exception handling involves capturing and handling specific SQL
 * exceptions more effectively, providing better feedback to users and improving
 * error reporting. Below is an example of how to enhance SQL exception handling
 * in JDBC code:
 */
public class SQLExceptionHandlingExample {
	public static void main(String[] args) {
		SQLExceptionHandlingExample example = new SQLExceptionHandlingExample();
		example.readCourseDetails(100); // Replace 100 with the actual course_id to be read.
	}

	public void readCourseDetails(int courseId) {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			String selectQuery = "SELECT * FROM course WHERE course_id = ?";
			try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
				selectStatement.setInt(1, courseId);
				ResultSet resultSet = selectStatement.executeQuery();

				if (resultSet.next()) {
					String courseName = resultSet.getString("course_name");
					String duration = resultSet.getString("duration");
					String description = resultSet.getString("description");

					System.out.println("Course ID: " + courseId);
					System.out.println("Course Name: " + courseName);
					System.out.println("Duration: " + duration);
					System.out.println("Description: " + description);
				} else {
					System.out.println("Course not found with ID: " + courseId);
				}
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
	}

	public void handleSQLException(SQLException e) {
		System.err.println("SQL Exception occurred:");
		System.err.println("SQL State: " + e.getSQLState());
		System.err.println("Error Code: " + e.getErrorCode());
		System.err.println("Message: " + e.getMessage());

		// Specific handling for different SQL states or error codes
		if (e.getSQLState().equals("42S02")) {
			System.err.println("Table not found. Check your SQL query or database schema.");
		} else if (e.getSQLState().startsWith("23")) {
			System.err.println("Data integrity violation. Check your data constraints.");
		} else {
			System.err.println("Unknown SQL error occurred.");
		}

		// Optionally, you can log the exception or perform other error handling tasks.

		e.printStackTrace();
	}

}
