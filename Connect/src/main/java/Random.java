


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Random")
public class Random extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("reached");
		 Logger logger= Logger.getLogger(Random.class);
	        String path="/home/safeek-zstk256/eclipse-workspace/Connect/src/main/java/log4j.properties";
	        PropertyConfigurator.configure(path);
	        LocalDateTime myDateObj = LocalDateTime.now();
	        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		 try {
			 ServletContext context = request.getServletContext();
				String dbname=context.getInitParameter("db");
				String uname=context.getInitParameter("user");
				String pwd=context.getInitParameter("password");
				
       	 int r =(int)(Math.random()*40)+1;
       	 Class.forName("com.mysql.cj.jdbc.Driver"); 
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname, uname, pwd);
         PreparedStatement stmt1 = con.prepareStatement("select quote from quotes where id =?");
         stmt1.setInt(1, r);
         ResultSet rs1 = stmt1.executeQuery();
         String quotes="";
         if(rs1.next()) {
         quotes=rs1.getString(1);
         }
         
         JSONArray arr = new JSONArray();
         arr.add(quotes);    
         PrintWriter pw=response.getWriter();
         pw.write(arr.toJSONString());
         pw.flush();
         pw.close();
		 con.close();
		 }
		 catch (Exception e) {
			 logger.log(Level.INFO,myDateObj.format(myFormatObj)+" : "+e+"\n"+"Exception in Random servlet class");
		 } 
	}

}
