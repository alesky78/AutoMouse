package it.spaghettisource.automouse;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.automouse.agent.Agent;
import it.spaghettisource.automouse.agent.DataBug;
import it.spaghettisource.automouse.console.Console;
import it.spaghettisource.automouse.swing.Swing;
import it.spaghettisource.automouse.utils.Configuration;
import it.spaghettisource.automouse.utils.ImageIconFactory;

/**
 * Main class to start the AutoMouse application. Handles initialization, configuration,
 * and selection of the user interface mode (console or Swing GUI).
 * <p>
 * Usage: java -jar automouse.jar [console|swing]
 * </p>
 *
 * <ul>
 *   <li>console - starts the application in command-line mode</li>
 *   <li>swing   - starts the application with a graphical user interface</li>
 * </ul>
 *
 * The application initializes configuration, sets up the agent, and starts the selected UI.
 *
 * @author alesky
 */
public class Application {

	static Log log = LogFactory.getLog(Application.class.getName());

    /**
     * Shared DataBug instance used by the agent and UI components.
     */
    private static DataBug dataBug;

    /**
     * Main entry point for the AutoMouse application.
     *
     * @param args Command-line arguments. The first argument must be either "console" or "swing".
     */
    public static void main(String[] args) {
	
		//configure the application
    	Configuration.init();
		dataBug = new DataBug(Configuration.getDefaultSleepTime(), Configuration.getDefaultPixelMove(), Configuration.getAutoStopSlots());
		
		//prepare the agent
		Agent agent = new Agent(dataBug);
		Thread thread = new Thread(agent);

		
		if(args.length == 0 ){
			log.fatal("start type not defined");			
			log.fatal("set [console|swing] in the command line to define how to start the application");
			System.exit(-1);
		}
		
		//start by console
		if(args[0].equals("console") ){
			
			log.info("start by console");		
			startCommandLine(dataBug);
			
		} 
		
		//start by swing
		if(args[0].equals("swing") ){
			
			log.info("start by swing");		
			/* Turn off metal's use of bold fonts */
	        UIManager.put("swing.boldMetal", Boolean.FALSE);

	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI(dataBug);
	            }
	        });
			
		}		
		
		//start the agent
		thread.start();
		log.info("application started succesfully");
		
	}
	
	
	/**
	 * Starts the command-line interface for the application.
	 *
	 * @param dataBug The DataBug instance to be used by the console.
	 */
	private static void startCommandLine(DataBug dataBug){
		//start the console
		Console console = new Console(dataBug);
		Thread thc = new Thread(console);
		thc.start();
	}
	
	
	/**
	 * Creates and displays the Swing GUI for the application.
	 *
	 * @param dataBug The DataBug instance to be used by the Swing UI.
	 */
    private static void createAndShowGUI(DataBug dataBug) {
    	log.debug("create frame");

    	//Create and set up the window.
        JFrame frame = new JFrame("JAutom");
		frame.setIconImage(ImageIconFactory.getAppImage());        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Swing animator = new Swing(dataBug);

        //Add content to the window and pack it
        frame.add(animator, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);

        //Centre the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        log.debug("center the frame");
        log.debug("monitor width:"+dim.width+" height:"+dim.height);
        log.debug("frame width:"+frame.getSize().width+" height:"+frame.getSize().height);


        //Display the window.
        frame.setVisible(true);
    }
	
}
