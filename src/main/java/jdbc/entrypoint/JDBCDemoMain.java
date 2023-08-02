package jdbc.entrypoint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.DbConnect;
import jdbc.dao.course.CourseDAO;
import jdbc.dao.factory.CourseDAOFactory;
import jdbc.model.Course;

public class JDBCDemoMain {
	public static void main(String[] args) {
		try {
			Connection connection = DbConnect.connect();
			CourseDAO courseDAO = CourseDAOFactory.getCourseDAO(connection);

			// Add a new course
			Course course1 = new Course();
			course1.setDuration("3 months");
			course1.setCourseName("Java Programming");
			course1.setUnivId(1);
			course1.setDescription("Learn Java programming from scratch");
			courseDAO.addCourse(course1);

			// Update an existing course
			Course course2 = courseDAO.getCourseById(1);
			course2.setDuration("4 months");
			course2.setDescription("Learn advanced Java programming concepts");
			courseDAO.updateCourse(course2);

			// Delete a course
			courseDAO.deleteCourse(2);

			// Get a course by ID
			Course course3 = courseDAO.getCourseById(3);
			System.out.println(course3.getCourseName());

			// Get all courses
			List<Course> courses = courseDAO.getAllCourses();
			for (Course course : courses) {
				System.out.println(course.getCourseName());
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
