package it.spaghettisource.automouse.agent;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.automouse.console.CmdLineManager;

/**
 * Agent
 * 
 * @author alesky
 *
 */
public class Agent implements Runnable {

	static Log log = LogFactory.getLog(CmdLineManager.class.getName());

	private DataBug dataBug;

	public Agent(DataBug dataBug){
		this.dataBug = dataBug;
	}


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

			while(!dataBug.isStop()){
				if(!dataBug.isPause()){
					
					mousePointer = MouseInfo.getPointerInfo();
					
					if(mousePointer!=null) {	//check the pointer because it is null in case of desktop not available or in lock mode
						mouseLocation = mousePointer.getLocation();
						pixelToMove = dataBug.getPixelToMove();

						x = flag? mouseLocation.x+pixelToMove : mouseLocation.x-pixelToMove;
						y = flag? mouseLocation.y+pixelToMove : mouseLocation.y-pixelToMove;

						flag = !flag;

						robot.mouseMove(x, y);
						log.info("automa move mouse event x:"+x+" y:"+y+" time "+ new Date(System.currentTimeMillis()));						
					}else {
						log.info("mouse not available (not desktop loaded or pc in lock mode)"+ new Date(System.currentTimeMillis()));
					}

				}else{
					log.info("automa is in pause mode");
				}

				log.info("automa sleep for :"+dataBug.getSleepTimeSeconds()+" seconds");
				Thread.sleep(dataBug.getSleepTimeMilliseconds());

			}

		} catch (Exception e) {
			log.error("automa goes in error",e);
		}


	}

}
