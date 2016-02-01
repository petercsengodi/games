package hu.csega.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SayHello {

	public static void main(String[] args) throws Exception {
	    String hostname= "localhost";
	    int port=ConstantsHello.PORT;
	    InetAddress host;
	    DatagramSocket socket;
	    DatagramPacket packet;	
	    
	    
        try
        {
            host = InetAddress.getByName(hostname);
            socket = new DatagramSocket (null);
            byte[] bytes = "hello".getBytes(ConstantsHello.CHARSET);
			packet=new DatagramPacket (bytes, bytes.length, host, port);
            socket.send (packet);
            // packet.setLength(100);
            // socket.receive (packet);
            socket.close ();
//            byte[] data = packet.getData ();
//            String time=new String(data);  // convert byte array data into string
//            System.out.println(time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}

}
