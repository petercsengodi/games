package hu.csega.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class SayHelloBr {

	public static void main(String[] args) throws Exception {
		sendNotification("hello");
	}

	public static void sendNotification(String message) throws Exception {
		byte[] sendData = message.getBytes(ConstantsHello.CHARSET);

		try {
			try (DatagramSocket socket = new DatagramSocket()) {
				socket.setBroadcast(true);

				{
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length,
							InetAddress.getByName("255.255.255.255"),
							ConstantsHello.BROADCAST_PORT);

					socket.send(sendPacket);
					System.out.println(">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
				}

				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) {
					NetworkInterface networkInterface = interfaces.nextElement();

					if (networkInterface.isLoopback() || !networkInterface.isUp()) {
						continue; // Don't want to broadcast to the loopback interface
					}

					for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
						InetAddress broadcast = interfaceAddress.getBroadcast();
						if (broadcast == null) {
							continue;
						}

						try {
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
							socket.send(sendPacket);
						} catch (Exception e) {
						}

						System.out.println(">>> Request packet sent to: "
								+ broadcast.getHostAddress() + "; Interface: "
								+ networkInterface.getDisplayName());
					}
				}

				System.out.println(">>> Done looping over all network interfaces. Now waiting for a reply!");

				byte[] recvBuf = new byte[15000];
				DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(receivePacket);

				System.out.println(">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

				String response = new String(receivePacket.getData()).trim();
				System.out.println(">>> respopnse message: " + response);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
