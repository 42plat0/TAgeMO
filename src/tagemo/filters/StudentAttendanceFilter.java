package tagemo.filters;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tagemo.main.AttendanceEntry;
import tagemo.main.Student;

public class StudentAttendanceFilter implements AttendanceFilter {

	private final Student student;

	public StudentAttendanceFilter(Student student) {
		this.student = student;
	}

	@Override
	public ObservableList<AttendanceEntry> filter(ObservableList<AttendanceEntry> allAttendances) {
		if (student == null) {
			return allAttendances;
		}
		return allAttendances.stream().filter(a -> a.getStudent().equals(student))
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
	}
}
