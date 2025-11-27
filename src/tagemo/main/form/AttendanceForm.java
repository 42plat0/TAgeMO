package tagemo.main.form;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import tagemo.main.AttendanceEntry;
import tagemo.main.Form;
import tagemo.main.Student;

public class AttendanceForm extends Form<AttendanceEntry> {

	private ObservableList<AttendanceEntry> attendances;

	private ComboBox<Student> studentBox = new ComboBox<>();
	private DatePicker datePicker = new DatePicker();
	private ComboBox<String> presentBox = new ComboBox<>();

	public AttendanceForm(ObservableList<Student> students, ObservableList<AttendanceEntry> attendances) {
		this.attendances = attendances;

		studentBox.setItems(students);
		presentBox.getItems().addAll("Buvo", "Nebuvo");
		initUI();
	}

	@Override
	public void setFormTarget(ObservableList<AttendanceEntry> items) {
		setData(items);
	}

	@Override
	protected List<Node> createFields() {
		return List.of(studentBox, datePicker, presentBox);
	}

	@Override
	protected void handleSubmit() {
		Student student = studentBox.getValue();
		LocalDate date = datePicker.getValue();
		String pv = presentBox.getValue();

		if (student == null || date == null || pv == null) {
			System.out.println("Missing fields");
			return;
		}

		boolean wasPresent = pv.equals("Buvo");

		AttendanceEntry entry = new AttendanceEntry(attendances.size() + 1, student, date, wasPresent);

		if (entry != null) {
			insertData(entry);
		}

		finishSubmit();
	}

	@Override
	protected String getTitle() {
		return "Add Attendance";
	}

}
