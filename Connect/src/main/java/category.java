

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;

/**
 * Servlet implementation class category
 */
@WebServlet("/category")
public class category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String category=request.getParameter("category");
		 Logger logger= Logger.getLogger(category.class);
	        String path="/home/safeek-zstk256/eclipse-workspace/Connect/src/main/java/log4j.properties";
	        PropertyConfigurator.configure(path);
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	        Date date = new Date();
		 try {
			 
			 ServletContext context = request.getServletContext();
				String dbname=context.getInitParameter("db");
				String uname=context.getInitParameter("user");
				String pwd=context.getInitParameter("password");
				
      	 int r =(int)(Math.random()*10);
      	 Class.forName("com.mysql.cj.jdbc.Driver"); 
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname, uname, pwd);
        PreparedStatement stmt1 = con.prepareStatement("select quote from quotes where category =?");
        stmt1.setString(1, category);
        ResultSet rs1 = stmt1.executeQuery();
        String quotes="";
        List <String> ar=new ArrayList<String>();
        while(rs1.next()) {
          ar.add(rs1.getString(1));
        }
        quotes=ar.get(r);
        JSONArray arr = new JSONArray();  
        arr.add(quotes); 
        PrintWriter pw=response.getWriter();
        pw.write(arr.toJSONString());
        pw.flush();
        pw.close();
		 con.close();
		 }
		 catch (Exception e) {
			 logger.log(Level.INFO,formatter.format(date)+" "+e+"\n"+"Exception in category servlet class");
		 } 
	}

	}


