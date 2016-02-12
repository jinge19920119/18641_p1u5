<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Automobile,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show Car </title>
</head>
<body>

<%
  Automobile auto= (Automobile) request.getAttribute("auto");//get the Automobile object, all the optionSet names and values
  String autoName= auto.getMake()+" "+auto.getModel();
  double basePrice= auto.getBasePrice();
  String[] optionSetNames= new String[auto.getOptionSetNum()];
  String[] choices= new String[optionSetNames.length];
  double[] prices= new double[choices.length];
  for(int i=0;i<auto.getOptionSetNum();i++){
	  optionSetNames[i]= auto.getOptionSetName(i);
	  choices[i]= auto.getOptionChoice(optionSetNames[i]);
	  prices[i]= auto.getOptionChoicePrice(optionSetNames[i]);
  }
%>

<center>Now we display your car!</center><%//set the jsp content %>
        <br>
        <center>
        <form name="ShowCar" method="GET" > 
        <table border="1">
         <tr>
           <td><%=autoName %></td>
           <td>Base Price</td>
           <td><%=basePrice %></td>     
        </tr>
        <%for(int i=0;i<choices.length;i++){ %>
        	<tr>
        <td><%=optionSetNames[i]%></td>
        <td><%=choices[i] %></td>
        <td><%=prices[i] %></td>
        </tr>
        <%} %>
        <tr>
        <td>Total Cost</td>
        <td></td>
        <td><%=auto.getTotalPrice() %></td>
        </tr>
       
        </table>
        </form>
        </center>
</body>
</html>