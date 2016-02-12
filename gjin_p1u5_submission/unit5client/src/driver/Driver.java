package driver;

import client.CarModelOptionIO;
/*
 * Name: Ge Jin
 * andrew id: gjin
 * date : jun 30, 2015
 */



public class Driver {
	public static void main(String[] args){
		System.out.println("Select from one of these opinions: ");//serve as menu
		System.out.println("1.Upload Properties file. ");
		System.out.println("2.Configure a car. ");
		System.out.println("quit.");
		
		CarModelOptionIO model= new CarModelOptionIO(1234);//1234 is an casual port number, ip address will use auto ip address.
		model.run();
	}
	

}
