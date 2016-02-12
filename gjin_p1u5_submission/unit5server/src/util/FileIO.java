package util;
import java.io.*;

import exception.AutoException;
import model.Automobile;

public class FileIO {
	public FileIO() {//default constructor
		
	}
	/*
	 * read from one file and input all the values into the Automotive object
	 */
	public Automobile buildAutoObject(String filename) {
		Automobile auto;
		String modelName;
		String makeName;
		float basePrice;
		String[] readLine= new String[1000];
		int i=0;
		
			//the last line of the input file is for choice 
		boolean fixed= false;
		do {
			try{
			    FileReader file= new FileReader(filename);
			    fixed= true;
			    BufferedReader br= new BufferedReader(file);
				String line= new String();
				while((line= br.readLine())!=null){
					readLine[i]= line;
					i++;
				}
				br.close();
			}
	        catch(FileNotFoundException e){// Exception 1: FileNotFoundException
				AutoException ae= new AutoException(1,"FileNotFound!");
				filename= ae.fixProblem();//change the filename to an fault name
//				System.out.println(filename);
			}
			catch(IOException e){
				System.out.println("Error --"+ e.toString());
			}
		} while(fixed==false);
		
		String[] buf= readLine[0].split(",");
		int setNum= buf.length;
		String[][] input= new String[i][setNum];//input[][] saves every String object from the file
		for(int j=0;j<i-1;j++)
		{
			input[j]= readLine[j].split(",");
		}
		String[] choice= readLine[i-1].split(",");
		int wrongLine=0;
		fixed= false;
		do{
			try{//Exception 3: Missing baseprice in input file
				for(int j=1;j<i-1;j++)
				{
					for(int k=input[j][2].length()-1;k>=0;k--){
						int chr= input[j][2].charAt(k);
						if(chr<48 || chr>57)
						{
							wrongLine= j;
							throw new AutoException(3,"MissingBasePrice!!");
						}
							
					}
				}
				fixed = true;
			}
			catch(AutoException ae){
				input= ae.fixProblem(input, wrongLine);//if one line is short of baseprice, just deleting this line.
				i--;
			}
		} while (fixed == false);
		
		wrongLine =0;
		try{//Exception 2:Missing data in input file!!
			for(int j=0;j<i-1;j++)
				if(input[j].length!= setNum){
					wrongLine= j;
					throw new AutoException(2,"MissingData!!");
				}
					
		}
		catch(AutoException ae) {//if one line is short of some data, just deleting this line.
			input= ae.fixProblem(input, wrongLine);
			i--;
		}
		
		
		
		
		fixed= false;
		do{//Exception 4: Not all the model names are the same!!
			int j=0;
			try{
				for(j=2;j<i-1;j++)
					if(!input[j][1].equals(input[j-1][1]))
						throw new AutoException(4,"NotAllModelNamesSame!!");
				fixed= true;
			}
			catch(AutoException ae){//change that one to the former
				input[j][1]=ae.fixProblem(input[j][1],input[j-1][1]);
			}
		} while(fixed == false);
		
		fixed = false;
		do{
			int j=0,k=0,h=0;
			try{//Exception 5: There are some properties which have different values!! 
				
				for(j=1;j<i-1;j++){
					for(k=3;k<setNum;k=k+2){
						for(h=1;h<j;h++){
//							System.out.println(j+" "+k+" "+h);
							if(input[j][k].equals(input[h][k]) && !input[j][k+1].equals(input[h][k+1]))
								throw new AutoException(5,"SamePropertiesWithDifferentValues");
						}
					}
				}
				fixed= true;
			}
			catch(AutoException ae){//change that one to the former
				input[j][k+1]= ae.fixProblem(input[j][k+1], input[h][k+1]);
			}
		} while (fixed == false);
		
		
		
		makeName= input[1][0];
		modelName= input[1][1];
		basePrice= Float.parseFloat(input[1][2]);
		auto= new Automobile(makeName,modelName, basePrice);//initialize an Automobile object
		for(int j=3;j<setNum;j=j+2){
			auto.setOptionSet(input[0][j]);
			for(int k=1;k<i-1;k++){
				int h;
				for(h=0;h<k;h++)
				{
					if(input[k][j].equals(input[h][j]))
						break;
				}
				if(h==k)
					auto.setOption(input[0][j], input[k][j], Float.parseFloat(input[k][j+1]));
			}
			auto.setOptionChoice(input[0][j], choice[j/2-1]);
		}
		return auto;
	}
	
	
	
	public void serialize(Automobile auto){//serialization function
		try{
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("FileOut.txt"));
			out.writeObject(auto);
			out.close();
		}
		catch(Exception e){
			System.out.println("Error -- "+e);
			System.exit(1);
		}
	}
	public Automobile deserialize(String filename){//deserialization function
		ObjectInputStream in;
		Automobile auto= new Automobile();
		FileInputStream fis;
		try{
			fis= new FileInputStream(filename);
			in= new ObjectInputStream(fis);
			auto= (Automobile) in.readObject();
			in.close();
		}
		catch(Exception e){
			System.out.println("Error -- "+e);
			System.exit(1);
		}
		return auto;
	}

}
