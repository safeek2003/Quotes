

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


@WebServlet("/Signup")
public class Signupservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		String email=request.getParameter("email");
		String pwd=request.getParameter("pwd");		
		String uname=request.getParameter("uname");	
		String dob=request.getParameter("dob");
		System.out.println(dob);
		Sign obj=new Sign();
		JFrame f=new JFrame(); 
		if(!obj.mailCheck(email)) {
		String ans=obj.mail(email);
		 
		if(!ans.equals("")) {
			    HttpSession session = request.getSession();
			    session.setAttribute("email", email);
			    session.setAttribute("pwd", pwd);
			    session.setAttribute("uname", uname);
			    session.setAttribute("dob", dob);
			    session.setAttribute("ootp", ans);
			response.sendRedirect("otp.html");
			     }
		else {
		    JOptionPane.showMessageDialog(f,"Please try again");  
		}
		}
		 else {
	    	 JOptionPane.showMessageDialog(f,"This Email id already exist");  
	    	 response.sendRedirect("/signin.html");
	     }
	}

}
