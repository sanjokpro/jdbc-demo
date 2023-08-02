package jdbc.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyExample {
	public static void main(String[] args) {
		ConcurrencyExample example = new ConcurrencyExample();
		example.insertCoursesInParallel();
	}

	public void insertCoursesInParallel() {
	        ExecutorService executorService = Executors.newFixedThreadPool(2);

	        executorService.submit(() -> insertCourse("2 years", "Physics", 1, "Bachelor's in Physics"));
	        executorService.submit(() -> insertCourse("4 years", "Chemistry", 1, "Bachelor's in Chemistry"));

	        executorService.shutdown();
	    }

	private void insertCourse(String duration, String courseName, int univId, String description) {
	        
	        	Connection connection=	DbConnect.connect();
	       
	            String insertQuery = "INSERT INTO course (duration, course_name, univ_id, description) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
	                insertStatement.setString(1, duration);
	                insertStatement.setString(2, courseName);
	                insertStatement.setInt(3, univId);
	                insertStatement.setString(4, description);
	                int rowsAffected = insertStatement.executeUpdate();

	                if (rowsAffected > 0) {
	                    ResultSet generatedKeys = insertStatement.getGeneratedKeys();
	                    if (generatedKeys.next()) {
	                        int generatedId = generatedKeys.getInt(1);
	                        System.out.println("New course created with ID: " + generatedId);
	                    }
	                }
}
