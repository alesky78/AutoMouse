package it.spaghettisource.automouse.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuration {

	static Log log = LogFactory.getLog(Configuration.class.getName());
		
	private final static String CONFIG_FILE_NAME = "/config.properties";
	private static Properties prop = new Properties();

	//keys list
	private final static String MIN_SLEEP_TIME = "agent.slepptime.min";
	private final static String MAX_SLEEP_TIME = "agent.slepptime.max";
	private final static String DEFAULT_SLEEP_TIME = "agent.slepptime.default";
		
	public static void init(){
		prop = new Properties();
		try {
			prop.load(Configuration.class.getResourceAsStream(CONFIG_FILE_NAME));
		} catch (IOException e) {
			String message = "error loading the configuration "+CONFIG_FILE_NAME+" for:"+e.getMessage();
			log.error(message,e );
			throw new RuntimeException(message,e);
		}
	}
	
	public static int getDefaultSleepTime(){
		return getIntValue(DEFAULT_SLEEP_TIME);
	}

	public static int getMaxSleepTime(){
		return getIntValue(MAX_SLEEP_TIME);
	}
	
	public static int getMinSleepTime(){
		return getIntValue(MIN_SLEEP_TIME);
	}
	
	private static int getIntValue(String key){
		return Integer.valueOf(prop.getProperty(key));
	}
	
}
