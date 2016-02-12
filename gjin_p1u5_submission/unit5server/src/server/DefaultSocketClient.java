package server;

import java.net.*;
import java.util.Properties;
import java.io.*;

import adapter.BuildAuto;
import model.Automobile;

public class DefaultSocketClient extends Thread {
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket sock;
	private String strHost;
	private int iPort;
	
	private boolean DEBUG = false;
	/*
	 * constructors
	 */
	public DefaultSocketClient(Socket socket) {
		sock= socket;
		iPort= socket.getPort();
		strHost= socket.getInetAddress().toString();
	}
	public DefaultSocketClient (String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
		try {
			sock= new Socket(strHost, iPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * set methods
	 */
	public void setHost(String strHost){
		this.strHost= strHost;
	}
	public void setPort(int iPort) {
		this.iPort= iPort;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		if(openConnection()){
			handleSession();
			closeSession();
		}
	}
	public boolean openConnection() {
		try {
			writer= new ObjectOutputStream(sock.getOutputStream());//io initialize
			reader= new ObjectInputStream(sock.getInputStream());
			
		} catch (Exception e){
			if(DEBUG) System.err.println("Unable to obtain stream to/from "+ strHost);
			return false;
		}
		return true;
		
	}
	
	public void handleSession() {
		while(true){//server needs keeping live all the time
			Object ob;
			try {
				ob= reader.readObject();//read from object stream
				if(ob.getClass().equals(Properties.class)){// ob is a properties object
					Properties props= (Properties) ob;
					BuildCarModelOptions bc= new BuildCarModelOptions();
					bc.addAuto(props);
					writer.writeObject(true);//send a flag
					writer.flush();
				}
				if(ob.getClass().equals(String.class)){//request to configure
					String input= (String) ob;
					System.out.println(input);
					if(input.equalsIgnoreCase("configuring")){
						BuildAuto ba= new BuildAuto();
						ba.printAuto();
						String[] modelName= ba.getAllModelName();//get all the model names and send them to client
						writer.writeObject(modelName);
						writer.flush();
					} 
					else {     //request of one Automobile
						BuildAuto ba= new BuildAuto();
						Automobile auto= ba.getAuto(input);
//						auto.print();
						writer.writeObject(auto);//write this Automobile object to client
						writer.flush();

					}
				}
				if(ob.getClass().equals(Integer.class)){
					break;
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void closeSession () {
		try{
			writer= null;
			reader= null;
			sock.close();
		} catch(IOException e) {
			if(DEBUG) System.err.println("Error closing socket to "+ strHost);
		}
	}

}
