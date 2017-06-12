package de.fom.kp.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.xml.registry.infomodel.User;

import org.apache.commons.lang3.*;

import de.fom.kp.dao.*;
import de.fom.kp.model.*;
import de.fom.kp.persistence.*;
import de.fom.kp.view.Message;

//@WebServlet(urlPatterns="*.html")
public class DispatcherServlet extends HttpServlet {

	private PersonDao personDao;
	private EntnahmestelleDao eDao;
	private ZaehlerDao zDao;
	private MesswertDao mDao;
	
	private static final long serialVersionUID = 1L;

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
			mDao = new JdbcMesswertDao(kp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("deprecation")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Locale locale = (Locale)request.getSession().getAttribute(LocaleFilter.KEY);
		//Locale locale = request.getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle("MessageResources",locale);
			String pattern =  bundle.getString("i18n.datepattern");
			
			
			request.setAttribute("datepattern", pattern);
			request.setAttribute("flag", "/images/flag_"+locale.getLanguage()+".png");
			//Testweise hart auf dieses Format!
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			//DateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false);
			NumberFormat d = NumberFormat.getNumberInstance(locale);
			//System.out.println(request.getRequestURI());
			String[] sa = StringUtils.split(request.getServletPath(), "/.\\");
			String forward = null;
			switch (sa[0]) {
			case "index":
				forward = list(request);
				break;
			case "personlist": 
				forward = list(request);
				break;
			case "register":
				forward = "register";
				if(request.getParameter("register")!=null){
					//abgeschicktes Formular
					RegisterForm form = new RegisterForm(request, df, d);
					//validieren
					List<Message> errors = new ArrayList<Message>();
					form.validate(errors);
					if(errors.size()!=0){
						//error
						request.setAttribute("form", form);
						request.setAttribute("errors", errors);
					}else{
						//success
						Person p = form.getPerson();
						if(form.getCompanyid()==null&&StringUtils.isNotBlank(form.getNewcompany())){
							// Company abspeichern
						}
						personDao.save(p);
						forward = list(request);
					}
				}else if(request.getParameter("id")!=null){
					//start edit
					Person p = personDao.read(Integer.parseInt(request.getParameter("id")));
					RegisterForm form = new RegisterForm(p, df,d);
					request.setAttribute("form",form);
				}else{
					//start new
					RegisterForm form = new RegisterForm(df,d);
					request.setAttribute("form",form);
				}
				break;
			case "contact":
				break;

// ---------------------------------------------				
// ---------- K U N D E N P O R T A L ----------		
// ---------------------------------------------
				
			case "entnahmestelle":
				request.setAttribute("entnahmestelle",eDao.list());
				forward = "entnahmestelle";
				break;
				
			case "zaehlerliste":
				request.setAttribute("zaehlerliste",zDao.list());
				forward = "zaehlerliste";
				break;
				
			case "zaehlerstaende":		
				request.setAttribute("zaehler",zDao.read(Integer.parseInt(request.getParameter("id"))));
				forward = "zaehlerstaende";
				break;
				
			case "verbrauch":
				forward = "verbrauch";
				Verbrauchsrechner vr = new Verbrauchsrechner();
				vr.setFrom(new Date(2016-1900,0,1));
				vr.setTo(new Date(2017-1900,11,31));
				vr.setZ(zDao.read(Integer.parseInt( request.getParameter("id"))));
				vr.getZ().setmList(mDao.listByZaehler(Integer.parseInt( request.getParameter("id"))));
				request.setAttribute("verbrauchsListe",vr.ListVerbrauch(Verbrauchsrechner.con_mode_for_each));
				break;
				
			case "welcome":
				forward = "welcome";
				if(request.getParameter("MeineDatenÄndern")!=null){
					PersonForm pf = new PersonForm(request,df,d);
					pf.setChangemode(true);
					request.setAttribute("personform",pf);
				}else if(request.getParameter("Speichern")!=null){	
					PersonForm pf = new PersonForm(request,df,d);
					Person u  = pf.getPerson();
					personDao.save(u);
					request.getSession().setAttribute("user",u);
					pf = new PersonForm(u, df, d);
					request.setAttribute("personform",pf);
					
				}else {
				Person p = (Person) request.getSession().getAttribute("user");
				PersonForm pf = new PersonForm(p, df, d);
				request.setAttribute("personform",pf);
				}
				break;
				
			case "logout":
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				break;
				
			default:
				break;
			}
			if(forward!=null){
				request.setAttribute("forward", forward);
				request.getRequestDispatcher("/WEB-INF/jsp/"+forward+".jsp").forward(request, response);
			}
		
	}

	private String list(HttpServletRequest request) throws DaoException {
		String forward;
		request.setAttribute("personlist",personDao.list());
		forward = "personlist";
		return forward;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
