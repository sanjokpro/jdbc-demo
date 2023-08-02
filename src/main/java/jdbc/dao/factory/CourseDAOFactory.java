package jdbc.dao.factory;

import java.sql.Connection;

import jdbc.dao.course.CourseDAO;
import jdbc.dao.course.CourseDAOImpl;

public interface CourseDAOFactory {
	public static CourseDAO getCourseDAO(Connection connection) {
		return new CourseDAOImpl(connection);
	}

}
