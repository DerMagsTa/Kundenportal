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


public class DispatcherServlet extends HttpServlet {

	private PersonDao personDao;
	private EntnahmestelleDao eDao;
	private ZaehlerDao zDao;
	private MesswertDao mDao;

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// DB Verbindungen zur Verf�gung stellen
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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonDataBuffer pdbuffer = (PersonDataBuffer)request.getSession().getAttribute("pdbuffer");
		Locale locale = (Locale)request.getSession().getAttribute(LocaleFilter.KEY);
			ResourceBundle bundle = ResourceBundle.getBundle("MessageResources",locale);
			String pattern =  bundle.getString("i18n.datepattern");
			request.setAttribute("datepattern", pattern);
			request.setAttribute("flag", "/images/flag_"+locale.getLanguage()+".png");	
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false);
			NumberFormat d = NumberFormat.getNumberInstance(locale);
			d.setParseIntegerOnly(false);
			//System.out.println(request.getRequestURI());
			String[] sa = StringUtils.split(request.getServletPath(), "/.\\");
			String forward = null;
			switch (sa[0]) {
			case "index":
				forward = "welcome";
				break;
			case "personlist": 
				forward = list(request);
				break;
			case "register":
				forward = "register";
				if(request.getParameter("psave")!=null){
					//abgeschicktes Formular
					PersonForm form = new PersonForm(request, df, d);
					List<Message> errors = new ArrayList<Message>();
					form.validate(errors);
					if(errors.size()!=0){
						//error
						request.setAttribute("form", form);
						request.setAttribute("errors", errors);
					}else{
						//success
						Person p = form.getPerson();
						personDao.save(p);
						forward = list(request);
					}
				}else if(request.getParameter("id")!=null & StringUtils.isNotEmpty(request.getParameter("id"))){
					//start edit
					Person p = personDao.read(Integer.parseInt(request.getParameter("id")));
					RegisterForm form = new RegisterForm(p, df,d);
					request.setAttribute("form",form);
				}else{
					//start new
					PersonForm form = new PersonForm(df,d);
					request.setAttribute("form",form);
				}
				break;
			case "contact":
				break;

// ---------------------------------------------				
// ---------- K U N D E N P O R T A L ----------		
// ---------------------------------------------
				
			case "entnahmestellen":
				//Testseite - sp�ter l�schen?
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
						if (request.getParameter("eid")==null){
							//insert
							pdbuffer.getEs().add(e);
						}else{
							//Update
							e.setZaehler(pdbuffer.getEntnahmestelle(e.getId()).getZaehler());
							pdbuffer.getEs().remove(pdbuffer.getEntnahmestelle(e.getId()));
							pdbuffer.getEs().add(e);
						}
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}else if(request.getParameter("eid")!=null){
					//vorhandene Entnahmestelle soll ge�ndert werden
					EStellenForm eForm = new EStellenForm(pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid"))));
					request.setAttribute("eform",eForm);
				}else{
						//neue Entnahmestelle soll angegeben werden
						EStellenForm eForm = new EStellenForm();
						eForm.setPersonId(((Person) request.getSession().getAttribute("user")).getId());
						request.setAttribute("eform",eForm);
					 }
				if(request.getParameter("edele")!=null){
					if((pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid")))!=null)){
						eDao.delete(Integer.parseInt(request.getParameter("eid")));
						pdbuffer.getEs().remove(pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid"))));
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}
				break;
				
			case "zaehler":
				//pr�fen, ob die IDs in der URL zu den daten der Person passen. wenn nicht: redirect auf welcome!
				if (((request.getParameter("zid")!=null && pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid")))==null))||
					 (request.getParameter("eid")!=null && pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid")))==null)){
					response.sendRedirect(request.getContextPath() + "/welcome.html");
					forward=null;
				}else{
					forward = "zaehler";
					request.getSession().setAttribute("EnergieArten", EnergieArt.getEnergieArten());
					ZaehlerForm zf = null;
					
						//ein Z�hler soll ge�ndert werden
					if ((request.getParameter("zid")!=null)){
						zf = new ZaehlerForm(pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))));
						zf.setEntnahmestellenId(pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))).getEntnahmestelleId());
						request.setAttribute("zform", zf);
					}if(request.getParameter("eid")!=null){
					//ein neuer Z�hler soll angelegt werden
					zf = new ZaehlerForm();
							zf.setEntnahmestellenId(Integer.parseInt(request.getParameter("eid")));
							request.setAttribute("zform", zf);
					}
		
				if(request.getParameter("zspeichern")!=null){
					zf = new ZaehlerForm(request);
					List<Message> errors = new ArrayList<Message>();
					zf.validate(errors);
					if(errors.size()!=0){
						request.setAttribute("zform", zf);
						request.setAttribute("errors", errors);
					}else{
						Zaehler z = zf.getZaehler();
						zDao.save(z);
						if (request.getParameter("eid")!=null){
							//insert
							pdbuffer.getZs().add(z);
							pdbuffer.getEntnahmestelle(z.getEntnahmestelleId()).getZaehler().add(z);
						}else{
							//Update
							//alten z�hler in der Puffer Klasse mit dem neuen ersetzen
							pdbuffer.getEntnahmestelle(z.getEntnahmestelleId()).getZaehler().remove(pdbuffer.getZaehler(z.getId()));
							pdbuffer.getEntnahmestelle(z.getEntnahmestelleId()).getZaehler().add(z);
							pdbuffer.getZs().remove(pdbuffer.getZaehler(z.getId()));
							pdbuffer.getZs().add(z);
						}
//						request.getSession().setAttribute("pdbuffer", pdbuffer);
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}		
				
				if(request.getParameter("zdele")!=null){
					zf = new ZaehlerForm(request);
					Zaehler z = zf.getZaehler();
					zDao.delete(z.getId());
					pdbuffer.getEntnahmestelle(z.getEntnahmestelleId()).getZaehler().remove(pdbuffer.getZaehler((z.getId())));
					pdbuffer.getZs().remove(pdbuffer.getZaehler(z.getId()));
					response.sendRedirect(request.getContextPath() + "/welcome.html");
					forward=null;					
				}
				}
				break;
			case "zaehlerliste":
				//Testseite - sp�ter l�schen?
				request.setAttribute("zaehlerliste",zDao.list());
				forward = "zaehlerliste";
				break;
				
			case "zaehlerstaende":
				forward = "zaehlerstaende";
				if(request.getParameter("zspeichern")!=null){
					//ein Z�hlerstand soll gespeichert werden (Klick auf Save)
					MesswerteForm mForm = new MesswerteForm(request, df, d);
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
						mForm.setZaehlerId(Integer.parseInt(request.getParameter("zid")));
						request.setAttribute("mform",mForm);
					}
				}else if(request.getParameter("mid")!=null){
					//vorhandener Z�hlerstand soll ge�ndert werden
					MesswerteForm mForm = new MesswerteForm(mDao.read(Integer.parseInt(request.getParameter("mid"))), df, d);
					request.setAttribute("mform",mForm);
				}else{
						//neuer Z�hlerstand soll angegeben werden (default - beim Aufruf der Seite)
						MesswerteForm mForm = new MesswerteForm(df, d);
						mForm.setZaehlerId(Integer.parseInt(request.getParameter("zid")));
						request.setAttribute("mform",mForm);
					 }
				request.setAttribute("entnahmestelle",eDao.read(Integer.parseInt(request.getParameter("eid"))));
				request.setAttribute("zaehler",zDao.read(Integer.parseInt(request.getParameter("zid"))));
				break;
				
			case "verbrauch":
				
				forward = "verbrauch";
				VerbrauchsRechnerForm vrform = new VerbrauchsRechnerForm(request, df, d);
				Verbrauchsrechner vr = vrform.getVerbrauchsrechner();
				if (pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid")))!=null){
					//den Verbrauch nur berechenen, wenn der Z�hler auch zur Person geh�rt!
				vr.setZ(pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))));
				//if (vr.getZ().getmList().size()==0){
					//wenn die Messwerte leer sind dann von der DB lesen
					// es kann auch sein, dass der Z�hler keine Messwerte hat... aber warum dann Verbauch anzeigen?!
				vr.getZ().setmList(mDao.listByZaehler(Integer.parseInt(request.getParameter("zid"))));
			//	}
				//hier wird der Verbauch berechnet auf basis der Parameter aus der VerbrauchsForm und an diese �bergeben.
				vrform.setVl(vr.ListVerbrauch());
				request.setAttribute("verbrauchsForm", vrform);
				request.setAttribute("entnahmestelle",pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid"))));
				request.setAttribute("zaehler",pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))));
				}else{
					//sonst Redirect auf Welcome!
					response.sendRedirect(request.getContextPath() + "/welcome.html");
					forward=null;
				}
				break;
				
			case "welcome":
				forward = "welcome";
				if(request.getParameter("MeineDaten�ndern")!=null){
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
		request.setAttribute("personlist", personDao.list());
		forward = "personlist";
		return forward;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
