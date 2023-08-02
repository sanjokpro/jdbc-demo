package jdbc.model;

/**
 * With this POJO class, you can easily map data from the "course" table into
 * objects and use them in your Java application. The class follows the POJO
 * principles, making it simple,
 */
public class Course {
	private int courseId;
	private String duration;
	private String courseName;
	private int univId;
	private String description;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		if (duration.contains("chada")) {
			return;

		}
		this.duration = duration;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getUnivId() {
		return univId;
	}

	public void setUnivId(int univId) {
		this.univId = univId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
