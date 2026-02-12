package it.spaghettisource.automouse.agent;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import it.spaghettisource.automouse.utils.TimeSlot;

/**
 * DataBug is a data container class that holds the state and configuration for the AutoMouse agent.
 * It stores flags for stopping and pausing the agent, as well as timing and movement parameters.
 * Implements Serializable for possible future persistence or transfer.
 *
 * @author alesky
 */
public class DataBug implements Serializable{

    private static final long serialVersionUID = -5927015819875117637L;

    /**
     * Indicates whether the agent should stop execution.
     */
    private boolean stop;
    /**
     * Indicates whether the agent is paused.
     */
    private volatile boolean pauseManual;
    private volatile boolean pauseAuto;
    private List<TimeSlot> autoStopSlots;
    /**
     * The sleep time in milliseconds between mouse movements.
     */
    private int sleepTime;
    /**
     * The number of pixels to move the mouse cursor.
     */
    private int pixelToMove;

    /**
     * Constructs a DataBug instance with default sleep time and pixel movement.
     *
     * @param defaultSleepTime the default sleep time in milliseconds
     * @param defaultPixelToMove the default number of pixels to move
     */
    public DataBug(int defaultSleepTime,int defaultPixelToMove, List<TimeSlot> autoStopSlots) {
        super();
        stop = false;
        this.pauseManual = false;
        this.pauseAuto = false;
        this.autoStopSlots = autoStopSlots;
        sleepTime = defaultSleepTime;
        pixelToMove = defaultPixelToMove;
    }

    /**
     * Returns whether the agent should stop execution.
     *
     * @return true if the agent should stop, false otherwise
     */
    public boolean isStop() {
        return stop;
    }
    /**
     * Sets the stop flag for the agent.
     *
     * @param stop true to stop the agent, false to continue
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * Returns whether the agent is paused (manual or auto).
     * @return true if paused (manual or auto), false otherwise
     */
    public boolean isPause() {
        return pauseManual || pauseAuto;
    }
    /**
     * Sets the manual pause flag for the agent.
     * @param pause true to pause manually, false to resume
     */
    public void setPauseManual(boolean pause) {
        this.pauseManual = pause;
    }
    /**
     * Returns whether the agent is paused manually.
     */
    public boolean isPauseManual() {
        return pauseManual;
    }
    /**
     * Returns whether the agent is paused automatically (by scheduler).
     */
    public boolean isPauseAuto() {
        return pauseAuto;
    }
    /**
     * Sets the automatic pause flag (by scheduler).
     */
    public void setPauseAuto(boolean pauseAuto) {
        this.pauseAuto = pauseAuto;
    }
    /**
     * Gets the current list of multy-stop slots.
     */
    public List<TimeSlot> getAutoStopSlots() {
        return autoStopSlots;
    }
    /**
     * Sets the list of multy-stop slots (defensive copy, unmodifiable).
     */
    public void setAutoStopSlots(List<TimeSlot> slots) {
        this.autoStopSlots = Collections.unmodifiableList(new ArrayList<>(slots));
    }

    /**
     * Gets the sleep time in milliseconds between mouse movements.
     *
     * @return the sleep time in milliseconds
     */
    public int getSleepTimeMilliseconds() {
        return sleepTime;
    }
    /**
     * Gets the sleep time in seconds between mouse movements.
     *
     * @return the sleep time in seconds
     */
    public int getSleepTimeSeconds() {
        return sleepTime/1000;
    }

    /**
     * Sets the sleep time in milliseconds between mouse movements.
     *
     * @param sleepTime the sleep time in milliseconds
     */
    public void setSleepTimeMilliseconds(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    /**
     * Gets the number of pixels to move the mouse cursor.
     *
     * @return the number of pixels to move
     */
    public int getPixelToMove() {
        return pixelToMove;
    }
    /**
     * Sets the number of pixels to move the mouse cursor.
     *
     * @param pixelToMove the number of pixels to move
     */
    public void setPixelToMove(int pixelToMove) {
        this.pixelToMove = pixelToMove;
    }

}
