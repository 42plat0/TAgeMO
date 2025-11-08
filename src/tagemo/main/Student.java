package tagemo.main;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import tagemo.core.Attendance;
import tagemo.core.Group;
import tagemo.core.Groupable;
import tagemo.core.Person;

@Getter
@Setter
public class Student implements Groupable {

	private Person person;
	private List<Attendance> attendances;

	private List<Group> groups;

	@Override
	public List<Group> getGroups() {
		return groups;
	}

	@Override
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
