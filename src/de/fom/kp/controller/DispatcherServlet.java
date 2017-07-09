package de.fom.kp.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

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
		
		String[] sa = StringUtils.split(request.getServletPath(), "/.\\");
		String forward = null;
		Boolean logout = false;
		switch (sa[0]) {
			case "index":
				
			case "welcome":
				forward = "welcome";
				Person user = (Person) request.getSession().getAttribute("user");
				PersonForm pf = new PersonForm(user, df, d);
				request.setAttribute("personform",pf);
				break;
				
			case "register":
				forward = "register";
				if(request.getParameter("psave")!=null){
					//abgeschicktes Formular
					PersonForm form = new PersonForm(request, df, d);
					List<Message> errors = new ArrayList<Message>();
					//PersonDao wird benötigt, um Email prüfen zu können.
					form.validate(errors, personDao);
					if(errors.size()!=0){
						//error
						request.setAttribute("form", form);
						request.setAttribute("errors", errors);
					}else{
						//success
						Person p = form.getPerson();
						personDao.save(p);
						request.getSession().setAttribute("user", p);
						if (pdbuffer==null){
							pdbuffer = new PersonDataBuffer();
						}
						pdbuffer.setP(p);
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}else if(request.getParameter("id")!=null && StringUtils.isNotEmpty(request.getParameter("id"))){
					if (pdbuffer.checkPerson(request.getParameter("id"))) {
						//bestehende Person soll geändert werden
						Person p = personDao.read(Integer.parseInt(request.getParameter("id")));
						PersonForm form = new PersonForm(p, df,d);
						request.setAttribute("form",form);
					} else {
						//nicht berechtigt
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}else{
					//start new
					PersonForm form = new PersonForm(df,d);
					request.setAttribute("form",form);
				}
				break;
				
			case "passwort":
				forward = "passwort";
				if(request.getParameter("pwsave")!=null){
					//abgeschicktes Formular
					PasswortForm pwForm = new PasswortForm(request);
					List<Message> errors = new ArrayList<Message>();
					pwForm.validate(errors);
					if(errors.size()!=0){
						//error
						request.setAttribute("pwform", pwForm);
						request.setAttribute("errors", errors);
					}else{
						//success
						Person p = (Person) request.getSession().getAttribute("user");
						if ( personDao.updatePassword(p.getId(), pwForm.getPasswort_alt(), pwForm.getPasswort_neu())) {
							//Passwort wurde geändert
							request.setAttribute("pwupdate", "erfolgreich");
						} else {
							// Fehler
							request.setAttribute("pwupdate", "fehlgeschlagen");
						}
					}
				}else {
					//Passwort soll geändert werden
					PasswortForm pwForm = new PasswortForm(request);
					request.setAttribute("pwform", pwForm);
				}
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
							pdbuffer.getEs().set(pdbuffer.getEs().indexOf(pdbuffer.getEntnahmestelle(e.getId())), e);
						}
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}else if(request.getParameter("eid")!=null){
					//vorhandene Entnahmestelle soll geändert werden
					//-> Berechtigung prüfen
					if ( pdbuffer.checkEntnahmestelle(request.getParameter("eid"))== false){
							response.sendRedirect(request.getContextPath() + "/welcome.html");
							forward=null;
						}else{
							EStellenForm eForm = new EStellenForm(pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid"))));
							request.setAttribute("eform",eForm);
						}
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
				//prüfen, ob die IDs in der URL zu den daten der Person passen. wenn nicht: redirect auf welcome!
				if ((request.getParameter("zid")!=null &&pdbuffer.checkZaehler(request.getParameter("zid"))==false)||
					(request.getParameter("eid") !=null && pdbuffer.checkEntnahmestelle(request.getParameter("eid"))== false)){
					response.sendRedirect(request.getContextPath() + "/welcome.html");
					forward=null;
				}else{
					forward = "zaehler";
					request.getSession().setAttribute("EnergieArten", EnergieArt.getEnergieArten());
					ZaehlerForm zf = null;
					
					//ein Zähler soll geändert werden
					if ((request.getParameter("zid")!=null)){
						zf = new ZaehlerForm(pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))));
						zf.setEntnahmestellenId(pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))).getEntnahmestelleId());
						request.setAttribute("zform", zf);
					}if(request.getParameter("eid")!=null){
						//ein neuer Zähler soll angelegt werden
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
								//alten zähler in der Puffer Klasse mit dem neuen ersetzen;
								pdbuffer.getEntnahmestelle(z.getEntnahmestelleId()).getZaehler().set(pdbuffer.getEntnahmestelle(z.getEntnahmestelleId()).getZaehler().indexOf(pdbuffer.getZaehler(z.getId())), z);
								pdbuffer.getZs().set(pdbuffer.getZs().indexOf(pdbuffer.getZaehler(z.getId())), z);
							}
							//bei erfolgreichem Ändern/Anlegen zurücl auf welcome
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
						//bei erfolgreichem Löschen zurück auf welcome
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;					
					}
				}
				break;
				
			case "zaehlerstaende":
				forward = "zaehlerstaende";
				MesswerteForm mForm;
				Integer mId;
				Integer zId;
				Integer eId;
				Zaehler z;
				Messwert m;
				if ((pdbuffer.checkEntnahmestelle(request.getParameter("eid")) == false) || 
			        (pdbuffer.checkZaehler(request.getParameter("zid")) == false) ||
					(request.getParameter("mid") != null && pdbuffer.checkMesswert(request.getParameter("mid"), request.getParameter("zid")) == false)){
					//Bei Zäherlständen haben wir im Request eid, zid und ggf mid bei Neuanlage eines Messwertes.
					//Entnahmestelle und Zähler müssen zum user gehören
					//ein gänderter Messwert muss zum User und zum angegebenen Zähler gehören
					//ansonste: raus!
					response.sendRedirect(request.getContextPath() + "/welcome.html");
					forward=null;
				}else{
					zId = Integer.parseInt(request.getParameter("zid"));
					eId = Integer.parseInt(request.getParameter("eid"));
					z = pdbuffer.getZaehler(zId);
					if(request.getParameter("zspeichern")!=null){
						//ein Zählerstand soll gespeichert werden (Klick auf Save)
						mForm = new MesswerteForm(request, df, d);
						List<Message> errors = new ArrayList<Message>();
						//Die Messwerte sind im Puffer gepspeichert!
						List<Messwert> mList = z.getmList();
						Collections.sort(mList, new MesswertAblesdatumComparator());
						mForm.validate(errors, mList);
						if(errors.size()!=0){
							//error
							request.setAttribute("mform", mForm);
							request.setAttribute("errors", errors);
						}else{
							//success
							m = mForm.getMesswertClass();
							mDao.save(m);
							
							if (mForm.getId()==null){
								z.getmList().add(m);
							}else{
								Messwert alt = z.getMWert(m.getId());
								Integer index = z.getmList().indexOf(alt);
								z.getmList().set(index, m);
							}
							request.getSession().setAttribute("pdbuffer", pdbuffer);
							mForm = new MesswerteForm(df, d);
						}
					}else if(request.getParameter("mid")!=null){
						mId = Integer.parseInt(request.getParameter("mid"));
						if(request.getParameter("del")!=null){
							//ein Zählerstand soll gelöscht werden
							m = pdbuffer.getZaehler(zId).getMWert(mId);
							mDao.delete(mId);
							pdbuffer.getZaehler(zId).getmList().remove(m);
							mForm = new MesswerteForm(df, d);
						}else{
						//vorhandener Zählerstand soll geändert werden
						mForm = new MesswerteForm(z.getMWert(mId), df, d);
						}
						
					}else{
							//neuer Zählerstand soll angegeben werden (default - beim Aufruf der Seite)
							mForm = new MesswerteForm(df, d);
							
						 }
					mForm.setZaehlerId(z.getId());
					request.setAttribute("mform",mForm);
					request.setAttribute("entnahmestelle", pdbuffer.getEntnahmestelle(eId));
					request.setAttribute("zaehler", z);
					request.setAttribute("unit", EnergieArt.getEnergieArt(z.getEnergieArt()).getUnitMesswert());
				}
				break;
				
			case "verbrauch":
				forward = "verbrauch";
				VerbrauchsRechnerForm vrform = new VerbrauchsRechnerForm(request, df, d);
				List<Message> errors = new ArrayList<Message>();
				vrform.validate(errors);
				if(errors.size()!=0){
					//error
					request.setAttribute("verbrauchsForm", vrform);
					request.setAttribute("errors", errors);
				}else{
					Verbrauchsrechner vr = vrform.getVerbrauchsrechner();
					
					//den Verbrauch nur berechenen, wenn der Zähler und Entnahmetelle auch zur Person gehört!
					if (pdbuffer.checkZaehler(request.getParameter("zid")) &&
					    pdbuffer.checkEntnahmestelle(request.getParameter("eid"))){
						//den Verbrauch nur berechenen, wenn der Zähler und Entnahmetelle auch zur Person gehört!
						vr.setZ(pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))));
						if (vr.getZ().getmList().size()==0){
							//wenn die Messwerte leer sind dann von der DB lesen
							// es kann auch sein, dass der Zähler keine Messwerte hat... aber warum dann Verbauch anzeigen?!
							vr.getZ().setmList(mDao.listByZaehler(Integer.parseInt(request.getParameter("zid"))));
						}
						//hier wird der Verbauch berechnet auf basis der Parameter aus der VerbrauchsForm und an diese Übergeben.
						List<Verbrauchswert> vlsort = vr.ListVerbrauch();
						Collections.sort(vlsort, new VerbrauchswertComparator(VerbrauchswertComparator.descending, VerbrauchswertComparator.field_date_from));
						vrform.setVl(vlsort);
						Collections.sort(vlsort, new VerbrauchswertComparator(VerbrauchswertComparator.ascending, VerbrauchswertComparator.field_date_from));
						vrform.setVlchart(vlsort);
						request.setAttribute("verbrauchsForm", vrform);
						request.setAttribute("entnahmestelle",pdbuffer.getEntnahmestelle(Integer.parseInt(request.getParameter("eid"))));
						request.setAttribute("zaehler",pdbuffer.getZaehler(Integer.parseInt(request.getParameter("zid"))));
				    //sonst Redirect auf Welcome!
					}else{
						response.sendRedirect(request.getContextPath() + "/welcome.html");
						forward=null;
					}
				}
				break;
				
			case "logout":
				request.getSession().invalidate();
				logout = true;
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				break;
				
			default:
				break;
		}
		
		if(!logout){
			request.getSession().setAttribute("pdbuffer",pdbuffer);
		}

		
		if(forward!=null){
			request.setAttribute("forward", forward);
			request.getRequestDispatcher("/WEB-INF/jsp/"+forward+".jsp").forward(request, response);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
