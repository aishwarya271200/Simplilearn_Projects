<%@page import="com.business.entity.Subject"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.business.entity.Classes"%>
<%@page import="java.util.List"%>
<%@page import="com.business.util.HibernateUtil"%>
<%@page import="org.hibernate.SessionFactory"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
</head>
<body>


<a href="index.jsp" class="card">Back to Main Menu</a><br>
<a href="addClasses.html" class="card">Add Class</a><br>
<a href="assignSubjectToClass.jsp" class="card">Assign Subject to Class</a><br>




<h1>The Following Classes are listed</h1>
<%
	SessionFactory sf  = HibernateUtil.buildSessionFactory();
	Session hibernateSession = sf.openSession();
	List<Classes> classes = hibernateSession.createQuery("from Classes").list();
%>
<table>
<tr>
<th>Class Name </th>
<th>Subject Name</th>
</tr>	
	<%		
		for(Classes clas : classes){
			out.print("<tr>");	
			out.print("<td>" + clas.getName() + "</td>");
						
			StringBuffer buf = new StringBuffer();
			boolean first = true;
			for (Subject subject : clas.getSubjects()){
				if(first== true){
					buf.append("<td>" + subject.getName() + "</td>");
					buf.append("</tr>");
					first = false;
				}else{
					buf.append("<tr><td></td>");
					buf.append("<td>" + subject.getName() + "</td>");
					buf.append("</tr>");
				}
				
			}
			out.print(buf.toString());
		}
	%>
</table>

</body>
</html>