package de.fom.kp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
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
import de.fom.kp.persistence.MesswertAblesdatumComparator;
import de.fom.kp.persistence.PersonDataBuffer;


public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PersonDao dao;
	private MesswertDao mDao;
	private SimpleDateFormat dateFormat;
	
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
		Locale locale = (Locale)request.getSession().getAttribute(LocaleFilter.KEY);
		ResourceBundle bundle = ResourceBundle.getBundle("MessageResources",locale);
		String pattern =  bundle.getString("i18n.datepattern");
		Gson gson = new GsonBuilder().setDateFormat(pattern).create();
		switch (request.getPathInfo()) {
		case "/personsearch":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			gson.toJson(dao.list(), response.getWriter());
			break;
		case "/zaehlerstaende":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PersonDataBuffer pdbuffer = (PersonDataBuffer) request.getSession().getAttribute("pdbuffer");
			if (pdbuffer.checkZaehler(request.getParameter("id"))){
			//Zählerstände nur lesen, wenn der Zähler zum user gehört.
			List<Messwert> mList = mDao.listByZaehler(Integer.parseInt(request.getParameter("id")) );
			Collections.sort(mList, new MesswertAblesdatumComparator());
			pdbuffer.getZaehler(Integer.parseInt(request.getParameter("id"))).setmList(mList);
			gson.toJson(mList, response.getWriter());
			}
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
