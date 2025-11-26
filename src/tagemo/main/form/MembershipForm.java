package tagemo.main.form;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tagemo.core.Group;
import tagemo.main.GroupManager;
import tagemo.main.Student;

public class MembershipForm {

	private final ObservableList<Student> students;
	private final ObservableList<Group> groups;
	private final GroupManager manager;

	private final ComboBox<Student> studentBox = new ComboBox<>();
	private final ComboBox<Group> groupBox = new ComboBox<>();

	public MembershipForm(ObservableList<Student> students, ObservableList<Group> groups, GroupManager manager) {

		this.students = students;
		this.groups = groups;
		this.manager = manager;

		studentBox.setItems(students);
		groupBox.setItems(groups);
	}

	public void show() {
		Stage stage = new Stage();
		VBox v = new VBox(10, studentBox, groupBox, new Button("Pridet"));
		v.setPadding(new Insets(20));
		stage.setScene(new Scene(v));

		Button btn = (Button) v.getChildren().get(2);
		btn.setOnAction(e -> {
			Student s = studentBox.getValue();
			Group g = groupBox.getValue();
			manager.addStudentToGroup(s, g);
			stage.close();
		});

		stage.show();
	}
}