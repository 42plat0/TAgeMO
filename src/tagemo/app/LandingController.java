package tagemo.app;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tagemo.core.Group;
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
import tagemo.reports.PDFReporter;
import tagemo.reports.Reporter;
import tagemo.reports.StudentReporter;

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
	private Button editBtn;
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
	private Button filterAttendentDaysBtn;
	@FXML
	private HBox filterByContentContainer;
	@FXML
	private Button reportBtn;

	private boolean isFilteredByAttendantDays = false;

	@FXML
	public void initialize() {
		if (currentGrid.equals(Constants.NO_GRID)) {
			createBtn.setVisible(false);
			addToGroupBtn.setVisible(false);
			filterContainer.setVisible(false);
			filterAttendentDaysBtn.setVisible(false);
			reportBtn.setVisible(false);
			editBtn.setVisible(false);
		}

		// Choices for attendance filtering
		filterPromptCbox.getItems().addAll(Constants.FILTER_BY_GROUP, Constants.FILTER_BY_STUDENT,
				Constants.FILTER_BY_DATES);
		setDummyData();
	}

	private void setDummyData() {
//		students.add(new Student(1, new Person("nelankantis", "test1")));
//		students.add(new Student(2, new Person("xyzGrupe", "111")));
//		students.add(new Student(3, new Person("unionGrupe", "222")));
//		students.add(new Student(4, new Person("unionGrupe1", "333")));
//		students.add(new Student(4, new Person("priklausantis2grupem", "444")));
//
//		groups.add(new Group(1, "grupe-test1"));
//		groups.add(new Group(2, "Gxyz"));
//		groups.add(new Group(3, "union"));
//
//		attendances.add(new AttendanceEntry(1, students.get(0), LocalDate.now(), false));
//		attendances.add(new AttendanceEntry(2, students.get(1), LocalDate.now(), true));
//		attendances.add(new AttendanceEntry(3, students.get(2), LocalDate.now(), true));
//		attendances.add(new AttendanceEntry(4, students.get(3), LocalDate.now(), true));
//		attendances.add(new AttendanceEntry(5, students.get(4), LocalDate.now(), true));
//
//		groupManager.addStudentToGroup(students.get(1), groups.get(1));
//		groupManager.addStudentToGroup(students.get(0), groups.get(0));
//		groupManager.addStudentToGroup(students.get(2), groups.get(2));
//		groupManager.addStudentToGroup(students.get(3), groups.get(2));
//		groupManager.addStudentToGroup(students.get(4), groups.get(1));
//		groupManager.addStudentToGroup(students.get(4), groups.get(2));

	}

	@FXML
	private void handleStudentBtnAction(ActionEvent action) {
		Grid<Student> studentGrid = new StudentGrid();
		studentGrid.setData(students);

		addComponentToContentPane(studentGrid);
		updateCurrentGrid(Constants.STUDENT_GRID);
		addToGroupBtn.setVisible(true);
		filterAttendentDaysBtn.setVisible(false);

		filterContainer.setVisible(false);
		reportBtn.setVisible(false);
	}

	@FXML
	private void handleGroupBtnAction(ActionEvent event) {
		Grid<Group> groupGrid = new GroupGrid();
		groupGrid.setData(groups);
		((GroupGrid) groupGrid).setManager(groupManager);

		addComponentToContentPane(groupGrid);
		updateCurrentGrid(Constants.GROUP_GRID);

		filterContainer.setVisible(false);
		filterBtn.setVisible(false);
		filterAttendentDaysBtn.setVisible(false);
		reportBtn.setVisible(false);

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
		filterContainer.setVisible(true);
		filterBtn.setVisible(true);
		filterAttendentDaysBtn.setVisible(true);
		filterAttendentDaysBtn.setVisible(true);
		addToGroupBtn.setVisible(false);
		reportBtn.setVisible(true);

	}

	@FXML
	private void handleImportExportBtnAction(ActionEvent event) {
		Button importBtn = new Button("Importuoti studentus is CSV");
		importBtn.setOnAction(e -> {
			File file = Reporter.chooseAndGetCsv(contentPane, "Ikelti studentų duomenis", false);
			try {
				Reporter<Student> reporter = new StudentReporter();
				List<Student> importedStudents = reporter.dataImport(file);
				students.addAll(importedStudents);

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		Button exportBtn = new Button("Eksportuoti studentus i CSV");
		exportBtn.setOnAction(e -> {
			File file = Reporter.chooseAndGetCsv(contentPane, "Išsaugoti studentų duomenis sąraše", true);
			try {
				Reporter<Student> reporter = new StudentReporter();
				reporter.export(students, file);
//				Utils.exportStudents(file, students, groupManager);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		HBox container = new HBox(importBtn, exportBtn);
		addComponentToContentPane(container);

	}

	@FXML
	private void handleFilterAttendentDaysBtnAction(ActionEvent event) {
		if (!filteredAttendances.isEmpty()) {
			filteredAttendances.clear();
		}
		if (isFilteredByAttendantDays) {
			attGrid.setData(attendances);
			isFilteredByAttendantDays = false;
			filterAttendentDaysBtn.setText("Rodyti užpildytas dienas");
			return;
		}
		filteredAttendances.addAll(attendances.stream().filter(a -> a.isPresent())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));

		attGrid.setData(filteredAttendances);
		attGrid.refresh();
		isFilteredByAttendantDays = true;
		filterAttendentDaysBtn.setText("Rodyti visas dienas");
	}

	@FXML
	private void handleReportsBtnAction() {
		File file = Reporter.chooseAndGetPdf(contentPane, "Išsaugoti studentų lankomumo sąrašą");

		if (file != null) {
			try {
				Reporter<AttendanceEntry> reporter = new PDFReporter();
				reporter.export(!filteredAttendances.isEmpty() ? filteredAttendances : attendances, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void initializeFilterContentCbox(ActionEvent event) {
		String selectedOption = filterPromptCbox.getValue();
		switch (selectedOption) {
		case Constants.FILTER_BY_GROUP:
			filterByContentContainer.getChildren().clear();
			filterByContentContainer.getChildren().add(filterContentCbox);
			// show groups
			filterContentCbox.setItems((ObservableList<Object>) (ObservableList<?>) groups);
			break;

		case Constants.FILTER_BY_STUDENT:
			// show students
			filterByContentContainer.getChildren().clear();
			filterByContentContainer.getChildren().add(filterContentCbox);
			filterContentCbox.setItems((ObservableList<Object>) (ObservableList<?>) students);
			break;
		case Constants.FILTER_BY_DATES:
			filterByContentContainer.getChildren().clear();
			filterContentCbox.hide();
			DatePicker startDP = new DatePicker();
			DatePicker endDP = new DatePicker();
			filterByContentContainer.getChildren().addAll(startDP, endDP);

		}
	}

	@FXML
	private void handleFilterBtnAction(ActionEvent event) {
		if (!filteredAttendances.isEmpty()) {
			filteredAttendances.clear();
		}
		List<Node> filterContentContainerItems = filterByContentContainer.getChildren();

		// filtering by student/group
		if (filterContentContainerItems.size() == 1) {
			Object filterBy = filterContentCbox.getValue();

			if (filterBy instanceof Student s) {
				filteredAttendances.addAll(attendances.stream().filter(a -> a.getStudent().equals(s))
						.collect(Collectors.toCollection(FXCollections::observableArrayList)));
			} else if (filterBy instanceof Group g) {
				List<Student> studentsInGroup = groupManager.getStudentsInGroup(g);
				filteredAttendances.addAll(attendances.stream().filter(a -> studentsInGroup.contains(a.getStudent()))
						.collect(Collectors.toCollection(FXCollections::observableArrayList)));
			}
			// FIltering by dates
		} else if (filterContentContainerItems.size() == 2) {
			;
			LocalDate start = ((DatePicker) filterContentContainerItems.get(0)).getValue();
			LocalDate end = ((DatePicker) filterContentContainerItems.get(1)).getValue();
			filteredAttendances.addAll(attendances.stream().filter(a -> {
				LocalDate attendanceDate = a.getDate();
				return (attendanceDate.isEqual(start) || attendanceDate.isAfter(start))
						&& (attendanceDate.isEqual(end) || attendanceDate.isBefore(end));
			}).collect(Collectors.toCollection(FXCollections::observableArrayList)));
		}
		attGrid.setData(filteredAttendances);
		attGrid.refresh();
	}

	@FXML
	private void handleCreateBtnAction(ActionEvent event) {
		showDialogPane(null);
	}

	@FXML
	private void handleEditBtnAction(ActionEvent event) {
		Grid grid = (Grid) contentPane.getChildren().get(0);
		var selectedItem = grid.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			showDialogPane(selectedItem);
		}
	}

	@FXML
	private void handleAddToGroupBtnAction(ActionEvent event) {
		if (List.of(Constants.STUDENT_GRID, Constants.GROUP_GRID).contains(currentGrid)) {
			new MembershipForm(students, groups, groupManager).show();
		}
	}

	private void showDialogPane(Object editingItem) {
		Form form = null;
		Object editedItem = null;
		switch (currentGrid) {
		case Constants.STUDENT_GRID:
			form = new StudentForm();
			form.setFormTarget(students);
			form.setEditingItem(editingItem);
			break;
		case Constants.GROUP_GRID:
			form = new GroupForm();
			form.setFormTarget(groups);
			form.setEditingItem(editingItem);
			break;
		case Constants.ATTENDANCE_GRID:
			form = new AttendanceForm(students, attendances);
			form.setFormTarget(attendances);
			form.setEditingItem(editingItem);
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
		editBtn.setVisible(true);
	}
}
