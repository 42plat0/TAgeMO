package tagemo.main.grid;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import tagemo.core.Attendance;
import tagemo.main.AttendanceManager;
import tagemo.main.Grid;

public class AttendanceGrid extends Grid<Attendance> {

	private AttendanceManager manager;

	@Override
	public void createColumns() {
		TableColumn<Attendance, String> studentCol = new TableColumn<>("Studentas");
		TableColumn<Attendance, String> dateCol = new TableColumn<>("Data");
		TableColumn<Attendance, String> wasPresentCol = new TableColumn<>("Data");

		studentCol.setCellValueFactory(p -> new SimpleStringProperty(p.toString()));
		dateCol.setCellValueFactory(p -> new SimpleStringProperty((String) p.getValue()[1]));
		wasPresentCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[1].toString()));

		getColumns().addAll(studentCol, dateCol, wasPresentCol);

	}
}
