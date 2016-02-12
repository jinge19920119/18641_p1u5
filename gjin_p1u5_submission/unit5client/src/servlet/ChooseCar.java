package servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Automobile;
@WebServlet("/ChooseCar")
public class ChooseCar extends HttpServlet{
	
	private Socket sock;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private static int num=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * doGet method can be called directly
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
             {

		String local="";
		local = InetAddress.getLocalHost().getHostAddress();//get local address
		int port= 1234;
		if(num==0){//set a static variable to make sure create socket once
			sock= new Socket(local, port);	
			writer= new ObjectOutputStream(sock.getOutputStream());
			writer.flush();
			reader= new ObjectInputStream(sock.getInputStream());
		}
		
		response.setContentType("text/html");//set content type 
		PrintWriter out = response.getWriter();
		String carSelect= new String();
			out.println("<HTML>");
	        out.println("<HEAD>");
	        out.println("<TITLE>");
	        out.println("Using the init Method");
	        out.println("</TITLE>");
	        out.println("</HEAD>");
	        out.println("<BODY>");
	        
	        out.println("<center>Please select your auto:</center>");
	        out.println("<center>");
	        out.println("<br>");
	        if(num==0){//call itself for the first time, and then goes to the next
	        	out.println("<form name=\"info\" method=\"GET\" action=\"ChooseCar\">");
	        }else {
	        	System.out.println("will go to config");
	        	out.println("<form name=\"info\" method=\"GET\" action=\"ConfigureCar\">");
	        }
	        out.println("<br>");
	        out.println("<select name=\"carSelect\">");
	        out.println("<option value=\"Wagon ZTW\">Focus Wagon ZTW</option>");
	        out.println("<option value=\"A6\">BMW A6</option>");
	        out.println("</select>");
	        out.println("<input type=\"submit\" value=\"Done\">");
	        out.println("</form>");
	        out.println("</center>");
	        out.println("</BODY></HTML>");
	        carSelect= request.getParameter("carSelect");
	        
		if(num>0){
			writer.writeObject(carSelect);//send it through the writer
			writer.flush();
			Automobile auto= new Automobile();
			try {
				auto= (Automobile) reader.readObject();//create a new automobile object and read it through reader
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			request.setAttribute("auto", auto);
			String url = "/ConfigureCar";//send this object to the next servlet
	        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request,response);
	        
		}else {
			num++;
			
		}
	    
	}

}
