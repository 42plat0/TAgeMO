package tagemo.filters;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tagemo.core.Group;
import tagemo.main.AttendanceEntry;
import tagemo.main.GroupManager;
import tagemo.main.Student;

public class GroupAttendanceFilter implements AttendanceFilter {

	private final Group group;
	private final GroupManager groupManager;

	public GroupAttendanceFilter(Group group, GroupManager groupManager) {
		this.group = group;
		this.groupManager = groupManager;
	}

	@Override
	public ObservableList<AttendanceEntry> filter(ObservableList<AttendanceEntry> allAttendances) {
		if (group == null) {
			return allAttendances;
		}
		List<Student> studentsInGroup = groupManager.getStudentsInGroup(group);

		return allAttendances.stream().filter(a -> studentsInGroup.contains(a.getStudent()))
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

}
