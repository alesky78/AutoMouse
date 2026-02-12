package it.spaghettisource.automouse.utils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Configuration class loads and provides access to application configuration properties.
 * It reads values from the config.properties file and exposes them as static methods.
 *
 * @author alesky
 */
public class Configuration {

    /**
     * Logger for the Configuration class.
     */
    static Log log = LogFactory.getLog(Configuration.class.getName());

    /**
     * Name of the configuration file.
     */
    private static final String CONFIG_FILE_NAME = "/config.properties";

    //keys list
    private static final String MIN_SLEEP_TIME = "agent.slepptime.min";
    private static final String MAX_SLEEP_TIME = "agent.slepptime.max";
    private static final String DEFAULT_SLEEP_TIME = "agent.slepptime.default";
    private static final String MIN_PIXEL_MOVE = "agent.pixel.min";
    private static final String MAX_PIXEL_MOVE = "agent.pixel.max";
    private static final String DEFAULT_PIXEL_MOVE = "agent.pixel.default";
    private static final String AUTO_STOP_SLOTS = "agent.autostop.slots";

    /**
     * Properties object holding configuration values.
     */
    private static Properties prop = new Properties();

    /**
     * Private constructor to prevent instantiation.
     */
    private Configuration(){
    }

    /**
     * Initializes the configuration by loading properties from the configuration file.
     * Throws a RuntimeException if loading fails.
     */
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

    /**
     * Gets the default sleep time in milliseconds for the agent.
     *
     * @return the default sleep time in milliseconds
     */
    public static int getDefaultSleepTime(){
        return getIntValue(DEFAULT_SLEEP_TIME);
    }

    /**
     * Gets the maximum sleep time in milliseconds for the agent.
     *
     * @return the maximum sleep time in milliseconds
     */
    public static int getMaxSleepTime(){
        return getIntValue(MAX_SLEEP_TIME);
    }

    /**
     * Gets the minimum sleep time in milliseconds for the agent.
     *
     * @return the minimum sleep time in milliseconds
     */
    public static int getMinSleepTime(){
        return getIntValue(MIN_SLEEP_TIME);
    }

    /**
     * Gets the default number of pixels to move the mouse.
     *
     * @return the default pixel movement
     */
    public static int getDefaultPixelMove(){
        return getIntValue(DEFAULT_PIXEL_MOVE);
    }

    /**
     * Gets the maximum number of pixels to move the mouse.
     *
     * @return the maximum pixel movement
     */
    public static int getMaxPixelMove(){
        return getIntValue(MAX_PIXEL_MOVE);
    }

    /**
     * Gets the minimum number of pixels to move the mouse.
     *
     * @return the minimum pixel movement
     */
    public static int getMinPixelMove(){
        return getIntValue(MIN_PIXEL_MOVE);
    }

    /**
     * Returns the list of auto stop slots as configured in the properties file.
     * Format: agent.autostop.slots=HH:MM-HH:MM,HH:MM-HH:MM
     */
    public static List<TimeSlot> getAutoStopSlots() {
        String raw = prop.getProperty(AUTO_STOP_SLOTS);
        if (raw == null || raw.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<TimeSlot> slots = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        String[] tokens = raw.split(",");
        for (String token : tokens) {
            String trimmed = token.trim();
            String[] parts = trimmed.split("-");
            LocalTime start = LocalTime.parse(parts[0].trim(), fmt);
            LocalTime end = LocalTime.parse(parts[1].trim(), fmt);
            slots.add(new TimeSlot(start, end));
        }
        return slots;
    }

    /**
     * Retrieves an integer value from the loaded properties by key.
     *
     * @param key the property key
     * @return the integer value for the given key
     */
    private static int getIntValue(String key){ return Integer.valueOf(prop.getProperty(key)); }

}
