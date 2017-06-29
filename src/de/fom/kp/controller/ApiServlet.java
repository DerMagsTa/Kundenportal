package de.fom.kp.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.fom.kp.dao.JdbcEntnahmestelleDao;
import de.fom.kp.dao.JdbcMesswertDao;
import de.fom.kp.dao.JdbcPersonDao;
import de.fom.kp.dao.JdbcZaehlerDao;
import de.fom.kp.dao.MesswertDao;
import de.fom.kp.dao.PersonDao;
import de.fom.kp.persistence.Messwert;
import de.fom.kp.persistence.MesswertAblesdatumComparator;
import de.fom.kp.persistence.MesswertJsonSerializer;
import de.fom.kp.persistence.PersonDataBuffer;


public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PersonDao dao;
	private MesswertDao mDao;
	private SimpleDateFormat df;
	
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
		NumberFormat d = NumberFormat.getNumberInstance(locale);
		ResourceBundle bundle = ResourceBundle.getBundle("MessageResources",locale);
		String pattern =  bundle.getString("i18n.datepattern");
		df = new SimpleDateFormat(pattern);
		Gson gson = new GsonBuilder().registerTypeAdapter(Messwert.class, new MesswertJsonSerializer(df, d)).create();		
		switch (request.getPathInfo()) {
		case "/personsearch":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			gson.toJson(dao.list(), response.getWriter());
			break;
		case "/zaehlerstaende":
			Integer zId = Integer.parseInt(request.getParameter("id"));
			List<Messwert> mList;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PersonDataBuffer pdbuffer = (PersonDataBuffer) request.getSession().getAttribute("pdbuffer");
			if (pdbuffer.checkZaehler(request.getParameter("id"))){
			//Zählerstände nur lesen, wenn der Zähler zum user gehört.
				if (pdbuffer.getZaehler(zId).getmList().size()==0){
					pdbuffer.getZaehler(zId).setmList(mDao.listByZaehler(zId));
				}
				mList = pdbuffer.getZaehler(zId).getmList();
				request.getSession().setAttribute("pdbuffer", pdbuffer);
				Collections.sort(mList, new MesswertAblesdatumComparator(MesswertAblesdatumComparator.descending));
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
