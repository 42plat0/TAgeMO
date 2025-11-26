package tagemo.main.form;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import tagemo.core.Group;
import tagemo.main.Form;

public class GroupForm extends Form<Group> {

	@Override
	protected List<TextField> createFields() {
		return List.of(new TextField("Pavadinimas"));
	}

	@Override
	protected void handleSubmit() {
		String name = fields.get(0).getText();

		insertData(new Group(data.size() + 1, name));

		finishSubmit();
	}

	@Override
	protected String getTitle() {
		return "Kurti grupę";
	}

	@Override
	public void setFormTarget(ObservableList<Group> data) {
		setData(data);
	}

}
