
import jakarta.servlet.http.Cookie;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.json.simple.JSONArray;
@WebServlet("/Wishlist")
public class Wishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub\
		String quote=request.getParameter("quote");
		String id="";
		
		Cookie ck[]=request.getCookies();  
	
		for(Cookie cookie : ck){
		    if("user".equals( cookie.getName())){
		        id=cookie.getValue();
		    }
		    }
		 Logger logger= Logger.getLogger(Wishlist.class);
	        String path="/home/safeek-zstk256/eclipse-workspace/Connect/src/main/java/log4j.properties";
	        PropertyConfigurator.configure(path);
	        LocalDateTime myDateObj = LocalDateTime.now();
	        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        PrintWriter pw=response.getWriter();
			 
			JSONArray arr = new JSONArray();  
		 try {
			 ServletContext context = request.getServletContext();
				String dbname=context.getInitParameter("db");
				String uname=context.getInitParameter("user");
				String pwd=context.getInitParameter("password");
	       	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname, uname, pwd);
	        try {
	         PreparedStatement stmt1 = con.prepareStatement("INSERT INTO wishlist (id,quote) values (?,?)");
	         stmt1.setString(1,id);
	         stmt1.setString(2,quote);
	         stmt1.executeUpdate();
	         arr.add("Added to your Favourites") ;
	         pw.write(arr.toJSONString());
	         pw.flush();
	         pw.close(); 
		 }
		 catch(SQLIntegrityConstraintViolationException se) {
			 arr.add("This is already in your favourites");
			 pw.write(arr.toJSONString());
	         pw.flush();
	         pw.close();
		 }
		 }
			 catch (Exception e) {
				 arr.add("Something wrong please try again");
				 pw.write(arr.toJSONString());
		         pw.flush();
		         pw.close();
				 logger.log(Level.INFO,myDateObj.format(myFormatObj)+" "+e+"\n"+"Error in NQuotes servlet");
	   } 
	}
}
