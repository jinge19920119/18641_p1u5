package model;

import java.util.*;
public class Fleet {

	private LinkedHashMap<String, Automobile> setModels;
	
	public Fleet() {//constructor
		setModels= new LinkedHashMap<String, Automobile>();
	}
	/*
	 * add one Automobile object to the linkedhashmap
	 */
	public void addModel(String name, Automobile auto){
		setModels.put(name, auto);
	}
	/*
	 * delete one Automobile object from the linkedhashmap
	 */
	public Automobile deleteModel(String name ){
		return setModels.remove(name);
	}
	/*
	 * update the value of one Automobile object in the linkedhashmap
	 */
	public void updateModel(String name, Automobile auto){
		setModels.put(name, auto);
	}
	/*
	 * read  one Automobile object from the linkedhashmap
	 */
	public Automobile readModel (String name) {
		return setModels.get(name);
	}
	
	public Automobile[] getAll () {
		Automobile[] autoSet= new Automobile[setModels.size()];
		Iterator<Automobile> it= setModels.values().iterator();
		int i=0;
		while(it.hasNext()){
			autoSet[i++]=(Automobile) it.next();
		}
		return autoSet;
	}
	
	
}
