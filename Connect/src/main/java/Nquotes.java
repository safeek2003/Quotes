import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;


@WebServlet("/Nquotes")

public class Nquotes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String limit=request.getParameter("limit");
		 Logger logger= Logger.getLogger(Nquotes.class);
	        String path="/home/safeek-zstk256/eclipse-workspace/Connect/src/main/java/log4j.properties";
	        PropertyConfigurator.configure(path);
	        LocalDateTime myDateObj = LocalDateTime.now();
	        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		 try {
			 ServletContext context = request.getServletContext();
				String dbname=context.getInitParameter("db");
				String uname=context.getInitParameter("user");
				String pwd=context.getInitParameter("password");
			 int a=0;
			 PrintWriter pw=response.getWriter();
			  a=Integer.parseInt(limit);
			  JSONArray arr = new JSONArray();  
			 if(a<=0) {
				 arr.add("Please enter the valid number");
			 }
			 else if(a>40){
				 arr.add("Please enter the number less then or equal to 40 because if you read too many quotes you will easily forget");
			 } 
			 else {
		        Set<Integer> numbers = new HashSet<>();
		         while(numbers.size()<a){
		     	 int r =(int)(Math.random()*40)+1;
		           numbers.add(r);
		           }
		         Integer[] num = new Integer[numbers.size()];
		         numbers.toArray(num);	         
	       	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname, uname, pwd);
	         PreparedStatement stmt1 = con.prepareStatement("select quote from quotes where id =?");
	         for(int t:num) {
	         stmt1.setInt(1, t);
	         ResultSet rs1 = stmt1.executeQuery();
	         String quotes="";
	         while(rs1.next()) {
	         quotes=rs1.getString(1);
	         } 
	         arr.add(quotes); 
	         }
	         }
	         pw.write(arr.toJSONString());
	         pw.flush();
	         pw.close();
		 } 
			 catch (Exception e) {
				 PrintWriter pw=response.getWriter();
				 JSONArray arr = new JSONArray(); 
				 arr.add("Something wrong please try again");
				 pw.write(arr.toJSONString());
		         pw.flush();
		         pw.close();
				 logger.log(Level.INFO,myDateObj.format(myFormatObj)+" "+e+"\n"+"Error in NQuotes servlet");
			 } 
	}

}
