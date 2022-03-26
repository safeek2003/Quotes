<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
   Cookie ck[]=request.getCookies();  
boolean state = false;
for(Cookie cookie : ck){
    if("status".equals(cookie.getName()) && "true".equals(cookie.getValue())){
        state = true;
    }
    }
     if(!state)
    	 response.sendRedirect("signin.html");
%>
<style>
#main{ 
    height:82vh;
    width: 61%;
    margin: auto;
    margin-top: 15vh;
    display:flex;
    flex-wrap:wrap;
    justify-content: space-evenly;
}
.sub{
  border-radius: 112px;
  width:33%;
  height:49%;
}
h1{
      text-align: center;
    margin-top: 42%;
    font-size: 46px;
    color: white;
}
.sub:hover{
  width:34%;
  height:
}</style>
  <div id="main">
    
     <div onclick="window.location.href = 'random.html';"class="sub" style="background:#483D87;">
       <h1>Random quotes</h1>
     </div>
    
     <div onclick="window.location.href = 'nquotes.html';" class="sub" style="background:#6CA2EA";>
       <h1>Limit of quotes</h1>
     </div>
     <div onclick="window.location.href = 'category.html';"class="sub" style="background:#B5D33D";>
       <h1>Search by category</h1>
     </div>
     <div onclick="window.location.href = 'author.html';"class="sub" style="background:#FCD13F";>
       <h1>Search by author</h1>
     </div>
     <div onclick="window.location.href = 'wishlist.html';"class="sub" style="background:#EB7D5B;">
       <h1>Wishlist</h1>
     </div>
  </div>
</body>
</html>