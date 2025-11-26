package tagemo.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Group {

	private long id;
	private String name;

	@Override
	public String toString() {
		return name;
	}
}
