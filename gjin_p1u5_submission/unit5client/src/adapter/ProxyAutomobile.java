package adapter;

import model.Automobile;
import model.Fleet;
import scale.EditOptions;
import util.FileIO;

public abstract class ProxyAutomobile {
	private Automobile a1;
	private static Fleet autoSet = new Fleet();
	private EditOptions eo;

	private void createThread(int i, String[] para, Automobile auto) {
		this.eo = new EditOptions(i, para, auto);
		eo.start();

	}

	private void createThread(int i, String[] para, Automobile auto, float price) {
		this.eo = new EditOptions(i, para, auto, price);
		eo.start();
	}

	public void BuildAuto(String fileName) {// build an Automobile object
											// through a filename
		FileIO fio = new FileIO();
		a1 = fio.buildAutoObject(fileName);
		autoSet.addModel(a1.getModel(), a1);
	}
	public void BuildAuto(Automobile auto){
		autoSet.addModel(auto.getModel(),auto);
	}
	

	public void printAuto(String modelName) {// print the values of this object
		Automobile auto = autoSet.readModel(modelName);
		// auto.printChange();
		auto.print();
	}

	public void updateOptionSetName(String modelName, String optionSetName,
			String newName) {
		Automobile auto = autoSet.readModel(modelName);
		auto.updateOptionSetName(optionSetName, newName);
	}

	/*
	 * update the name of one option
	 */
	public  void updateOptionName(String modelName,String optionSetName, String optionName, String newName) {
		Automobile auto = autoSet.readModel(modelName);
		synchronized (auto) {
			String[] para = { optionSetName, optionName, newName };
			System.out.println("before update : " + newName);
			this.createThread(1, para, auto);// updateOptionSetName, id==1

			System.out.println("after update : " + newName);
			try {
				auto.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	/*
	 * update the price of one option
	 */
	public void updateOptionPrice(String modelName, String optionSetName,
			String optionName, float newPrice) {
		
			Automobile auto = autoSet.readModel(modelName);
			synchronized (auto){
			String[] para = { optionSetName, optionName };

			this.createThread(2, para, auto, newPrice);// updateOptionPrice, id==2
			try {
				auto.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}

}
