package com.business.util;



import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.business.entity.Classes;
import com.business.entity.Student;
import com.business.entity.Subject;
import com.business.entity.Teacher;

public class HibernateUtil {

	public static SessionFactory buildSessionFactory() {

		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Teacher.class)
				.addAnnotatedClass(Subject.class)
				.addAnnotatedClass(Classes.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
				
		return sessionFactory;
	}
}
