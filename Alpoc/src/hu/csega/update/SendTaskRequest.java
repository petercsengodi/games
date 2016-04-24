package hu.csega.update;

import hu.csega.alpoc.Alpoc;
import hu.csega.net.ConstantsHello;
import hu.csega.net.SayHelloBr;

public class SendTaskRequest {

	public static void main(String[] args) throws Exception {
		UpdateRequest request = new UpdateRequest();
		request.message = "run";
		request.host = "*";
		request.port = ConstantsHello.TCP_PORT;
		request.taskName = "alpoc";
		request.version = Alpoc.VERSION;

		SayHelloBr.sendNotification(request.toString());
	}

}
