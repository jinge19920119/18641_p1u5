package client;

import model.Automobile;

public class SelectCarOption {
	public SelectCarOption(){
		
	}
	public void chooseOption(Automobile auto, int i, String option){
		auto.setChoice(i, option);
	}
	public void displayChoice(Automobile auto){
		auto.printChoice();
		float price= auto.getTotalPrice();
		System.out.println("Total price: "+ price);
	}

}
