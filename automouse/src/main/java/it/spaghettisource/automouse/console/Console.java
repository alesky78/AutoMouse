package it.spaghettisource.automouse.console;

import it.spaghettisource.automouse.agent.DataBug;
import it.spaghettisource.automouse.utils.Configuration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import it.spaghettisource.automouse.utils.TimeSlot;

/**
 * Console class provides a command-line interface for managing the AutoMouse agent.
 * It allows the user to pause, stop, and configure timing and movement parameters interactively.
 *
 * @author alesky
 */
public class Console implements Runnable {

    /**
     * Menu option for pause management.
     */
    private static final int PAUSE_MANAGEMENT = 0;
    /**
     * Menu option for stop management.
     */
    private static final int STOP_MANAGEMENT = 1;
    /**
     * Menu option for time management.
     */
    private static final int TIME_MANAGEMENT = 2;
    /**
     * Menu option for move management.
     */
    private static final int MOVE_MANAGEMENT = 3;
    /**
     * Menu option for multy-stop management.
     */
    private static final int MULTYSTOP_MANAGEMENT = 4;

    /**
     * Current menu selection.
     */
    private int selection = -1;

    /**
     * DataBug instance for agent configuration.
     */
    private DataBug dataBug;
    /**
     * Command line manager for input/output.
     */
    private CmdLineManager cl;

    /**
     * Constructs a Console with the specified DataBug instance.
     *
     * @param dataBug the DataBug instance to manage
     */
    public Console(DataBug dataBug){
        this.dataBug = dataBug;
        cl = new CmdLineManager();
        cl.init();
    }

    /**
     * Main execution loop for the console interface. Displays menu and processes user input.
     */
    public void run() {

        cl.writeLine("");
        cl.writeLine("                    __");
        cl.writeLine("     _____   __ ___/  |_  ____   _____ _____");
        cl.writeLine("     \\__  \\ |  |  \\   __\\/  _ \\ /     \\\\__  \\");
        cl.writeLine("      / __ \\|  |  /|  | (  <_> )  Y Y  \\/ __ \\_");
        cl.writeLine("     (____  /____/ |__|  \\____/|__|_|  (____  /");
        cl.writeLine("          \\/                         \\/     \\/");
        cl.writeLine("");

        while (!dataBug.isStop()) {

            writeMenu();

            if(readMenuSelection()){
                manageSelection();
            }

        }

        cl.writeLine("request to stop application");

    }

    /**
     * Writes the main menu to the console.
     */
    private void writeMenu(){
        cl.writeLine("");
        cl.writeLine(PAUSE_MANAGEMENT+" --> pause management");
        cl.writeLine(STOP_MANAGEMENT+" --> stop  management");
        cl.writeLine(TIME_MANAGEMENT+" --> time  management");
        cl.writeLine(MOVE_MANAGEMENT+" --> move  management");
		cl.writeLine(MULTYSTOP_MANAGEMENT+" --> multi stop management");

	}

    /**
     * Reads the user's menu selection from the console.
     *
     * @return true if a valid selection was made, false otherwise
     */
	private boolean readMenuSelection(){
		try {
			selection = Integer.parseInt(cl.readLine());
			return true;
		} catch (Exception e) {
			selection = -1;
			cl.writeLine("invalid selection");
			return false;
		}
	}


    /**
     * Processes the user's menu selection and performs the corresponding action.
     */
	private void manageSelection() {
		if(selection == STOP_MANAGEMENT){
			dataBug.setStop(cl.requestConfirmation("do you want to stop the automat"));
		}

		if(selection == PAUSE_MANAGEMENT){
			dataBug.setPauseManual(cl.requestConfirmation("do you want to pause the automat"));
		}

		if(selection == TIME_MANAGEMENT){
			try{
				cl.writeLine("set the timer of the automa in seconds [between "+Configuration.getMinSleepTime()/1000+"s and "+Configuration.getMaxSleepTime()/1000+"s]");
				int time = Integer.parseInt(cl.readLine());
				if(time < (Configuration.getMinSleepTime()/1000) || time > (Configuration.getMaxSleepTime()/1000)){
					cl.writeLine("out of range, values is not changed");
				}else{
					dataBug.setSleepTimeMilliseconds(time*1000); //set in milliseconds
				}
			} catch (Exception e) {
				cl.writeLine("invalid selection, values is not changed");
			}
		}
		
		if(selection == MOVE_MANAGEMENT){
			try{
				cl.writeLine("set the amount of pixel to move the cursor [between "+Configuration.getMinPixelMove()+" pixel and "+Configuration.getMaxPixelMove()+" pixel]");
				int pixelToMove = Integer.parseInt(cl.readLine());
				if(pixelToMove < Configuration.getMinPixelMove() || pixelToMove > Configuration.getMaxPixelMove()){
					cl.writeLine("out of range, values is not changed");
				}else{
					dataBug.setPixelToMove(pixelToMove);
				}
			} catch (Exception e) {
				cl.writeLine("invalid selection, values is not changed");
			}
		}		

		if(selection == MULTYSTOP_MANAGEMENT){
            manageMultiStop();
        }

	}

