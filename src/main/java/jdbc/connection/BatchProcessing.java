package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Batch processing is useful when you need to perform a large number of similar
 * database operations (such as inserts, updates, or deletes) on a database
 * table. Instead of executing each operation one by one, batch processing
 * allows you to group multiple operations into a single batch and send them to
 * the database in one go. This can provide several advantages, including
 * improved performance, reduced network round-trips, and better handling of
 * large datasets.
 * 
 * [Problems it solves:] Performance Improvement: When dealing with a large
 * dataset, executing individual database operations for each data record can be
 * slow and inefficient. Batch processing reduces the number of interactions
 * with the database, leading to better overall performance.
 * 
 * Reduced Network Overhead: Each database operation involves network
 * communication between the application and the database server. By using batch
 * processing, you can minimize the network overhead by bundling multiple
 * operations into a single request.
 * 
 * Atomicity and Consistency: Batch processing ensures that all operations
 * within the batch are executed as a single unit of work. If any operation
 * within the batch fails, the entire batch is rolled back, maintaining data
 * consistency.
 * 
 * Now, let's provide an example of how to use batch processing to insert a
 * large amount of data into the previously created "course" table.
 */
public class BatchProcessing {
	public static void main(String[] args) {
		BatchProcessingExample example = new BatchProcessingExample();
		List<Course> courses = example.generateLargeDataSet(); // Replace this with your method to generate the data.

		example.insertCoursesUsingBatch(courses);
	}

	public void insertCoursesUsingBatch(List<Course> courses) {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			String insertQuery = "INSERT INTO course (duration, course_name, univ_id, description) VALUES (?, ?, ?, ?)";
			try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
				for (Course course : courses) {
					insertStatement.setString(1, course.getDuration());
					insertStatement.setString(2, course.getCourseName());
					insertStatement.setInt(3, course.getUnivId());
					insertStatement.setString(4, course.getDescription());
					insertStatement.addBatch();
				}

				int[] batchResults = insertStatement.executeBatch();
				System.out.println("Batch processing completed. Rows inserted: " + batchResults.length);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Dummy Course class to represent the data
	private static class Course {
		private String duration;
		private String courseName;
		private int univId;
		private String description;

		// Constructor, getters, and setters
		// ...

		// Example of generating a large dataset (you can replace this with your own
		// method)
		public static List<Course> generateLargeDataSet() {
			List<Course> courses = new ArrayList<>();
			// Add course data to the list
			// ...

			return courses;
		}
	}

}
