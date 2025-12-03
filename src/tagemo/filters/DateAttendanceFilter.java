package tagemo.filters;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tagemo.main.AttendanceEntry;

public class DateAttendanceFilter implements AttendanceFilter {
	private final LocalDate startDate;
	private final LocalDate endDate;

	public DateAttendanceFilter(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public ObservableList<AttendanceEntry> filter(ObservableList<AttendanceEntry> allAttendances) {
		if (startDate == null || endDate == null) {
			return allAttendances;
		}

		return allAttendances.stream().filter(a -> {
			LocalDate attendanceDate = a.getDate();
			return (attendanceDate.isEqual(startDate) || attendanceDate.isAfter(startDate))
					&& (attendanceDate.isEqual(endDate) || attendanceDate.isBefore(endDate));
		}).collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

}
