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

		AttendanceEntry entry = null;

		if (!isEdit()) {
			entry = new AttendanceEntry(attendances.size() + 1, student, date, wasPresent);

			if (entry != null) {
				insertData(entry);
			}

		} else {
			entry = getEditingItem();
			entry.setStudent(student);
			entry.setDate(date);
			entry.setPresent(wasPresent);

			ObservableList<AttendanceEntry> attendances = getData();
			Integer idx = getItemIdxInData(attendances, entry);
			if (!idx.equals(-1)) {
				attendances.set(idx, entry);
				setData(attendances);
			} else {
				System.out.println("Nerastas redaguojamas lankomumo irasas");
			}
		}

		finishSubmit();
	}

	@Override
	protected String getTitle() {
		return "Add Attendance";
	}

	@Override
	protected void prefilForm() {
		// TODO Auto-generated method stub
		AttendanceEntry editedItem = getEditingItem();
		((ComboBox<Student>) fields.get(0)).setValue(editedItem.getStudent());
		((DatePicker) fields.get(1)).setValue(editedItem.getDate());
		((ComboBox<String>) fields.get(2)).setValue(editedItem.isPresentString());
	}

}
