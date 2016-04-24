package hu.csega.update;

import java.util.StringTokenizer;

public class FileResponse {

	public static FileResponse fromString(String content) {
		StringTokenizer tokenizer = new StringTokenizer(content, "|");

		FileResponse downloadRequest = new FileResponse();
		downloadRequest.message = tokenizer.nextToken().trim();
		downloadRequest.name = tokenizer.nextToken().trim();
		downloadRequest.size = Long.parseLong(tokenizer.nextToken().trim());
		downloadRequest.content = tokenizer.nextToken().trim();

		return downloadRequest;
	}

	public String toString() {
		return message + '|'  + name + '|' + size + '|' + content;
	}

	public String message;
	public String name;
	public long size;
	public String content;
}
