package tagemo.main.grid;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import lombok.Setter;
import tagemo.core.Group;
import tagemo.main.Grid;
import tagemo.main.GroupManager;

public class GroupGrid extends Grid<Group> {

	@Setter
	private GroupManager manager;

	@Override
	public void createColumns() {

		TableColumn<Group, String> nameCol = new TableColumn<>("Pavadinimas");
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getName())));

		TableColumn<Group, String> membershipInGroupCol = new TableColumn<>("Studentų skaičius grupėje");
		membershipInGroupCol
				.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(manager.getGroupSize(p.getValue()))));

		getColumns().addAll(nameCol, membershipInGroupCol);
	}

}
