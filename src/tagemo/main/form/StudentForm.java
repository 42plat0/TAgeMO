package tagemo.main.form;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import tagemo.core.Person;
import tagemo.main.Form;
import tagemo.main.Student;

public class StudentForm extends Form<Student> {

	public StudentForm() {
		initUI();
	}

	@Override
	protected List<TextField> createFields() {
		return List.of(new TextField(), new TextField());
	}

	@Override
	protected void handleSubmit() {
		String firstName = ((TextField) fields.get(0)).getText();
		String lastName = ((TextField) fields.get(1)).getText();

		Student student = null;
		if (!isEdit()) {
			student = new Student(data.size() + 1, new Person(firstName, lastName));
			insertData(student);
		} else {
			student = getEditingItem();
			student.setPerson(new Person(firstName, lastName));
			ObservableList<Student> students = getData();
			Integer idx = getItemIdxInData(students, student);
			if (!idx.equals(-1)) {
				students.set(idx, student);
				setData(students);
			} else {
				System.out.println("Nerastas studentas redaguoti");
			}
		}

		finishSubmit();
	}

	@Override
	protected String getTitle() {
		return !isEdit() ? "Kurti studentą" : "Redaguoti studenta";
	}

	@Override
	public void setFormTarget(ObservableList<Student> students) {
		setData(students);
	}

	@Override
	public void prefilForm() {
		if (!isEdit()) {
			return;
		}

		Student student = getEditingItem();
		((TextField) fields.get(0)).setText(student.getPerson().getFirstName());
		((TextField) fields.get(1)).setText(student.getPerson().getLastName());

	}

}
