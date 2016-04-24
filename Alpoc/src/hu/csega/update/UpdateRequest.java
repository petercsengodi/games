package hu.csega.update;

import java.util.StringTokenizer;

public class UpdateRequest {

	public static UpdateRequest fromString(String content) {
		StringTokenizer tokenizer = new StringTokenizer(content, "|");

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.message = tokenizer.nextToken().trim();
		updateRequest.host = tokenizer.nextToken().trim();
		updateRequest.port = Integer.parseInt(tokenizer.nextToken().trim());
		updateRequest.taskName = tokenizer.nextToken().trim();
		updateRequest.version = tokenizer.nextToken().trim();

		return updateRequest;
	}

	public String toString() {
		return message + '|' + host + '|' + port + '|' + taskName + '|' + version;
	}

	public String message;
	public String host;
	public int port;
	public String taskName;
	public String version;

}
