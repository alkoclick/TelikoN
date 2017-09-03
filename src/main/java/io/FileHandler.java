package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;

import com.topologi.diffx.DiffXException;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileHandler {

	public static Body reader(String filename) throws Docx4JException {
		WordprocessingMLPackage newerPackage = WordprocessingMLPackage.load(new java.io.File(filename));
		return ((Document) newerPackage.getMainDocumentPart().getContents()).getBody();
	}

	public static boolean compare(String file1, String file2) throws DiffXException, IOException, Docx4JException {
		Body older = reader(file1);
		Body newer = reader(file2);
		for (int i = 0; i < older.getContent().size(); i++) {
			if (!older.getContent().get(i).toString().equals(newer.getContent().get(i).toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Reads a file line by line and returns all lines in a new list
	 * 
	 * @param filename
	 *            The file to read from
	 * @throws IOException
	 * 
	 * @return ArrayList where each element is one line of the file
	 * @throws Docx4JException
	 */
	public static List<String> readToStrings(String filename) throws Docx4JException {
		ArrayList<String> lines = new ArrayList<>();
		Body body = reader(filename);
		for (Object L : body.getContent()) {
			lines.add(L.toString());
		}
		return lines;
	}

	/**
	 * Reads a file line by line and returns all lines in a new list. It Also
	 * Catches any exceptions
	 * 
	 * @param filename
	 *            The file to read from
	 * @return ArrayList where each element is one line of the file
	 */
	public static List<String> safeReader(String filename) {
		ArrayList<String> lines = new ArrayList<>();

		// Try with resources autocloses the reader when done, regardless of try
		// result - Java 1.7 +
		// No need to close the temporary FileReader, java does it for us
		// http://stackoverflow.com/questions/16584777/is-it-necessary-to-close-a-filewriter-provided-it-is-written-through-a-buffered
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String L;
			while ((L = reader.readLine()) != null) {
				// Ignore comments and empty lines
				if (!(L.isEmpty())) {
					lines.add(L);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	public static boolean writer(String data, String path, boolean append) {
		BufferedWriter bw = null;
		boolean success = false;
		try {
			bw = new BufferedWriter(new FileWriter(path, append));
			bw.write(data);
			bw.newLine();
			bw.flush();
			success = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
					ioe2.printStackTrace();
				}
			}
		}
		return success;
	}

	public static boolean writer(List<String> data, String path, boolean append) {
		BufferedWriter bw = null;
		boolean success = false;
		try {
			bw = new BufferedWriter(new FileWriter(path, append));
			for (int i = 0; i < data.size(); ++i) {
				bw.write(data.get(i));
				bw.newLine();
				bw.flush();
			}
			success = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
					ioe2.printStackTrace();
				}
			}
		}
		return success;
	}

	public static boolean showFileChooser(String filename, String buttName, String fileDesc, String fileExt,
			List<String> writeable) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName(filename);
		fileChooser.setTitle(buttName);
		ExtensionFilter extensionFilter = new ExtensionFilter(fileDesc, fileExt);
		fileChooser.getExtensionFilters().add(extensionFilter);

		File file = fileChooser.showSaveDialog(null);
		return (file != null && FileHandler.writer(writeable, file.getAbsolutePath(), false));

	}

}
