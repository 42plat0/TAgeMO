package tagemo.main.form;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tagemo.core.Attendance;
import tagemo.core.Group;
import tagemo.main.AttendanceManager;
import tagemo.main.Student;

public class AttendanceForm {

	private ObservableList<Attendance> attendances;
	private ObservableList<Student> students;
	private final AttendanceManager manager;

	private final ComboBox<Student> studentBox = new ComboBox<>();
	private final ComboBox<Group> groupBox = new ComboBox<>();
	private final DatePicker datePicker = new DatePicker();

	public AttendanceForm(ObservableList<Student> students, ObservableList<Attendance> attendances,
			AttendanceManager manager) {

		this.attendances = attendances;
		this.students = students;
		this.manager = manager;

		studentBox.setItems(students);
	}

	public void show() {
		Stage stage = new Stage();
		VBox v = new VBox(10, studentBox, datePicker, new Button("Prideti lankomuma"));
		v.setPadding(new Insets(20));
		stage.setScene(new Scene(v));

		Button btn = (Button) v.getChildren().get(2);
		btn.setOnAction(e -> {
			Student s = studentBox.getValue();
			LocalDate date = datePicker.getValue();
//			Group g = groupBox.getValue();
//			manager.addStudentToGroup(s, g);
			stage.close();
		});

		stage.show();
	}
}
