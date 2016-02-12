package client;

import java.net.InetAddress;
import java.net.UnknownHostException;



public class CarModelOptionIO {
	private DefaultSocketClient dsc;
	
	public CarModelOptionIO(int port){
		String str= new String();
		try {
			str = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		dsc= new DefaultSocketClient(str, port);
		
	}
	public void run(){
		dsc.start();
	}

}
