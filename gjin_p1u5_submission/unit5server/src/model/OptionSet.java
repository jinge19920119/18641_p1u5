package model;

import java.io.Serializable;
import java.util.*;

public class OptionSet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Option> opt;
	private Option choice;
	OptionSet() {//default constructor
		
	}
	OptionSet(String name) {//constructor with parameters
		this.name= name;
		opt= new ArrayList<Option>();
		choice= new Option();
	}
	protected void addOption(String name, float price) {
		Option op= new Option(name, price);
		opt.add(op);
	}
	/*
	 * getters, that can get properties
	 */
	protected String getName() {
		return name;
	}
	protected ArrayList<Option> getOpt() {
		return opt;
	}
	protected Option getChoice() {
		return choice;
	}
	/*
	 * setters, that can set or reset properties
	 */
	protected void setName(String name) {
		this.name= name;
	}
	protected void setChoice(String name) {
		int i= this.findOption(name);
		choice= opt.get(i);
	}
	/*
	 * find functions
	 */
	protected int findOption(String name) {
		for(int i=0;i<opt.size();i++){
			if(opt.get(i).getName().equals(name)){
				return i;
			}
		}
		return 999;
	}
	
	/*
	 * delete functions, using name or index
	 */
	protected void deleteOption(String name){
		int i= this.findOption(name);
		if(i==opt.size()){
			System.out.println("the one you want to delete doesn't exist!");
		}else {
			opt.remove(i);
		}
		
	}
	protected void deleteOption(int i){
		opt.remove(i);
	}
	
	protected void print() {//print function
		System.out.println();
		System.out.println("name : "+ this.name);
		for(int i=0;i<opt.size();i++){
			opt.get(i).print();
		}
		System.out.println("choice : "+ choice.getName());
			
	}
	/*
	 * Inner class
	 */
	protected class Option implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private float price;
		Option() {//default constructor
			
		}
		Option(String name, float price){//constructor with parameters
			this.name= name;
			this.price= price;
		}
		/*
		 * getters
		 */
		protected String getName() {
			return name;
		}
		protected float getPrice(){
//			
			return price;
		}
		/*
		 * setters
		 */
		protected void setName(String name) {
			this.name= name;
		}
		protected void setPrice(float price){
			float adding= this.getPrice();
			this.price= adding + price;
		}
		/*
		 * print function
		 */
		protected void print () {
			System.out.println(name+" , extra cost: "+price+" ");
		}
		
	}

}
