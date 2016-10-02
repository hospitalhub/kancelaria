package pl.kalisz.szpital.kancelaria.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class PropertiesLoader.
 */
public class PropertiesLoader {

	/** The p. */
	private static Properties p = null;

	/**
	 * Load property.
	 * 
	 * @param name
	 *            the name
	 * @return the string
	 */
	public static final String loadProperty(String name) {
		if (p == null) {
			load();
		}
		return p.getProperty(name);
	}

	/**
	 * Load.
	 */
	private static final void load() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("wsz.properties");

		if (stream == null) {
			System.err.println("Props not loaded!");
		} else {
			p = new Properties();
			try {
				p.load(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Instantiates a new properties loader.
	 */
	private PropertiesLoader() {
		//
	}

}
