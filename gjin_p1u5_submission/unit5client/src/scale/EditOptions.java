package scale;

import model.Automobile;

public class EditOptions extends Thread{
	private Automobile auto;
	private int idnum;
	private String[] para;
	private float price;

	public EditOptions() {//default constructor

	}

	/*
	 * constructors with parametres
	 */
	public EditOptions(int i) {
		idnum = i;
	}

	public EditOptions(int i, String[] para, Automobile auto) {
		this.idnum = i;
		this.para = para;
		this.auto = auto;
	}

	public EditOptions(int i, String[] para, Automobile auto, float price) {
		this.idnum = i;
		this.para = para;
		this.price = price;
		this.auto = auto;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		synchronized (auto) {

			switch (idnum) {
			case 1:
				this.updateOptionName(para, auto);
				auto.notifyAll();
				break;
			case 2:
				this.updateOptionPrice(para, auto, price);
				auto.notifyAll();
				break;
			default:
				break;
			}
		}
	}

	/*
	 * case 1: update option name
	 */
	public  void updateOptionName(String[] para, Automobile auto) {
		String optionSetName = para[0];
		String optionName = para[1];
		String newName = para[2];

		System.out.println("before update, in edit : " + newName);
		//there are 4 printout in total for one object, we can see from them if the consequence of them is right or 
		// wrong because of synchrnoization.
		            
		auto.updateOptionName(optionSetName, optionName, newName);
		System.out.println("after update,in edit : " + newName);

	}

	/*
	 * case 2: update option price
	 */
	public void updateOptionPrice(String[] para, Automobile auto,
			float price) {
		String optionSetName = para[0];
		String optionName = para[1];
		//as i did not get the result of data-corrupting of prices, so there are no printout here.
		auto.updateOptionPrice(optionSetName, optionName, price);

	}

}
