package tagemo.main.form;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import tagemo.core.Person;
import tagemo.main.Form;
import tagemo.main.Student;

public class StudentForm extends Form<Student> {

	@Override
	protected List<TextField> createFields() {
		return List.of(new TextField("Vardas"), new TextField("Pavardė"));
	}

	@Override
	protected void handleSubmit() {
		String firstName = fields.get(0).getText();
		String lastName = fields.get(1).getText();

		insertData(new Student(data.size() + 1, new Person(firstName, lastName)));

		finishSubmit();
	}

	@Override
	protected String getTitle() {
		return "Kurti studentą";
	}

	@Override
	public void setFormTarget(ObservableList<Student> students) {
		setData(students);
	}

}
