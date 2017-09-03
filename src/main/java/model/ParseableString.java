package model;

public class ParseableString {

	private String contents;

	public ParseableString(String contents) {
		this.contents = contents;
	}

	public String applyAll() {
		replaceSan();
		replaceTwn();
		return contents;
	}

	// Always replace σα with σαν
	public ParseableString replaceSan() {
		contents = contents.replace("σα", "σαν");
		return this;
	}

	// Always replace τω with των
	// Presumably useless, I've never seen this happen
	public ParseableString replaceTwn() {
		contents = contents.replace("τω", "των");
		return this;
	}
}
