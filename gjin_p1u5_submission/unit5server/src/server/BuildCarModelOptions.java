package server;

import java.net.*;
import java.io.*;
import java.util.*;

import adapter.BuildAuto;
import model.Automobile;


public class BuildCarModelOptions implements AutoServer{
	


	private ServerSocket serverSocket= null;
	/*
	 * default constructors
	 */
	public BuildCarModelOptions() {
		
	}
	/*
	 * start methods
	 */
	public void run() {
		this.createServerSocket();
		this.createClietSocket();
	}
	
	public void createServerSocket() {//create a serversocket in the server
		try{
			this.serverSocket= new ServerSocket (1234);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 1234");
			System.exit(1);
		}
	}
	
	public void createClietSocket() {//wait for a client server connecting to serversocket
		try {
			while(true){
				Socket soc= serverSocket.accept();
				DefaultSocketClient clientSocket= new DefaultSocketClient(soc);
				clientSocket.start();
			}
		} catch (IOException e) {
			System.err.println("Accept failed!");
			System.exit(1);
		}
	}
	public void addAuto(Properties props) {
		Automobile auto= new Automobile();      
		auto= auto.readProperties(props);
		BuildAuto ba= new BuildAuto();
		ba.BuildAuto(auto);
	}
	
	

}
