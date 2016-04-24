package hu.csega.net;

import java.net.ServerSocket;
import java.net.Socket;

public class DownloadUpdateServer {

	public static void main(String args[]) throws Exception {
		System.out.println("Starting TCP/IP server.");

		try(ServerSocket welcomeSocket = new ServerSocket(ConstantsHello.TCP_PORT)) {

			while (true) {
				System.out.println("Waiting for requests.");
				Socket connectionSocket = welcomeSocket.accept();
				new DownloadUpdateThread(connectionSocket).run();
			}
		}
	}
}
