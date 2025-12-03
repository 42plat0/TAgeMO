package tagemo.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import tagemo.core.Person;
import tagemo.main.Student;

public class StudentReporter implements Reporter<Student> {

	@Override
	public void export(List<Student> entries, File destination) throws IOException {
		// TODO Auto-generated method stu
		FileWriter fw = new FileWriter(destination);
		StringBuilder sb = new StringBuilder();
		// Headers
		for (Field field : entries.getFirst().getClass().getDeclaredFields()) {
			sb.append(field.getName()).append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("\n");
		entries.forEach(item -> sb.append(item).append("\n"));
//		+ ", grupes: ["
//				+ GroupManager.getGroupsInString(manager.getGroupsHavingStudent(item)) + "]" + "\n"));

		fw.write(sb.toString());
		fw.close();

	}

	@Override
	public List<Student> dataImport(File destination) throws IOException {
		FileReader reader = new FileReader(destination);
		BufferedReader br = new BufferedReader(reader);

		String line = br.readLine(); // remove headers
		List<String[]> readVals = new ArrayList<String[]>();

		while ((line = br.readLine()) != null) {
			String[] rowVals = line.split(",");
			readVals.add(rowVals);
		}
		return parseImportedStudents(readVals);
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

	@Override
	public String getFileExtension() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

}
