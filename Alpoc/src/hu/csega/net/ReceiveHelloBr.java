package hu.csega.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceiveHelloBr {

	public static void main(String[] args) throws Exception {
		// Create a socket to listen on the port.
		try (DatagramSocket socket = new DatagramSocket(ConstantsHello.PORT,
				InetAddress.getByName("0.0.0.0"))) {
			socket.setBroadcast(true);

			System.out.println(">>>Ready to receive broadcast packets!");
			byte[] recvBuf = new byte[15000];
			DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
			socket.receive(packet);

			System.out.println(">>>Discovery packet received from: "
					+ packet.getAddress().getHostAddress());
			System.out.println(">>>Packet received; data: "
					+ new String(packet.getData()));

			String message = new String(packet.getData()).trim();
			if (message.equals("hello")) {
				byte[] sendData = "ack".getBytes();

				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, packet.getAddress(), packet.getPort());
				socket.send(sendPacket);
				System.out.println(">>>Sent packet to: "
						+ sendPacket.getAddress().getHostAddress());
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
