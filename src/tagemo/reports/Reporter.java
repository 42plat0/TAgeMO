package tagemo.reports;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public interface Reporter<T> {
	/**
	 * Exports attendance entries to a file
	 * 
	 * @param entries     List of attendance entries to export
	 * @param destination File to save the export
	 * @throws IOException if export fails
	 */
	void export(List<T> entries, File destination) throws IOException;

	List<T> dataImport(File destination) throws IOException;

	/**
	 * Returns the file extension for this exporter
	 * 
	 * @return file extension (e.g., "pdf", "csv")
	 */
	String getFileExtension();

	/**
	 * Returns a human-readable description of this export format
	 * 
	 * @return description (e.g., "PDF Document")
	 */
	String getDescription();

	public static File chooseAndGetPdf(Pane pane, String fileChooserTitle) {
		return chooseAndGetFile(pane, fileChooserTitle, ".pdf", true);
	}

	public static File chooseAndGetCsv(Pane pane, String fileChooserTitle, boolean isSave) {
		return chooseAndGetFile(pane, fileChooserTitle, ".csv", isSave);
	}

	public static File chooseAndGetFile(Pane pane, String fileChooserTitle, String fileExtension, boolean isSave) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("test" + fileExtension);
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
}
