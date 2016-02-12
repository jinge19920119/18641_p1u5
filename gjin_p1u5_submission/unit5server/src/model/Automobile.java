package model;

import java.io.*;
import java.util.*;


public class Automobile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String make;
	private String model;
	private float basePrice;
	private ArrayList<OptionSet> opset;
	
	
	public Automobile(){//default constructor
		
	}
	/*
	 * constructor with parameters
	 */
	public Automobile(String make,String model, float baseprice){
		this.make= make;
		this.model= model;
		this.basePrice= baseprice;
		opset= new ArrayList<OptionSet>();
	}
	/*
	 * getters, that can get properties of Automotive and OptionSet classes
	 */
	public String getMake() {
		return make;
	}
	public String getModel() {
		return model;
	}
	public float getBasePrice() {
		return basePrice;
	}
	public String getOptionChoice (String setName) {
		int i= this.findOptionSet(setName);
		if(i!=999){
			return opset.get(i).getChoice().getName();
		} else {
			return null;
		}
		
	}
	public float getOptionChoicePrice(String setName) {
		int i= this.findOptionSet(setName);
		if(i==999){
			return 0;
		}else {
			return opset.get(i).getChoice().getPrice();
		}
		
	}
	public float getTotalPrice(){
		float total= this.basePrice;
		for(int i=0;i<opset.size();i++){
			total+= opset.get(i).getChoice().getPrice();
		}
		return total;
	}
	
	/*
	 * find functions using OptionSet name or Option name
	 */
	public int findOptionSet(String name){
		int i;
		for(i=0;i<opset.size();i++)
		{
			if(opset.get(i)!=null && opset.get(i).getName().equals(name))
				return i;
		}
		return 999;
	}
	public int findOption(String setName, String optName) {
		int i;
		i= this.findOptionSet(setName);
		if(i==999){
			return 999;
		}
		OptionSet optset= opset.get(i);
		if(optset.findOption(optName)==999){
			return 999;
		}
		return optset.findOption(optName);
		
	}
	/*
	 * setters, that can set or reset properties
	 */
	public void setModel(String name) {
		this.model= name;
	}
	public void setMake (String make) {
		this.make= make;
	}
	public void setOptionChoice(String setName, String optionName){
		int i= this.findOptionSet(setName);
		if(i!=999){
			opset.get(i).setChoice(optionName);
		}else {
			System.out.println("The setname doesn't exist!");
		}
		
		
	}
	
	public void setBasePrice(float price){
		this.basePrice= price;
	}
	
//	public void setOptionSet(int i, String name, int optionSize){
//		opset[i]= new OptionSet(name, optionSize);
//	}
	/*
	 * FileIO would call it to initialize new objects
	 */
	public void setOptionSet(String name){ 
		OptionSet ops= new OptionSet(name);
		opset.add(ops);
	}
	public void setOption(String setname, String name, float price) {
		int i= this.findOptionSet(setname);
		if(i!=999){
			opset.get(i).addOption(name, price);
		} else {
			System.out.println("setoptions fails!");
		}
		
		
	}
	
	public void setOptionName(String setname,String optname, String name) {
		int i=this.findOptionSet(setname);
		int j= opset.get(i).findOption(optname);
		if(i!=999 && j!=999){
			opset.get(i).getOpt().get(j).setName(name);
		}
		
	}
	public void setOptionPrice(String setname, String optname, float price) {
		int i= this.findOptionSet(setname);
		int j= this.findOption(setname, optname);
		if(i!=999 && j!=999) {
			opset.get(i).getOpt().get(j).setPrice(price);
		}
		
	}
	/*
	 * delete functions, can delete OptionSet objects or Option objects, with index or object-name
	 */
	public void deleteOptionSet(int i){
		opset.remove(i);
	}
	public void deleteOptionSet(String name){
		int i= this.findOptionSet(name);
		if(i!=999){
			opset.remove(i);
		}
		
	}
	public void deleteOption(int i, int j){
		if(opset.get(i)!=null){
			opset.get(j).deleteOption(j);
		}
	}
	public void deleteOption(String setName, String optName){
		int i= this.findOptionSet(setName);
		int j= this.findOption(setName, optName);
		if(i!=999 && j!=999){
			opset.get(i).deleteOption(j);
		}
		
	}
	
	/*
	 * update functions, that will call find and set functions
	 */
	public void updateOptionSetName(String oldname, String newname){
		int i= this.findOptionSet(oldname);
		if(i!=999){
			opset.get(i).setName(newname);
		}
	}
	public void updateOptionName(String setName, String oldname, String newname){
		int i= this.findOptionSet(setName);
		int j= this.findOption(setName, oldname);
		if(i!=999 && j!=999) {
			opset.get(i).getOpt().get(j).setName(newname);
		}
	}
	public void updateOptionPrice(String setName, String optName, float newPrice){
		int i= this.findOptionSet(setName);
		int j= this.findOption(setName, optName);
		if(i!=999 && j!=999) {
			opset.get(i).getOpt().get(j).setPrice(newPrice);
		}
	}
	/*
	 * print method, print all the properties
	 */
	public void printChange(){
		System.out.println(opset.get(0).getOpt().get(9).getName());
	}
	public void print() {
		System.out.println("make : "+ this.make+ ", model : "+model+", baseprice : "+basePrice);
		for(int i=0;i<opset.size();i++){
			opset.get(i).print();
		}
		System.out.println("****************************");
			
	}
	/*
	 * read from properties to build auto
	 */
	public Automobile readProperties(Properties props) {
		Automobile auto;
		String carMake= props.getProperty("CarMake");
		String carModel= props.getProperty("CarModel");
		float basePrice= Float.parseFloat(props.getProperty("BasePrice"));
		auto= new Automobile(carMake, carModel, basePrice);
		if(!carMake.equals(null)){
			int i=1;
			String optionSet, option;
			while((optionSet=props.getProperty("Option"+i))!=null){
				auto.setOptionSet(optionSet);
				char ch= 'a';
				while((option= props.getProperty("OptionValue"+i+ch))!=null){
					auto.setOption(optionSet, option, Float.parseFloat(props.getProperty("OptionPrice"+i+ch)));
					ch++;
				}
				i++;
			}
		}
		return auto;
	}
	

}
