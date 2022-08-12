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
import com.business.entity.Subject;
import com.business.entity.Teacher;
import com.business.util.HibernateUtil;
@WebServlet("/assignSubject")
public class AssignSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AssignSubject() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Step 1: Get details , user has entered
		String clas = request.getParameter("class");
		String subject = request.getParameter("subject");
				
		// Step2: Create session
		SessionFactory sf  = HibernateUtil.buildSessionFactory();
		Session session = sf.openSession();
		
		// Step 3: Begin Transaction
		Transaction tx = session.beginTransaction();
		
		String hql_clas= "from Classes where name='" + clas + "'";
		List<Classes> classes = session.createQuery(hql_clas).list();
		
		String hql_subject = "update Subject s set s.classes=:n where s.name=:sn";
		
		Query<Subject> query = session.createQuery(hql_subject);
		query.setParameter("n", classes.get(0));
		query.setParameter("sn", subject);
				
		query.executeUpdate();

		
		// STep5: Commit transaction and close sessoin
		tx.commit();
		session.close();
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/viewClass.jsp");
        dispatcher.forward(request, response); 
		
	}

}