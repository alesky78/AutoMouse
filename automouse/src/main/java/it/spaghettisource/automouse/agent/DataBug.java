package it.spaghettisource.automouse.agent;

import java.io.Serializable;

public class DataBug implements Serializable{

	private static final long serialVersionUID = -5927015819875117637L;

	public DataBug(int defaultSleepTime,int defaultPixelToMove) {
		super();
		stop = false;
		pause = false;
		sleepTime = defaultSleepTime;
		pixelToMove = defaultPixelToMove;
	}

	private boolean stop;
	private boolean pause;
	private int sleepTime;
	private int pixelToMove;	


	public boolean isStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public boolean isPause() {
		return pause;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public int getSleepTimeMilliseconds() {
		return sleepTime;
	}

	public int getSleepTimeSeconds() {
		return sleepTime/1000;
	}

	public void setSleepTimeMilliseconds(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public int getPixelToMove() {
		return pixelToMove;
	}
	
	public void setPixelToMove(int pixelToMove) {
		this.pixelToMove = pixelToMove;
	}
	
	


}
