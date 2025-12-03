package tagemo.main;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;;

@Getter
@Setter
public abstract class Form<T> {

	public ObservableList<T> data;
	public T editingItem = null;

	public List<? extends Node> fields;
	public Stage stage;
	public Button submitBtn = new Button("Patvirtinti");

	public Form() {
	}

	public void initUI() {
		fields = createFields();

		stage = new Stage();
		DialogPane dp = new DialogPane();

		VBox box = new VBox(10);
		box.getChildren().addAll(fields);
		box.getChildren().add(submitBtn);
		box.setPadding(new Insets(20));
		dp.setContent(box);

		Scene scene = new Scene(dp, 300, 500);
		stage.setScene(scene);
		stage.setTitle(getTitle());

		submitBtn.setOnAction(e -> handleSubmit());
	}

	public void insertData(T entity) {
		this.data.add(entity);
	}

	public void show() {
		prefilForm();
		stage.show();
	}

	public void finishSubmit() {
		stage.close();
	}

	protected boolean isEdit() {
		return editingItem != null;
	}

	protected Integer getItemIdxInData(ObservableList<T> list, T item) {
		return list.stream().filter(val -> val.equals(item)).map(found -> list.indexOf(found)).findFirst().orElse(-1);
	}

	public abstract void setFormTarget(ObservableList<T> items);

	protected abstract List<? extends Node> createFields();

	protected abstract void handleSubmit();

	protected abstract String getTitle();

	// TODO to abstract
	protected abstract void prefilForm();
}
