package tagemo.main;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceEntry {
	private Integer id;
	private Student student;
	private LocalDate date;
	private Boolean present;

	public Boolean isPresent() {
		return present;
	}

	public String isPresentString() {
		return isPresent() ? "Taip" : "Ne";
	}
}