    /**
     * Sub-menu for multy-stop slot management (list/add/remove/clear).
     */
    private void manageMultiStop() {
        while (true) {
            cl.writeLine("");
            cl.writeLine("multi stop management:");
            cl.writeLine("0 -> list slots");
            cl.writeLine("1 -> add slot");
            cl.writeLine("2 -> remove slot by index");
            cl.writeLine("3 -> clear slots");
            cl.writeLine("9 -> back");
            cl.writePrompt();
            String input = cl.readLine();
            if (input == null) return;
            switch (input.trim()) {
                case "0":
                    listSlots();
                    break;
                case "1":
                    addSlot();
                    break;
                case "2":
                    removeSlot();
                    break;
                case "3":
                    clearSlots();
                    break;
                case "9":
                    return;
                default:
                    cl.writeLine("invalid selection");
            }
        }
    }

    private void listSlots() {
        List<TimeSlot> slots = dataBug.getAutoStopSlots();
        if (slots.isEmpty()) {
            cl.writeLine("no slots configured");
        } else {
            cl.writeLine("current slots:");
            int idx = 0;
            for (TimeSlot slot : slots) {
                cl.writeLine(idx + ": " + slot.toString());
                idx++;
            }
        }
    }
    private void addSlot() {
        try {
            cl.writeLine("Enter slot start time (HH:MM):");
            String startStr = cl.readLine();
            cl.writeLine("Enter slot end time (HH:MM):");
            String endStr = cl.readLine();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime start = LocalTime.parse(startStr.trim(), fmt);
            LocalTime end = LocalTime.parse(endStr.trim(), fmt);
            if (!start.isBefore(end)) {
                cl.writeLine("invalid slot, start must be before end");
                return;
            }
            TimeSlot newSlot = new TimeSlot(start, end);
            List<TimeSlot> slots = new ArrayList<>(dataBug.getAutoStopSlots());
            for (TimeSlot slot : slots) {
                if (overlaps(newSlot, slot)) {
                    cl.writeLine("invalid slot, overlaps an existing slot");
                    return;
                }
            }
            slots.add(newSlot);
            slots.sort(null);
            dataBug.setAutoStopSlots(slots);
            cl.writeLine("slot added: " + newSlot);
        } catch (Exception e) {
            cl.writeLine("invalid input, slot not added");
        }
    }
    private void removeSlot() {
        List<TimeSlot> slots = new ArrayList<>(dataBug.getAutoStopSlots());
        if (slots.isEmpty()) {
            cl.writeLine("no slots to remove");
            return;
        }
        listSlots();
        cl.writeLine("Enter index to remove:");
        try {
            int idx = Integer.parseInt(cl.readLine().trim());
            if (idx < 0 || idx >= slots.size()) {
                cl.writeLine("invalid index");
                return;
            }
            TimeSlot removed = slots.remove(idx);
            dataBug.setAutoStopSlots(slots);
            cl.writeLine("removed slot: " + removed);
        } catch (Exception e) {
            cl.writeLine("invalid input, no slot removed");
        }
    }
    private void clearSlots() {
        dataBug.setAutoStopSlots(new ArrayList<>());
        cl.writeLine("all slots cleared");
    }
    /**
     * Returns true if two slots overlap (slot [start, end)).
     */
    private boolean overlaps(TimeSlot a, TimeSlot b) {
        return a.getStart().isBefore(b.getEnd()) && b.getStart().isBefore(a.getEnd());
    }

}
