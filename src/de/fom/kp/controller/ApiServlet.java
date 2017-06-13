package de.fom.kp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.fom.kp.dao.JdbcEntnahmestelleDao;
import de.fom.kp.dao.JdbcMesswertDao;
import de.fom.kp.dao.JdbcPersonDao;
import de.fom.kp.dao.JdbcZaehlerDao;
import de.fom.kp.dao.MesswertDao;
import de.fom.kp.dao.PersonDao;
import de.fom.kp.persistence.Messwert;



@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//@Inject 
	private PersonDao dao;
	private MesswertDao mDao;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void init(ServletConfig config) throws ServletException {

		// DB Verbindungen zur Verfügung stellen
		try {
			String s = config.getServletContext().getInitParameter("datasource");
			InitialContext initialContext = new InitialContext();
			DataSource kp = (DataSource) initialContext.lookup(s);			
			dao = new JdbcPersonDao(kp);
			mDao = new JdbcMesswertDao(kp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().create();
		switch (request.getPathInfo()) {
		case "/personsearch":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			gson.toJson(dao.list(), response.getWriter());
			break;
		case "/zaehlerstaende":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			gson.toJson(mDao.listByZaehler( Integer.parseInt(request.getParameter("id"))), response.getWriter());
			break;
		case "/zaehlerstand":
			Messwert m = new Messwert();
			try {
				m.setZaehlerId( Integer.parseInt(request.getParameter("id")));
				m.setAblesedatum(dateFormat.parse(request.getParameter("ablesedatum")));
				m.setMesswert(Double.parseDouble( request.getParameter("messwert")));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mDao.save(m);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
