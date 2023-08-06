package jdbc.dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.model.Course;

public class CourseDAOImpl implements CourseDAO {
	private Connection connection;

	public CourseDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void addCourse(Course course) {
		String query = "INSERT INTO course (duration, course_name, univ_id, description) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, course.getDuration());
			preparedStatement.setString(2, course.getCourseName());
			preparedStatement.setInt(3, course.getUnivId());
			preparedStatement.setString(4, course.getDescription());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCourse(Course course) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE course SET duration=?, course_name=?, univ_id=?, description=? WHERE course_id=?");
			preparedStatement.setString(1, course.getDuration());
			preparedStatement.setString(2, course.getCourseName());
			preparedStatement.setInt(3, course.getUnivId());
			preparedStatement.setString(4, course.getDescription());
			preparedStatement.setInt(5, course.getCourseId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteCourse(int courseId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course WHERE course_id=?");
			preparedStatement.setInt(1, courseId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Course getCourseById(int courseId) {
		Course course = null;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM courses WHERE course_id=?");
			preparedStatement.setInt(1, courseId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setDuration(resultSet.getString("duration"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setUnivId(resultSet.getInt("univ_id"));
				course.setDescription(resultSet.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setDuration(resultSet.getString("duration"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setUnivId(resultSet.getInt("univ_id"));
				course.setDescription(resultSet.getString("description"));
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
}