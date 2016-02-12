package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Automobile;

@WebServlet("/ConfigureCar")
public class ConfigureCar extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int num=0;
	private static Automobile auto;
	/*
	 * doGet method
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
		response.setContentType("text/html");//set the content type
		PrintWriter out = response.getWriter();
		if(num==0){
			auto= (Automobile) request.getAttribute("auto");//get this request only once
		}
		String[] optionSetNames= new String[auto.getOptionSetNum()];//get all the optionSet names
		for(int i=0;i<auto.getOptionSetNum();i++){
			optionSetNames[i]= auto.getOptionSetName(i);
		}
			out.println("<HTML>");
	        out.println("<HEAD>");
	        out.println("<TITLE>");
	        out.println("CarConfiguration!");
	        out.println("</TITLE>");
	        out.println("</HEAD>");
	        out.println("<BODY>");
	        
	        out.println("<center>Configure your car!</center>");
	        out.println("<center>");
	        if(num==0){
	        	out.println("<form name=\"info\" method=\"GET\" action=\"ConfigureCar\">" );
	        } else {
	        	out.println("<form name=\"info\" method=\"GET\" action=\"ShowCar.jsp\">");
	        }
	        out.println("<br>");
	        out.println("<table border=\"1\">");//get all the names and set the servlet content with them
	        for(int i=0;i<optionSetNames.length;i++){
	        	out.println("<tr>");
	        	String[] optionNames= auto.getOptionName(i);
	        	out.println("<td>"+optionSetNames[i]+"</td>");
	        	out.println("<td><select name=\""+ optionSetNames[i]+"\">");
	        	for(int j=0;j<optionNames.length;j++){
	        		out.println("<option value=\""+optionNames[j]+"\">"+optionNames[j]+"</option>");
	        	}
	        	out.println("</select></td>");
	        	out.println("</tr>");
	        }
	        out.println("</table>");
	        out.println("<input type=\"submit\" value=\"Done\">");
	        out.println("</form>");
	        out.println("</center>");
	        out.println("</BODY>");
	        out.println("</HTML>");
	    if(num==0) {
	    	num++;

	    } else {
	    	for(int i=0;i<optionSetNames.length;i++){//use the values of the parameters to set choices
				auto.setChoice(i, request.getParameter(optionSetNames[i]));
			}
			request.setAttribute("auto", auto);
			String url = "/ShowCar.jsp";//continue to the next jsp file
	        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request,response);
	    }
		
	}
	

}
