package it.spaghettisource.automouse.agent;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.automouse.utils.TimeSlot;

/**
 * The Agent class is responsible for periodically moving the mouse cursor to prevent the system from becoming idle.
 * It uses the DataBug instance to determine movement parameters and state (pause/stop).
 * The agent alternates the mouse position by a configurable number of pixels at a configurable interval.
 *
 * @author alesky
 */
public class Agent implements Runnable {

    /**
     * Logger for the Agent class.
     */
    static Log log = LogFactory.getLog(Agent.class.getName());

    /**
     * DataBug instance containing movement and timing configuration.
     */
    private DataBug dataBug;

    /**
     * Constructs an Agent with the specified DataBug configuration.
     *
     * @param dataBug the DataBug instance to use for configuration and state
     */
    public Agent(DataBug dataBug){
        this.dataBug = dataBug;
    }

    /**
     * Main execution loop for the agent. Moves the mouse cursor at intervals unless paused or stopped.
     */
    @Override
    public void run() {

        boolean flag = true;

        try {

            Robot robot;

            robot = new Robot();

            int x = 0;
            int y = 0;
            int pixelToMove = 0;
            Point mouseLocation;
            PointerInfo mousePointer;

            while (!dataBug.isStop()) {

                // Multi-stop: add auto pause automatic based on time slots
                LocalTime now = LocalTime.now();
                boolean inSlot = isNowInAnySlot(now, dataBug.getAutoStopSlots());
                dataBug.setPauseAuto(inSlot);

                if (!dataBug.isPause()){

                    mousePointer = MouseInfo.getPointerInfo();

                    if(mousePointer!=null) {	//check the pointer because it is null in case of desktop not available or in lock mode
                        mouseLocation = mousePointer.getLocation();
                        pixelToMove = dataBug.getPixelToMove();

                        x = flag? mouseLocation.x+pixelToMove : mouseLocation.x-pixelToMove;
                        y = flag? mouseLocation.y+pixelToMove : mouseLocation.y-pixelToMove;

                        flag = !flag;

                        robot.mouseMove(x, y);
                        log.debug("agent move mouse event x:"+x+" y:"+y+" time "+ new Date(System.currentTimeMillis()));
                    }else {
                        log.debug("agent not available (not desktop loaded or pc in lock mode)"+ new Date(System.currentTimeMillis()));
                    }

                }else{
                    log.debug("automa is in pause mode");
                }

                log.debug("automa sleep for :"+dataBug.getSleepTimeSeconds()+" seconds");
                Thread.sleep(dataBug.getSleepTimeMilliseconds());

            }

        } catch (Exception e) {
            log.error("automa goes in error",e);
        }


    }

    /**
     * Returns true if now is within any of the provided slots.
     */
    private boolean isNowInAnySlot(LocalTime now, List<TimeSlot> slots) {
        for (TimeSlot slot : slots) {
            if (slot.contains(now)) {
                return true;
            }
        }
        return false;
    }
}
