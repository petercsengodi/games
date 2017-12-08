package hu.csega.games.library.network;

public class NetworkOptions {

	public static int PacketLength() {
		return 4000;
	}

	public static int ReceiveLength() {
		return 100000;
	}

	public static int TimeVal() {
		return 300;
	}

	public static int MaxLimit() {
		return 15;
	}

	public static int UdpRate() {
		return 10;
	}

	public static int GenerateHostPort(int main_port, int hostID) {
		return main_port + (hostID + 1) * (MaxLimit() + 1);
	}

	public static int GenerateHostPlayerPort(int main_port, int hostID, int playerID) {
		return GenerateHostPort(main_port, hostID) + playerID + 1;
	}

	public static int GeneratePlayerPort(int host_port, int playerID) {
		return host_port + playerID + 1;
	}
}