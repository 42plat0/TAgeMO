package tagemo.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tagemo.core.Person;

@Getter
@Setter
@AllArgsConstructor
public class Student {

	private long id;
	private Person person;

	@Override
	public String toString() {
		return id + "," + person.toString();
	}
}
