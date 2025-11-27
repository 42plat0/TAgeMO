package tagemo.main.grid;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import tagemo.main.AttendanceEntry;
import tagemo.main.Grid;

public class AttendanceGrid extends Grid<AttendanceEntry> {

	@Override
	public void createColumns() {
		TableColumn<AttendanceEntry, String> studentCol = new TableColumn<>("Studentas");
		TableColumn<AttendanceEntry, String> dateCol = new TableColumn<>("Data");
		TableColumn<AttendanceEntry, String> wasPresentCol = new TableColumn<>("Buvo");

		studentCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStudent().toString()));
		dateCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDate().toString()));
		wasPresentCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().isPresentString()));

		getColumns().addAll(studentCol, dateCol, wasPresentCol);

	}
}
