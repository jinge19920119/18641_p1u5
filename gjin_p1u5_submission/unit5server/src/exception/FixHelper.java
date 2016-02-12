package exception;

import adapter.FixAuto;

public class FixHelper implements FixAuto {
	
	protected String fix(String input1,String input2){//Exception 4 & 5
		return input2;
	}
	protected String fix(){//Exception 1
		return "readFile1.txt";
	}
	protected String[][] fix(String[][] input, int line){//Exception 2 & 3
		String[][] output= new String[input.length-1][input[0].length];
		System.arraycopy(input, 0, output, 0, line);
		System.arraycopy(input, line+1, output, line, input.length-line-1);
		return output;
	}

}
