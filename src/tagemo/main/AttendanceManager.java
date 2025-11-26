package tagemo.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManager {
	private List<AttendanceEntry> records = new ArrayList<AttendanceEntry>();

	/*
	 * 
	 * - markPresent(student, date, group?) - getAttendanceForStudent(student) -
	 * getAttendanceForGroup(group) - getBetweenDates(start, end)
	 * 
	 */

	public void markPresent(Student student, LocalDate date, Boolean isPresent) {
		AttendanceEntry attendanceEntry = new AttendanceEntry(student, date, isPresent);

		for (AttendanceEntry record : records) {
			if (!record.equals(attendanceEntry)) {
				records.add(attendanceEntry);
			}
		}
	}

}
