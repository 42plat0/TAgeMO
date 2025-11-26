package tagemo.main.grid;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import tagemo.main.Grid;
import tagemo.main.Student;

public class StudentGrid extends Grid<Student> {

	@Override
	public void createColumns() {

		TableColumn<Student, String> nameCol = new TableColumn<>("Vardas");
		TableColumn<Student, String> surnameCol = new TableColumn<>("Pavardė");

		nameCol.setCellValueFactory(
				p -> new SimpleStringProperty(String.valueOf(p.getValue().getPerson().getFirstName())));

		surnameCol.setCellValueFactory(
				p -> new SimpleStringProperty(String.valueOf(p.getValue().getPerson().getLastName())));

		getColumns().addAll(nameCol, surnameCol);

	}
}
