package it.spaghettisource.automouse.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuration {

	static Log log = LogFactory.getLog(Configuration.class.getName());
		
	private static final String CONFIG_FILE_NAME = "/config.properties";

	//keys list
	private static final String MIN_SLEEP_TIME = "agent.slepptime.min";
	private static final String MAX_SLEEP_TIME = "agent.slepptime.max";
	private static final String DEFAULT_SLEEP_TIME = "agent.slepptime.default";
	private static final String MIN_PIXEL_MOVE = "agent.pixel.min";	
	private static final String MAX_PIXEL_MOVE = "agent.pixel.max";
	private static final String DEFAULT_PIXEL_MOVE = "agent.pixel.default";	

	private static Properties prop = new Properties();
	
	private Configuration(){
	}
	
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

	public static int getDefaultPixelMove(){
		return getIntValue(DEFAULT_PIXEL_MOVE);
	}

	public static int getMaxPixelMove(){
		return getIntValue(MAX_PIXEL_MOVE);
	}
	
	public static int getMinPixelMove(){
		return getIntValue(MIN_PIXEL_MOVE);
	}	
	
	private static int getIntValue(String key){
		return Integer.valueOf(prop.getProperty(key));
	}
	
}
