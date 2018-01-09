package it.spaghettisource.automouse.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CmdLineManager {

	static Log log = LogFactory.getLog(CmdLineManager.class.getName());


	private BufferedReader in;
	private BufferedWriter out;
	private String prompt;	


	public void init(){
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new BufferedWriter(new OutputStreamWriter(System.out));
		prompt = "#";
	}


	public void write(String line){
		try {
			out.write(line);
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}

	public void writeLine(String line){
		try {
			out.write(line+"\n");
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}

	public void writeLine(Object obj){
		writeLine(obj.toString());
	}


	public void writeEndOfLine(){
		try {
			out.write("\n");
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}


	public void writePrompt(){
		try {

			out.write(prompt);
			out.flush();
		} catch (IOException e) {
			log.error("error writing line to console", e);
		}
	}


	public String readLine(){
		try {
			return in.readLine();
		} catch (IOException e) {
			log.error("error reading line from console", e);
			return "";
		}
	}


	public boolean requestConfirmation(){

		writeLine("do you want to proceed? please type (y/n)");
		String response = readLine();
		if(response.equals("y")){
			return true;
		}

		return false;
	}

	public boolean requestConfirmation(String extraMessage){

		writeLine("do you want to proceed? "+extraMessage+", please type (y/n)");
		String response = readLine();
		if(response.equals("y")){
			return true;
		}

		return false;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}


}
