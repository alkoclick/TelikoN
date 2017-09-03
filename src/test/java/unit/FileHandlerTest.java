package unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import io.FileHandler;

public class FileHandlerTest {

	@Test
	public void tryReading() {
		// Try reading a file that has content and making sure its contents
		// match the expected result
		try {
			assertTrue(FileHandler.readToStrings("Tests/basic.docx").get(0)
					.equals("το μεν πνεύμα πρόθυμο η δε σαρξ ασθενής"));
		} catch (Exception e) {
			fail();
		}

		// Try reading a larger file

		// Try reading a nonexistent file
		try {
			FileHandler.reader("Tests/nonExistentFile.docx");
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void tryWriting() {

	}

	@Test
	public void tryComparing() {
		try {
			assertTrue(FileHandler.compare("Tests/correct1.docx", "Tests/correct1Copy.docx"));
			assertTrue(!FileHandler.compare("Tests/correct1.docx", "Tests/problems1.docx"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
