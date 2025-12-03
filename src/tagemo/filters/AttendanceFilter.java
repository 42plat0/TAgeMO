package tagemo.filters;

import javafx.collections.ObservableList;
import tagemo.main.AttendanceEntry;

public interface AttendanceFilter {
	/**
	 * Applies the specific filter logic to the original list of attendance entries.
	 * 
	 * @param allAttendances The full list of attendance entries to be filtered.
	 * @return An ObservableList containing the filtered subset of attendance
	 *         entries.
	 */
	ObservableList<AttendanceEntry> filter(ObservableList<AttendanceEntry> allAttendances);
}
