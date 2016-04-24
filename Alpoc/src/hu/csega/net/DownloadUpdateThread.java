package hu.csega.net;

import hu.csega.update.DownloadRequest;
import hu.csega.update.FileResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class DownloadUpdateThread extends Thread {

	public DownloadUpdateThread(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run() {
		try {

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			String line;
			while((line = inFromClient.readLine()) != null) {

				if(line.startsWith("download-task")) {
					DownloadRequest downloadRequest = DownloadRequest.fromString(line);
					String jarname = downloadRequest.taskName + '/' + downloadRequest.version +
							'/' + downloadRequest.taskName + ".jar";
					String fn = ConstantsHello.STORAGE_DIRECTORY + '/' + jarname;

					File f = new File(fn);
					if(!f.exists()) {
						String msg = "error – file missing: " + jarname;
						System.out.println(msg);
						outToClient.writeBytes(msg + "\n");
						break;
					}

					FileResponse response = new FileResponse();
					response.message = "file";
					response.name = jarname;
					response.size = f.length();

					Path path = Paths.get(f.getPath());
					byte[] data = Files.readAllBytes(path);
					response.content = Base64.getEncoder().encodeToString(data);

					outToClient.writeBytes(response.toString());
					outToClient.flush();

				} else {

					int index = line.indexOf('|');
					String command = (index < 0 ? line : line.substring(0, index));
					String msg = "error – no such request command: " + command;
					System.out.println(msg);
					outToClient.writeBytes(msg + "\n");
					outToClient.flush();

				}
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private Socket connectionSocket;
}
