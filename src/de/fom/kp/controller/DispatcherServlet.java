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
			DateFormat datumsformat = new SimpleDateFormat("yyyy-MM-dd");			
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
				
			case "entnahmestellen":
				//Testseite - später löschen?
				request.setAttribute("entnahmestellen",eDao.list());
				forward = "entnahmestellen";
				break;
			
			case "entnahmestelle":
				forward = "entnahmestelle";
				if(request.getParameter("espeichern")!=null){
					//eine Entnahmestelle soll gespeichert werden (Klick auf Save)
					EStellenForm eForm = new EStellenForm(request);
					List<Message> errors = new ArrayList<Message>();
					eForm.validate(errors);
					if(errors.size()!=0){
						//error
						request.setAttribute("eform", eForm);
						request.setAttribute("errors", errors);
					}else{
						//success
						Entnahmestelle e = eForm.getEntnahmestelle();
						eDao.save(e);
						request.getSession().setAttribute("entnahmestellen",eDao.listByPerson(((Person) request.getSession().getAttribute("user")).getId()));
						Person p = (Person) request.getSession().getAttribute("user");
						PersonForm pf = new PersonForm(p, df, d);
						pf.setChangemode(false);
						request.setAttribute("personform",pf);
						forward = "welcome";
					}
				}else if(request.getParameter("eid")!=null){
					//vorhandene Entnahmestelle soll geändert werden
					EStellenForm eForm = new EStellenForm(eDao.read(Integer.parseInt(request.getParameter("eid"))));
					request.setAttribute("eform",eForm);
				}else{
						//neue Entnahmestelle soll angegeben werden
						EStellenForm eForm = new EStellenForm();
						eForm.setPersonId(((Person) request.getSession().getAttribute("user")).getId());
						request.setAttribute("eform",eForm);
					 }
				break;
				
			case "zaehlerliste":
				//Testseite - später löschen?
				request.setAttribute("zaehlerliste",zDao.list());
				forward = "zaehlerliste";
				break;
				
			case "zaehlerstaende":
				forward = "zaehlerstaende";
				if(request.getParameter("zspeichern")!=null){
					//ein Zählerstand soll gespeichert werden (Klick auf Save)
					MesswerteForm mForm = new MesswerteForm(request, datumsformat, d);
					List<Message> errors = new ArrayList<Message>();
					List<Messwert> mList = mDao.listByZaehler(Integer.parseInt(request.getParameter("zid")) );
					Collections.sort(mList, new MesswertAblesdatumComparator());
					mForm.validate(errors, mList);
					if(errors.size()!=0){
						//error
						request.setAttribute("mform", mForm);
						request.setAttribute("errors", errors);
					}else{
						//success
						Messwert m = mForm.getMesswertClass();
						mDao.save(m);
					}
				}else if(request.getParameter("mid")!=null){
					//vorhandener Zählerstand soll geändert werden
					MesswerteForm mForm = new MesswerteForm(mDao.read(Integer.parseInt(request.getParameter("mid"))), datumsformat, d);
					request.setAttribute("mform",mForm);
				}else{
						//neuer Zählerstand soll angegeben werden (default - beim Aufruf der Seite)
						MesswerteForm mForm = new MesswerteForm(datumsformat, d);
						mForm.setZaehlerId(Integer.parseInt(request.getParameter("zid")));
						request.setAttribute("mform",mForm);
					 }
				request.setAttribute("entnahmestelle",eDao.read(Integer.parseInt(request.getParameter("eid"))));
				request.setAttribute("zaehler",zDao.read(Integer.parseInt(request.getParameter("zid"))));
				break;
				
			case "verbrauch":
				
				forward = "verbrauch";
				Verbrauchsrechner vr = new Verbrauchsrechner();
				if(request.getParameter("datumvon")!=null){
					try {
						Date from = datumsformat.parse(request.getParameter("datumvon"));
						vr.setFrom(from);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else {
					vr.setFrom(new Date(1901-1900,0,1));
				}
				if(request.getParameter("datumbis")!=null){
					try {
						Date to = datumsformat.parse(request.getParameter("datumbis"));
						vr.setTo(to);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else {
					vr.setTo(new Date(2017-1900,11,31));
				}
				vr.setZ(zDao.read(Integer.parseInt( request.getParameter("zid"))));
				vr.getZ().setmList(mDao.listByZaehler(Integer.parseInt( request.getParameter("zid"))));
				request.setAttribute("entnahmestelle",eDao.read(Integer.parseInt(request.getParameter("eid"))));
				request.setAttribute("zaehler",zDao.read(Integer.parseInt(request.getParameter("zid"))));
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
