

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;


@WebServlet("/Loadwish")
public class Loadwish extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
	         ArrayList<String> ls=new ArrayList<String>();
	         PreparedStatement stmt1 = con.prepareStatement("select quote from wishlist where id = ?");
	         stmt1.setString(1,id);
	         ResultSet rs=stmt1.executeQuery();
	         while(rs.next()) {
	        	 ls.add(rs.getString(1));
	         }
	         if(ls.size()==0) {
	        	 arr.add("You don't have any favourites");
				 pw.write(arr.toJSONString());
		         pw.flush();
		         pw.close();
	         }
	         else {
	         arr.add(ls) ;
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
