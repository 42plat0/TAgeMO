package tagemo.main;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceEntry {
	private Student student;
	private LocalDate date;
	private Boolean present;
}
