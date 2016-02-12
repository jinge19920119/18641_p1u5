package client;
import java.net.*;
import java.util.Properties;
import java.io.*;

import model.Automobile;
import util.FileIO;

public class DefaultSocketClient extends Thread {
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private BufferedReader in;
	private Socket sock;
	private String strHost;
	private int iPort;
	
	private boolean DEBUG = false;
	
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
	public void setHost(String strHost){
		this.strHost= strHost;
	}
	public void setPort(int iPort) {
		this.iPort= iPort;
	}
	public void run() {
		if(openConnection()){
			handleSession();
			closeSession();
		}
	}
	public boolean openConnection() {
		
		try {
			writer= new ObjectOutputStream(sock.getOutputStream());
			writer.flush();
			reader= new ObjectInputStream(sock.getInputStream());
			
		} catch (Exception e){
			if(DEBUG) System.err.println("Unable to obtain stream to/from "+ strHost);
			return false;
		}
		return true;
		
	}
	
	public void handleSession() {
		in= new BufferedReader(new InputStreamReader(System.in));//system.in serve as input 
		String input= new String();
		try {
			while(!(input=in.readLine()).equalsIgnoreCase("quit")){//run until receiving 'quit'
				if(input.equals("1")){//case 1: upload properties file
					System.out.println("Type in the filename: ");
					String filename= in.readLine();
					FileIO fio= new FileIO();
					Properties props= new Properties();
					while((props=fio.createProperties(filename))==null){
						System.out.println("You have typed wrong file name, please retype again! ");
						filename= in.readLine();
					}
					writer.writeObject(props);//send this properties object to server
					writer.flush();
					if((boolean)reader.readObject()){//receive the signal to mark it as loading successfully
						System.out.println("load successful!!");
					}
				} 
				else if(input.equals("2")){//case 2: configure a car
					writer.writeObject("configuring");
					writer.flush();
					System.out.println("You can choose from one of them! ");
					String[] names= (String[]) reader.readObject();
					for(int i=0;i<names.length;i++){
						System.out.println(names[i]);//output all the model names in console
					}
					String str= in.readLine();
					Boolean flag= true;
					while (flag){
						for(int i=0;i<names.length;i++){
							if(str.equals(names[i]))
								flag= false;
						}
						if(flag){
							System.out.println("You have typed the wrong one, please retype! ");
							str= in.readLine();
						}
					}
					
					writer.writeObject(str);
					writer.flush();
					Automobile auto= (Automobile) reader.readObject();// receive an automobile object from the stream
					auto.print();
					
					int num= auto.getOptionSetNum();
					SelectCarOption sc= new SelectCarOption();
					for(int i=0;i<num;i++){
						System.out.println("Select the options from : "+auto.getOptionSetName(i));
						String option=in.readLine();//option name as input
						sc.chooseOption(auto, i, option);
					}
					sc.displayChoice(auto);
				} else {
					System.out.println("Wrong input! Retype again!");
				}
			}
			writer.writeObject(1);//case 3: quit
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
