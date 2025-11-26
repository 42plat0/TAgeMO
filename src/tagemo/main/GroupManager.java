package tagemo.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tagemo.core.Group;

public class GroupManager {

	private List<Group> groups = new ArrayList<Group>();
	private List<Student> students = new ArrayList<Student>();
	private Map<Group, List<Student>> memberships = new HashMap<Group, List<Student>>();

	/*
	 * 
	 * 
	 * - addStudentToGroup(student, group) - removeStudentFromGroup(student, group)
	 * - getStudentsInGroup(group) - getGroupsForStudent(student)
	 * 
	 */

	private void addGroup(Group group) {
		groups.add(group);
		memberships.put(group, (List) new ArrayList<Group>());
	}

	public void addStudentToGroup(Student student, Group group) {
		if (!groups.contains(group)) {
			addGroup(group);
		}

		List<Student> membershipStudents = memberships.get(group);
		if (!membershipStudents.contains(student)) {
			membershipStudents.add(student);
			memberships.put(group, membershipStudents);
		}
	}

	public int getGroupSize(Group group) {
		if (memberships.get(group) == null) {
			return 0;
		}
		return memberships.get(group).size();
	}

	// TODO
	public List<Student> getStudentsInGroup(Group group) {
		return memberships.get(group);
	}
}
