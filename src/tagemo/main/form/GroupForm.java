package tagemo.main.form;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import tagemo.core.Group;
import tagemo.main.Form;

public class GroupForm extends Form<Group> {

	public GroupForm() {
		initUI();
	}

	@Override
	protected List<TextField> createFields() {
		return List.of(new TextField("Pavadinimas"));
	}

	@Override
	protected void handleSubmit() {
		String name = ((TextField) fields.get(0)).getText();

		Group group = null;

		if (!isEdit()) {
			group = new Group(data.size() + 1, name);
			insertData(group);
		} else {
			group = getEditingItem();
			group.setName(name);
			ObservableList<Group> groups = getData();
			Integer idx = getItemIdxInData(groups, group);
			if (!idx.equals(-1)) {
				groups.set(idx, group);
				setData(groups);
			} else {
				System.out.println("Nerastas studentas redaguoti");
			}
		}

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

	@Override
	protected void prefilForm() {
		Group group = getEditingItem();
		((TextField) fields.get(0)).setText(group.getName());
	}

}
