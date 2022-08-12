package com.business.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.business.entity.Classes;
import com.business.entity.Student;
import com.business.entity.Subject;
import com.business.entity.Teacher;
import com.business.util.HibernateUtil;

@WebServlet("/assignTeacher")
public class AssignTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AssignTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Step 1: Get details , user has entered
		String name = request.getParameter("name");
		String[] nameArray = name.split(" ");
		//System.out.println("NameList: " + nameList);
		String subject = request.getParameter("subject");
				
		// Step2: Create session
		SessionFactory sf  = HibernateUtil.buildSessionFactory();
		Session session = sf.openSession();
		
		// Step 3: Begin Transaction
		Transaction tx = session.beginTransaction();
		
		String hql_teacher= "from Teacher where name like '%" + nameArray[0] + "%'" + " and lname like '%" + nameArray[1] + "%'";
	//	System.out.println("hql_teacher: "+hql_teacher);
		Query quer = session.createQuery(hql_teacher);
		//System.out.println("quer str: "+quer.getQueryString());
		//System.out.println("quer: "+quer.getResultList());
		List<Teacher> teachers = quer.list();
		
		String hql_subject = "update Subject s set s.teacher=:n where s.name=:sn";
		
		Query<Subject> query = session.createQuery(hql_subject);
		query.setParameter("n", teachers.get(0));
		query.setParameter("sn", subject);
				
		query.executeUpdate();

		
		// STep5: Commit transaction and close sessoin
		tx.commit();
		session.close();
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/viewTeachers.jsp");
        dispatcher.forward(request, response); 
	}

}