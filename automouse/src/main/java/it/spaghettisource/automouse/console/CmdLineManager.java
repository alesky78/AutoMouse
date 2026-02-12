package it.spaghettisource.automouse.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CmdLineManager provides utility methods for reading from and writing to the command line.
 * It supports prompts, confirmation requests, and error handling for console I/O operations.
 *
 * @author alesky
 */
public class CmdLineManager {

    /**
     * Logger for the CmdLineManager class.
     */
    static Log log = LogFactory.getLog(CmdLineManager.class.getName());

    /**
     * BufferedReader for reading input from the console.
     */
    private BufferedReader in;
    /**
     * BufferedWriter for writing output to the console.
     */
    private BufferedWriter out;
    /**
     * The prompt string displayed to the user.
     */
    private String prompt;


    /**
     * Initializes the input and output streams and sets the default prompt.
     */
    public void init(){
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new BufferedWriter(new OutputStreamWriter(System.out));
		prompt = "#";
	}


    /**
     * Writes a string to the console without a newline.
     *
     * @param line the string to write
     */
	public void write(String line){
		try {
			out.write(line);
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}

    /**
     * Writes a string to the console followed by a newline.
     *
     * @param line the string to write
     */
	public void writeLine(String line){
		try {
			out.write(line+"\n");
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}

    /**
     * Writes the string representation of an object to the console followed by a newline.
     *
     * @param obj the object to write
     */
	public void writeLine(Object obj){
		writeLine(obj.toString());
	}


    /**
     * Writes a newline to the console.
     */
	public void writeEndOfLine(){
		try {
			out.write("\n");
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}


    /**
     * Writes the prompt string to the console.
     */
	public void writePrompt(){
		try {

			out.write(prompt);
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}


    /**
     * Reads a line of input from the console.
     *
     * @return the line read from the console, or an empty string if an error occurs
     */
	public String readLine(){
		try {
			return in.readLine();
		} catch (IOException e) {
			log.error("error reading line from console", e);
			return "";
		}
	}


    /**
     * Requests a yes/no confirmation from the user with a default message.
     *
     * @return true if the user confirms with 'y', false otherwise
     */
	public boolean requestConfirmation(){

		writeLine("do you want to proceed? please type (y/n)");
		String response = readLine();
		return response.equals("y");

	}

    /**
     * Requests a yes/no confirmation from the user with an extra message.
     *
     * @param extraMessage additional message to display in the confirmation prompt
     * @return true if the user confirms with 'y', false otherwise
     */
	public boolean requestConfirmation(String extraMessage){

		writeLine("do you want to proceed? "+extraMessage+", please type (y/n)");
		String response = readLine();
		return response.equals("y");

	}

    /**
     * Sets the prompt string for the console.
     *
     * @param prompt the new prompt string
     */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}


}
