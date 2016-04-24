package hu.csega.update;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import hu.csega.net.ConstantsHello;
import hu.csega.net.ReceiveHelloBr;

public class WaitForTaskAndRunIt extends Thread {

	public static void main(String[] args) throws Exception {

		for(;;) {
			UpdateRequest updateRequest = listenForTaskToStart();
			if(updateRequest == null)
				continue;

			WaitForTaskAndRunIt processUpdateThread = new WaitForTaskAndRunIt();
			processUpdateThread.updateRequest = updateRequest;
			processUpdateThread.run();
		}

	}

	private static UpdateRequest listenForTaskToStart() {
		try {
			String content = ReceiveHelloBr.waitForBroadcastPacket();
			if(content == null)
				return null;

			return UpdateRequest.fromString(content);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void run() {
		System.out.println("Processing message: " + updateRequest.message);
		System.out.println("Socket host: " + updateRequest.host + ", port: " + updateRequest.port);

		try (Socket clientSocket = new Socket(updateRequest.host, updateRequest.port)) {

			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// TODO: check if we already have that file in our storage

			DownloadRequest downloadRequest = DownloadRequest.fromUpdateRequest(updateRequest);
			outToServer.writeBytes(downloadRequest.toString() + '\n');
			outToServer.flush();

			String message = inFromServer.readLine();
			if(message.startsWith("file")) {
				FileResponse file = FileResponse.fromString(message);
				System.out.println("File name: " + file.name);
				System.out.println("File size: " + file.size);
				byte[] data = Base64.getDecoder().decode(file.content);

				String dir = ConstantsHello.STORAGE_DIRECTORY + '/' + file.name;
				int index = dir.lastIndexOf('/');
				dir = dir.substring(0, index);
				new File(dir).mkdirs();
				
				Path path = Paths.get(ConstantsHello.STORAGE_DIRECTORY + '/' + file.name);
				Files.write(path, data);

				System.out.println("File written.");
			} else {
				System.err.println(message);
			}


		} catch(Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Processing thread finished.");
	}

	private UpdateRequest updateRequest;
}
