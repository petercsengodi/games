package hu.csega.dyn;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import org.scannotation.AnnotationDB;

public class DynamicFramework {

	public static final int EXIT_CODE = 0;
	public static final int LAST_UNACCEPTED_CODE = 200;
	public static final int UPGRADE_PROCESS = LAST_UNACCEPTED_CODE + 1;
	public static final int ENGINE_PROCESS = LAST_UNACCEPTED_CODE + 2;

	public static final int UDP_BROADCAST_PORT = 1080;
	public static final int UDP_MESSAGE_PORT = 1081;
	public static final int TCP_FLOW_PORT = 1082;
	
	public static final Charset CHARSET = Charset.forName("UTF-8");
	

	public static Set<String> search(URL url) throws Exception {
		AnnotationDB db = new AnnotationDB();
		db.scanArchives(url);
		Set<String> entityClasses =
		    db.getAnnotationIndex().get(Agent.class.getName());
		return entityClasses;
	}

	public static void main(String[] args) throws Exception {
		URL url = new URL("file:///tmp/tmp.jar");
		Set<String> set = search(url);
		
		String firstClass = null;
		if(set != null) {
			for(String str : set) {
				System.out.println("found: " + str);
				if(firstClass == null)
					firstClass = str;
			}
		}
		
		System.out.println("first: " + firstClass);
		
	    try (URLClassLoader classLoader = new URLClassLoader(new URL[]{url})) {
	        Class<?> aClass = classLoader.loadClass(firstClass);
	        Method method = aClass.getMethod("main", new Class<?>[]{ String[].class });
	        method.invoke(null, new Object[] { null });
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
