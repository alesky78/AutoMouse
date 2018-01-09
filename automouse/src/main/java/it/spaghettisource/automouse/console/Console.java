package it.spaghettisource.automouse.console;

import it.spaghettisource.automouse.agent.DataBug;
import it.spaghettisource.automouse.utils.Configuration;

public class Console implements Runnable {

	private static final int PAUSE_MANAGEMENT = 0;
	private static final int STOP_MANAGEMENT = 1;
	private static final int TIME_MANAGEMENT = 2;

	private int selection = -1;

	private DataBug dataBug;
	private CmdLineManager cl;

	public Console(DataBug dataBug){
		this.dataBug = dataBug;
		cl = new CmdLineManager();
		cl.init();
	}

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

	private void writeMenu(){
		cl.writeLine("");
		cl.writeLine(PAUSE_MANAGEMENT+" --> pause management");
		cl.writeLine(STOP_MANAGEMENT+" --> stop  management");
		cl.writeLine(TIME_MANAGEMENT+" --> time  management");
	}

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


	private void manageSelection() {
		if(selection == STOP_MANAGEMENT){
			dataBug.setStop(cl.requestConfirmation("do you want to stop the automat"));
		}

		if(selection == PAUSE_MANAGEMENT){
			dataBug.setPause(cl.requestConfirmation("do you want to pause the automat"));
		}

		if(selection == TIME_MANAGEMENT){
			try{
				cl.writeLine("set the timer of the automa in seconds [between "+Configuration.getMinSleepTime()/1000+"s and "+Configuration.getMaxSleepTime()/1000+"s]");
				int time = Integer.parseInt(cl.readLine());
				if(time < (Configuration.getMinSleepTime()/1000) || time > (Configuration.getMaxSleepTime()/1000)){
					dataBug.setSleepTimeMilliseconds(Configuration.getDefaultSleepTime());
					cl.writeLine("out of range, set default value");
				}else{
					dataBug.setSleepTimeMilliseconds(time*1000); //set in milliseconds
				}
			} catch (Exception e) {
				dataBug.setSleepTimeMilliseconds(Configuration.getDefaultSleepTime());
				cl.writeLine("invalid selection, set default value");
			}
		}

	}

}
