package hu.csega.update;

import java.util.StringTokenizer;

public class DownloadRequest {

	public static DownloadRequest fromString(String content) {
		StringTokenizer tokenizer = new StringTokenizer(content, "|");

		DownloadRequest downloadRequest = new DownloadRequest();
		downloadRequest.message = tokenizer.nextToken().trim();
		downloadRequest.taskName = tokenizer.nextToken().trim();
		downloadRequest.version = tokenizer.nextToken().trim();

		return downloadRequest;
	}

	public String toString() {
		return message + '|' + taskName + '|' + version;
	}

	public static DownloadRequest fromUpdateRequest(UpdateRequest updateRequest) {
		DownloadRequest downloadRequest = new DownloadRequest();
		downloadRequest.message = "download-task";
		downloadRequest.taskName = updateRequest.taskName;
		downloadRequest.version = updateRequest.version;
		return downloadRequest;
	}

	public String message;
	public String taskName;
	public String version;
}
