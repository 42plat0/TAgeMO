package tagemo.reports;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import tagemo.main.AttendanceEntry;
import tagemo.main.Student;

public class PDFReporter implements Reporter<AttendanceEntry> {

	@Override
	public void export(List<AttendanceEntry> entries, File destination) throws IOException {
		// TODO Auto-generated method stub
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream content = new PDPageContentStream(document, page);

		int y = 750;
		for (AttendanceEntry attendanceEntry : entries) {
			Student student = attendanceEntry.getStudent();
//			String groupText = GroupManager.getGroupsInString(groupManager.getGroupsHavingStudent(student));
			String line = student.toString() + ", data: " + attendanceEntry.getDate().toString() + " buvo: "
					+ attendanceEntry.isPresentString() + ", grupes: [" + "groupText" + "];";
			content.beginText();
			content.setFont(PDType1Font.COURIER, 12);
			content.newLineAtOffset(50, y);
			content.showText(line);
			content.endText();
			y -= 20; // move down for next line
		}

		content.close();
		document.save(destination);
		document.close();

	}

	@Override
	public List dataImport(File destination) throws IOException {
		return null;
	}

	@Override
	public String getFileExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
