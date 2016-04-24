package hu.csega.net;

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
				String clientSentence = line;

				switch(clientSentence) {
				case "download-task":

					{
						String taskName = inFromClient.readLine();
						String version = inFromClient.readLine();
						String jarname = taskName + '/' + version + '/' + taskName + ".jar";
						String fn = ConstantsHello.STORAGE_DIRECTORY + '/' + jarname;

						File f = new File(fn);
						if(!f.exists()) {
							String msg = "error – file missing: " + jarname;
							System.out.println(msg);
							outToClient.writeBytes(msg + "\n");
							break;
						}

						outToClient.writeBytes("file: " + jarname + "\n");
						outToClient.writeBytes("size: " + f.length() + "\n");

						Path path = Paths.get(f.getPath());
						byte[] data = Files.readAllBytes(path);
						String encoded = Base64.getEncoder().encodeToString(data);
						outToClient.writeBytes(encoded);
						outToClient.flush();
					}

					break;
				default:
					String msg = "error – no such request command: " + clientSentence;
					System.out.println(msg);
					outToClient.writeBytes(msg + "\n");
					outToClient.flush();
					break;
				}
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private Socket connectionSocket;
}
