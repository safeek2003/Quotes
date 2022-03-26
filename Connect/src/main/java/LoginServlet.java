 

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.swing.*;

//mport org.eclipse.jdt.internal.compiler.batch.Main;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mail=request.getParameter("email");
		String pwd=request.getParameter("pwd");
		Sign obj=new Sign();
	     String id=obj.signin(mail,pwd);
		if(!id.equals("")) {
			Cookie cookie = new Cookie("status", "true");
			cookie.setMaxAge(24*60*60);  // 24 hours. 
			response.addCookie(cookie);
			Cookie cookie1 = new Cookie("user", id);
			System.out.println(id);
			cookie1.setMaxAge(24*60*60);  // 24 hours. 
			response.addCookie(cookie1);
			JOptionPane.showMessageDialog(null, "login successfully");
			response.sendRedirect("welcome.jsp");
		}
		else {
			JOptionPane.showMessageDialog(null, "Sorry email or password wrong");
			response.sendRedirect("signin.html");
	}
		
	}
}
