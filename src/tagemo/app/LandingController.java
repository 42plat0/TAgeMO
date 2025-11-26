package tagemo.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import tagemo.core.Attendance;
import tagemo.core.Group;
import tagemo.main.AttendanceManager;
import tagemo.main.Constants;
import tagemo.main.Form;
import tagemo.main.Grid;
import tagemo.main.GroupManager;
import tagemo.main.Student;
import tagemo.main.form.AttendanceForm;
import tagemo.main.form.GroupForm;
import tagemo.main.form.MembershipForm;
import tagemo.main.form.StudentForm;
import tagemo.main.grid.AttendanceGrid;
import tagemo.main.grid.GroupGrid;
import tagemo.main.grid.StudentGrid;

public class LandingController {

	private ObservableList<Student> students = FXCollections.observableArrayList();
	private ObservableList<Group> groups = FXCollections.observableArrayList();
	private ObservableList<Attendance> attendances = FXCollections.observableArrayList();
	private GroupManager groupManager = new GroupManager();
	private AttendanceManager attendanceManager = new AttendanceManager();

	private Integer currentGrid = Constants.NO_GRID;

	@FXML
	private AnchorPane contentPane;
	@FXML
	private Button createBtn;
	@FXML
	private Button addToGroupBtn;
	@FXML
	private Button addAttendanceBtn;

	@FXML
	public void initialize() {

		if (currentGrid.equals(Constants.NO_GRID)) {
			createBtn.setVisible(false);
			addToGroupBtn.setVisible(false);
			addAttendanceBtn.setVisible(false);
		}
	}

	@FXML
	private void handleStudentBtnAction(ActionEvent action) {
		Grid<Student> studentGrid = new StudentGrid();
		studentGrid.setData(students);

		addComponentToContentPane(studentGrid);
		updateCurrentGrid(Constants.STUDENT_GRID);
		addToGroupBtn.setVisible(true);

	}

	@FXML
	private void handleGroupBtnAction(ActionEvent event) {
		Grid<Group> groupGrid = new GroupGrid();
		groupGrid.setData(groups);
		((GroupGrid) groupGrid).setManager(groupManager);

		addComponentToContentPane(groupGrid);
		updateCurrentGrid(Constants.GROUP_GRID);
	}

	@FXML
	private void handleAttendanceBtnAction(ActionEvent event) {
		Grid<Attendance> attendanceGrid = new AttendanceGrid();
		attendanceGrid.setData(attendances);
//		((AttendanceGrid) attendanceGrid).setManager(attendanceManager);

		addComponentToContentPane(attendanceGrid);
		updateCurrentGrid(Constants.ATTENDANCE_GRID);
	}

	@FXML
	private void handleCreateBtnAction(ActionEvent event) {
		showDialogPane();
	}

	@FXML
	private void handleAddToGroupBtnAction(ActionEvent event) {
		new AttendanceForm(students, attendances, attendanceManager).show();
	}

	@FXML
	private void handleAddAttendanceBtnAction(ActionEvent event) {
		if (Constants.STUDENT_GRID == currentGrid) {
			new MembershipForm(students, groups, groupManager).show();
		}
	}

	private void showDialogPane() {
		Form form = null;
		switch (currentGrid) {
		case Constants.STUDENT_GRID:
			form = new StudentForm();
			form.setFormTarget(students);
			form.show();
			break;
		case Constants.GROUP_GRID:
			form = new GroupForm();
			form.setFormTarget(groups);
			form.show();
			break;
		default:
			break;
		}

	}

	private void addComponentToContentPane(Node node) {
		if (contentPane.getChildren().size() > 0) {
			contentPane.getChildren().clear();
		}
		contentPane.getChildren().add(node);
	}

	private void updateCurrentGrid(Integer val) {
		if (val == null) {
			currentGrid = -1;
			createBtn.setVisible(false);
		}
		currentGrid = val;
		createBtn.setVisible(true);
	}
}
