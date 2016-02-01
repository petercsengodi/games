package hu.csega.dyn;

import java.nio.charset.Charset;

public class DynamicFramework {

	public static final int EXIT_CODE = 0;
	public static final int LAST_UNACCEPTED_CODE = 200;
	public static final int UPGRADE_PROCESS = LAST_UNACCEPTED_CODE + 1;
	public static final int ENGINE_PROCESS = LAST_UNACCEPTED_CODE + 2;

	public static final int UDP_BROADCAST_PORT = 1080;
	public static final int UDP_MESSAGE_PORT = 1081;
	public static final int TCP_FLOW_PORT = 1082;
	
	public static final Charset CHARSET = Charset.forName("UTF-8");
}
