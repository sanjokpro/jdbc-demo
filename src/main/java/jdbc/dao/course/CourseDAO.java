package jdbc.dao.course;

import java.util.List;

import jdbc.model.Course;

public interface CourseDAO {
	public void addCourse(Course course);

	public void updateCourse(Course course);

	public void deleteCourse(int courseId);

	public Course getCourseById(int courseId);

	public List<Course> getAllCourses();
}
