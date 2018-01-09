package it.spaghettisource.automouse.agent;

public class DataBug {

	public DataBug(int defaultSleepTime) {
		super();
		this.stop = false;
		this.pause = false;
		this.sleepTime = defaultSleepTime;
	}

	private boolean stop;
	private boolean pause;
	private int sleepTime;


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


}