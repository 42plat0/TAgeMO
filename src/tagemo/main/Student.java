package tagemo.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tagemo.core.Group;
import tagemo.core.Groupable;
import tagemo.core.Person;

@Getter
@Setter
@AllArgsConstructor
public class Student implements Groupable {

	private long id;
	private Person person;

	@Override
	public void addToGroup(Group group) {
		// TODO
	}

	@Override
	public String toString() {
		return id + "," + person.toString();
	}
}
