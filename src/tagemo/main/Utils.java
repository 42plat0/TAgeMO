package tagemo.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import tagemo.core.Person;

public class Utils {

	public static File chooseAndGetPdf(Pane pane, String fileChooserTitle) {
		return chooseAndGetFile(pane, fileChooserTitle, "*.pdf", true);
	}

	public static File chooseAndGetCsv(Pane pane, String fileChooserTitle, boolean isSave) {
		return chooseAndGetFile(pane, fileChooserTitle, "*.csv", isSave);
	}

	public static File chooseAndGetFile(Pane pane, String fileChooserTitle, String fileExtension, boolean isSave) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("test");
		fileChooser.setTitle(fileChooserTitle);
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*", "*"));
		File file = null;
		if (isSave) {
			file = fileChooser.showSaveDialog(pane.getScene().getWindow());

		} else {
			file = fileChooser.showOpenDialog(pane.getScene().getWindow());
		}
		return file;
	}

	public static void exportDataToPdf(File file, ObservableList<AttendanceEntry> list, GroupManager groupManager)
			throws IOException {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream content = new PDPageContentStream(document, page);

		int y = 750;
		for (AttendanceEntry attendanceEntry : list) {
			Student student = attendanceEntry.getStudent();
			String groupText = GroupManager.getGroupsInString(groupManager.getGroupsHavingStudent(student));
			String line = student.toString() + ", data: " + attendanceEntry.getDate().toString() + " buvo: "
					+ attendanceEntry.isPresentString() + ", grupes: [" + groupText + "];";
			content.beginText();
			content.setFont(PDType1Font.COURIER, 12);
			content.newLineAtOffset(50, y);
			content.showText(line);
			content.endText();
			y -= 20; // move down for next line
		}

		content.close();
		document.save(file);
		document.close();
	}

	public static List<String[]> importStudents(File file) throws IOException {
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);

		String line = br.readLine(); // remove headers
		List<String[]> readVals = new ArrayList<String[]>();

		while ((line = br.readLine()) != null) {
			String[] rowVals = line.split(",");
			readVals.add(rowVals);
		}
		return readVals;
	}

	public static List<Student> parseImportedStudents(List<String[]> data) {
		List<Student> students = new ArrayList<>(data.size());
		for (String[] studentFields : data) {
			Long id = Long.valueOf(studentFields[0]);
			String firstName = studentFields[1].split(" ")[0];
			String lastName = studentFields[1].split(" ")[1];
			System.out.println(studentFields);
			System.out.println(firstName);
			System.out.println(lastName);
			students.add(new Student(id, new Person(firstName, lastName)));
		}
		return students;
	}

	public static void exportStudents(File file, ObservableList<Student> data, GroupManager manager)
			throws IOException {
		FileWriter fw = new FileWriter(file);
		StringBuilder sb = new StringBuilder();
		// Headers
		for (Field field : data.getFirst().getClass().getDeclaredFields()) {
			sb.append(field.getName()).append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("\n");
		data.forEach(item -> sb.append(item + ", grupes: ["
				+ GroupManager.getGroupsInString(manager.getGroupsHavingStudent(item)) + "]" + "\n"));

		fw.write(sb.toString());
		fw.close();

	}

}
