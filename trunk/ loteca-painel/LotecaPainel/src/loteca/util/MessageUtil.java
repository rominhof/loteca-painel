package loteca.util;

import java.util.ResourceBundle;

public class MessageUtil {
	private ResourceBundle rb = ResourceBundle.getBundle("loteca");

	private static MessageUtil instance;

	private MessageUtil() {
		// Vazio
	}

	static {
		instance = new MessageUtil();
		
//		File file = new File("C:\\languages");  
//		URL[] urls = {file.toURI().toURL()};  
//		ClassLoader loader = new URLClassLoader(urls);  
//		ResourceBundle bundle = ResourceBundle.getBundle("english", Locale.getDefault(), loader);  
	}

	public static MessageUtil getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return rb.getString(key);
	}

	public static void main(String[] args) {
		System.out.println(ResourceBundle.getBundle("loteca")
				.getString("teste"));
	}
}
