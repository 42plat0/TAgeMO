package tagemo.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public abstract class Grid<T> extends TableView<T> {
	public ObservableList<T> data;

	public Grid() {
		setEditable(false);
		setItems(FXCollections.observableArrayList());
		createColumns();
	}

	public abstract void createColumns();

	public void setData(ObservableList<T> data) {
		this.data = data;
		setItems(data);
	}
}
