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

	// Always replace �� with ���
	public ParseableString replaceSan() {
		contents = contents.replace("��", "���");
		return this;
	}

	// Always replace �� with ���
	// Presumably useless, I've never seen this happen
	public ParseableString replaceTwn() {
		contents = contents.replace("��", "���");
		return this;
	}
}
