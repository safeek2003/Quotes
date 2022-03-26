

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Servlet implementation class 
 */
@WebServlet("/Otp")
public class Otpservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String uotp=request.getParameter("auth");
	    Sign obj=new Sign();
	    HttpSession session = request.getSession();
	    String email = (String) session.getAttribute("email");
	    String pwd = (String)session.getAttribute("pwd");
	    String uname = (String)session.getAttribute("uname");
	    String dob = (String)session.getAttribute("dob");
	    String ootp = (String)session.getAttribute("ootp");
         session.removeAttribute("email");
         session.removeAttribute("pwd");
         session.removeAttribute("uname");
         session.removeAttribute("uname");
         session.removeAttribute("otp");
	    String state=obj.signup(ootp,uotp,email,pwd,uname,dob);
	    if(state.equals("Account created Successfully")) {
	    	
	    	//Adding cookie 
	    Cookie cookie = new Cookie("status", "true");
	    cookie.setMaxAge(24 * 60 * 60);  // 24 hours. 
	    response.addCookie(cookie);
	    
	    
	    response.sendRedirect("welcome.jsp");
	    }
	    else {
	    	response.sendRedirect("signup.html");
	    }
	    JFrame f=new JFrame();  
	    JOptionPane.showMessageDialog(f,state); 
	}

}
