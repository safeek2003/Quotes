import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import java.security.MessageDigest;  

public class Sign {
   public String signup(String ootp,String uotp,String mail,String pwd,String uname,String dob) {
  	 if(uotp.equals(ootp)) {
  		  Sign obj =new Sign();
	      pwd=obj.pwdencryption(pwd);
  		 try {
  	   		  
               Class.forName("com.mysql.cj.jdbc.Driver");
               try {
            	   UUID uuid=UUID.randomUUID(); 
            	   String uid=String.valueOf(uuid);
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Social", "root", "safeek2003");
               PreparedStatement qe = con.prepareStatement("insert into users(uid_dybe,username_ghzy,email_xyq,dob,bio,password_ctf) values(?,?,?,?,DEFAULT,?)");
               qe.setString(1, uid);
               qe.setString(2, uname);
               qe.setString(3, mail);
               qe.setString(4, dob);
               qe.setString(5, pwd);
               qe.executeUpdate();
               return "Account created Successfully";
                
               }
               catch(SQLException se) {
              	 System.out.println(se);
              	 return "This Email ID already exist";
               }
      	 }
      	 catch(ClassNotFoundException ce) {
      		 System.out.println(ce);
      	 }
  	 }
  	 else {
  		 return "OTP was wrong";
  	 }
  	 
	 return "Something went wrong please retry";
	  
   }
   
   
   public String mail(String mail) {
       Random rn=new Random();
       	 int r =rn.nextInt(10000)+99999;
       	String ootp1=String.valueOf(r);
  	 Mail mail1=new Mail(); 
  	 if(mail1.SendMail(mail, "", ootp1))
     return ootp1;
  	 else
  		 return"";
   }
   
   //sign in 
   public String signin(String mail,String pwd) {
	     Sign obj =new Sign();
	      pwd=obj.pwdencryption(pwd);
	      System.out.println(pwd+" "+mail);
 	  try {
           Class.forName("com.mysql.cj.jdbc.Driver"); 
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Social", "root", "safeek2003");
           PreparedStatement stmt1 = con.prepareStatement("select * from users where email_xyq =? and password_ctf=?");
           stmt1.setString(1, mail);
           stmt1.setString(2, pwd);
           ResultSet rs1 = stmt1.executeQuery();
           if(rs1.next()) {
        	   System.out.println("true");
        	   return rs1.getString(1);
           }
           
           System.out.println("false");
          
           return "";
      }
 	  catch(Exception e) {  
 		  System.out.println(e);
 		 return "";
 	  }
     }
   
   //password encryption
        public String pwdencryption(String password) {
	        String encryptedpassword ="";  
	        try   
	        {   
	            MessageDigest m = MessageDigest.getInstance("MD5");  
	            m.update(password.getBytes());    
	            byte[] bytes = m.digest(); 
	            StringBuilder s = new StringBuilder();  
	            for(int i=0; i< bytes.length ;i++)  
	            {  
	                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
	            }  
	            encryptedpassword = s.toString();  
	        }   
	        catch (Exception e)   
	        {  
	            e.printStackTrace();  
	        }   
	        return encryptedpassword;

	         }  
        public Boolean mailCheck(String mail) {
            
       	  try {
                 Class.forName("com.mysql.cj.jdbc.Driver"); 
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Social", "root", "safeek2003");
                 PreparedStatement stmt1 = con.prepareStatement("select * from users where email_xyq = ?");
                 stmt1.setString(1, mail);
                 ResultSet rs1 = stmt1.executeQuery();
                 return rs1.next(); 
            }
       	  catch(Exception e) {  
       		  System.out.println(e);
       		 return false;
       	  }
       
        }

  
}
