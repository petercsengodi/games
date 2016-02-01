package hu.csega.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import hu.csega.dyn.DynamicFramework;

public class CheckForUpdates {

	public void sendOutBroadcastQuery(String message) throws Exception {
		byte[] sendData = message.getBytes(ConstantsHello.CHARSET);

		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setBroadcast(true);
			
			{
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length,
						InetAddress.getByName("255.255.255.255"),
						DynamicFramework.UDP_BROADCAST_PORT);

				socket.send(sendPacket);
				System.out.println(LOG_PREFIX + "Request packet sent to: 255.255.255.255 (DEFAULT)");
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
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, DynamicFramework.UDP_BROADCAST_PORT);
						socket.send(sendPacket);
					} catch (Exception e) {
					}

					System.out.println(LOG_PREFIX + "Request packet sent to: "
							+ broadcast.getHostAddress() + "; Interface: "
							+ networkInterface.getDisplayName());
				}
			}

			System.out.println(LOG_PREFIX + "Done looping over all network interfaces. Now waiting for a reply!");
		} // end try socket
	}
	
	public static List<MessageFromServer> waitForResponses(int timeInMillis) throws Exception {
		List<MessageFromServer> ret = new ArrayList<>();
		
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setBroadcast(true);
			socket.setSoTimeout(timeInMillis);
			
			while(true) {
				byte[] recvBuf = new byte[15000];
				DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
				
				try {
					socket.receive(receivePacket);
				} catch(SocketTimeoutException ex) {
					return ret;
				}
				
				MessageFromServer m = new MessageFromServer();
				m.server = receivePacket.getAddress();
				m.message = new String(receivePacket.getData(), DynamicFramework.CHARSET).trim();
				ret.add(m);

				System.out.println(LOG_PREFIX + "Broadcast response from server: " + m.server.getHostAddress());
				System.out.println(LOG_PREFIX + "message: " + m.message);
			} // end while
			
		} // end try socket
	}

	private static final String LOG_PREFIX = "UPGRADE: ";
}
