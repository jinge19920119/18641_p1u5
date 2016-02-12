package exception;

public class AutoException extends Exception{
	private int errorno;
	private String errormsg;
	private FixHelper fh= new FixHelper();
	
	public AutoException() {//default constructor
		super();
		printProblem();
	}
	/*
	 * constructors with parameters
	 */
	public AutoException(int errorno){
		super();
		this.errorno= errorno;
		printProblem();
		
	}
	public AutoException(String errormsg) {
		super();
		this.errormsg= errormsg;
		printProblem();
	}
	public AutoException(int errorno, String errormsg) {
		super();
		this.errorno= errorno;
		this.errormsg= errormsg;
		printProblem();
	}
	/*
	 * getters and setters
	 */
	public int getErrorno() {
		return errorno;
	}
	public String getErrormsg() {
		return errormsg;
	}
	
	public void printProblem() {
		System.out.println("AutoException ,errorno="+errorno+", errormsg="+errormsg);
	}
	/*
	 * fix functions with different parameters
	 */
	public String fixProblem(String input1,String input2) {
		String output;
		output= fh.fix(input1,input2);
		return output;
	}
	public String fixProblem(){
		return fh.fix();
	}
	public String[][] fixProblem(String[][] input, int j){
		return fh.fix(input, j);
	}

}
