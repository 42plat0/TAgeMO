package tagemo.main.form;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import tagemo.main.AttendanceEntry;
import tagemo.main.Form;

public class AttendanceReportForm extends Form<AttendanceEntry> {

	public AttendanceReportForm() {
		initUI();
	}

	@Override
	public void setFormTarget(ObservableList<AttendanceEntry> items) {
		setData(items);
	}

	@Override
	protected List<? extends Node> createFields() {
		DatePicker startDP = new DatePicker();
		DatePicker endDP = new DatePicker();

		return List.of(startDP, endDP);
	}

	@Override
	protected void handleSubmit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	private void exportAttendancesToPDF() {

	}

	@Override
	protected void prefilForm() {
		// TODO Auto-generated method stub

	}

}
