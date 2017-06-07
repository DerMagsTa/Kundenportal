package de.fom.kp.controller;

import java.io.*;
import java.sql.*;
import java.util.List;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import de.fom.kp.dao.*;
import de.fom.kp.persistence.*;

//@WebServlet("/j_security_check")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PersonDao personDao;
	private EntnahmestelleDao eDao;
	private ZaehlerDao zDao;
	
	// private static final String loginsql = "select * from wp.person p where
	// p.email = ? and p.passphrase_sha2_salted = sha2(CONCAT(?, salt), 512)";

	@Override
	public void init(ServletConfig config) throws ServletException {

		// DB Verbindungen zur Verfügung stellen
		try {
			String s = config.getServletContext().getInitParameter("datasource");
			InitialContext initialContext = new InitialContext();
			DataSource kp = (DataSource) initialContext.lookup(s);			
			personDao = new JdbcPersonDao(kp);
			eDao = new JdbcEntnahmestelleDao(kp);
			zDao = new JdbcZaehlerDao(kp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			Person user = personDao.login(request.getParameter("j_username"), request.getParameter("j_password"));
			if (user != null) {
				request.getSession().setAttribute("user", user);

				List<Entnahmestelle> entnahmestellen = eDao.listByPerson(user.getId());
				for (Entnahmestelle e : entnahmestellen) {
					List<Zaehler> zaehler = zDao.listByEStelle(e.getId());
					e.setZaehler(zaehler);
				}
				
				request.getSession().setAttribute("entnahmestellen", entnahmestellen);

				response.sendRedirect(request.getContextPath() + "/welcome.html");
				return;
			}
			request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
}
