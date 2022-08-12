<%@page import="com.business.entity.Student"%>
<%@page import="com.business.entity.Teacher"%>
<%@page import="com.business.entity.Subject"%>
<%@page import="com.business.entity.Classes"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.Session"%>
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
<a href="index.jsp">Back to Main Menu</a><br>
<%String clas = request.getParameter("class");%>
<h1>Class Report for <%=clas%></h1>

<%
	SessionFactory sf  = HibernateUtil.buildSessionFactory();
	Session hibernateSession = sf.openSession();
	List<Classes> classes = hibernateSession.createQuery("from Classes cl where cl.name='" + clas + "'").list();
	Classes clasForReport = classes.get(0);
%>
<table>
<tr>
<th>Subject Name </th>
<th>Teacher Name </th>
</tr>
<%	
	for(Subject subject: clasForReport.getSubjects()){
		out.print("<tr>");	
		out.print("<td>" + subject.getName() + "</td>");
		out.print("<td>" + getTeacherName(subject) + "</td>");
		out.print("</tr>");
	}
%>
</table>
<%!
	public String getTeacherName(Subject subject){
		Teacher teacher = subject.getTeacher();
		String name;
		String lname;
	
		if(teacher != null){
			name=teacher.getName();
			lname = teacher.getLname();
			
			return name + " " + lname;
		}else{
			return "No Teacher assigned";
		}
}
%>
<table>
<br>
<tr>
<th>Listed Students</th>
</tr>
<%
	for(Student student : clasForReport.getStudents()){
		out.print("<tr>");
		out.print("<td>" + student.getName() + " " + student.getFname() + "</td>");
		out.print("</tr>");
	}
%>
</table>
</body>
</html>