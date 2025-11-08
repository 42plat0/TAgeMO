package tagemo.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group {

	private String name;

	@Override
	public String toString() {
		return name;
	}
}
