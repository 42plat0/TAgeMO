package tagemo.app;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tagemo.core.Group;
import tagemo.core.Person;
import tagemo.main.AttendanceEntry;
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
	private ObservableList<AttendanceEntry> attendances = FXCollections.observableArrayList();
	private ObservableList<AttendanceEntry> filteredAttendances = FXCollections.observableArrayList();
	private GroupManager groupManager = new GroupManager();

	private Integer currentGrid = Constants.NO_GRID;

	private AttendanceGrid attGrid = null;

	@FXML
	private AnchorPane contentPane;
	@FXML
	private Button createBtn;
	@FXML
	private Button addToGroupBtn;
	@FXML
	private Button filterBtn;
	@FXML
	private VBox filterContainer;
	@FXML
	private ComboBox<String> filterPromptCbox;
	@FXML
	private ComboBox<Object> filterContentCbox;

	@FXML
	public void initialize() {
		if (currentGrid.equals(Constants.NO_GRID)) {
			createBtn.setVisible(false);
			addToGroupBtn.setVisible(false);
			filterBtn.setVisible(false);
			filterContainer.setVisible(false);
		}

		// Choices for attendance filtering
		filterPromptCbox.getItems().addAll(Constants.FILTER_BY_GROUP, Constants.FILTER_BY_STUDENT);
		setDummyData();
	}

	private void setDummyData() {
		students.add(new Student(1, new Person("nelankantis", "test1")));
		students.add(new Student(2, new Person("xyzGrupe", "111")));
		students.add(new Student(3, new Person("unionGrupe", "222")));
		students.add(new Student(4, new Person("unionGrupe1", "333")));

		groups.add(new Group(1, "grupe-test1"));
		groups.add(new Group(2, "Gxyz"));
		groups.add(new Group(3, "union"));

		attendances.add(new AttendanceEntry(1, students.get(0), LocalDate.now(), false));
		attendances.add(new AttendanceEntry(2, students.get(1), LocalDate.now(), true));
		attendances.add(new AttendanceEntry(3, students.get(2), LocalDate.now(), true));
		attendances.add(new AttendanceEntry(4, students.get(3), LocalDate.now(), true));

		groupManager.addStudentToGroup(students.get(1), groups.get(1));
		groupManager.addStudentToGroup(students.get(0), groups.get(0));
		groupManager.addStudentToGroup(students.get(2), groups.get(2));
		groupManager.addStudentToGroup(students.get(3), groups.get(2));

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
//		Grid<AttendanceEntry> attendanceGrid = new AttendanceGrid();
		if (attGrid == null) {
			attGrid = new AttendanceGrid();
		}
		attGrid.setData(attendances);

		addComponentToContentPane(attGrid);
		updateCurrentGrid(Constants.ATTENDANCE_GRID);
		addToGroupBtn.setVisible(false);
		filterContainer.setVisible(true);
		filterBtn.setVisible(true);
	}

	@FXML
	private void handleReportsBtnAction(ActionEvent event) {

	}

	@FXML
	private void handleImportExportBtnAction(ActionEvent event) {

	}

	@FXML
	private void initializeFflterContentCbox(ActionEvent event) {
		String selectedOption = filterPromptCbox.getValue();
		switch (selectedOption) {
		case Constants.FILTER_BY_GROUP:
			// show groups
			filterContentCbox.setItems((ObservableList<Object>) (ObservableList<?>) groups);
			break;

		case Constants.FILTER_BY_STUDENT:
			// show students
			filterContentCbox.setItems((ObservableList<Object>) (ObservableList<?>) students);
			break;
		}
	}

	@FXML
	private void handleFilterBtnAction(ActionEvent event) {
		if (!filteredAttendances.isEmpty()) {
			filteredAttendances.clear();
		}

		Object filterBy = filterContentCbox.getValue();

		if (filterBy instanceof Student s) {
			filteredAttendances.addAll(attendances.stream().filter(a -> a.getStudent().equals(s))
					.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		} else if (filterBy instanceof Group g) {
			List<Student> studentsInGroup = groupManager.getStudentsInGroup(g);
			filteredAttendances.addAll(attendances.stream().filter(a -> studentsInGroup.contains(a.getStudent()))
					.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		}

		attGrid.setData(filteredAttendances);
		attGrid.refresh();
	}

	@FXML
	private void handleCreateBtnAction(ActionEvent event) {
		showDialogPane();
	}

	@FXML
	private void handleAddToGroupBtnAction(ActionEvent event) {
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
			break;
		case Constants.GROUP_GRID:
			form = new GroupForm();
			form.setFormTarget(groups);
			break;
		case Constants.ATTENDANCE_GRID:
			form = new AttendanceForm(students, attendances);
			form.setFormTarget(attendances);
			break;
		default:
			break;
		}

		if (form != null) {
			form.show();
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
