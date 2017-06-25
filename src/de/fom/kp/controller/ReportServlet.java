package de.fom.kp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;


import de.fom.kp.dao.JdbcEntnahmestelleDao;
import de.fom.kp.dao.JdbcMesswertDao;
import de.fom.kp.dao.JdbcPersonDao;
import de.fom.kp.dao.JdbcZaehlerDao;
import de.fom.kp.dao.MesswertDao;
import de.fom.kp.dao.PersonDao;
import de.fom.kp.dao.ZaehlerDao;
import de.fom.kp.persistence.Entnahmestelle;
import de.fom.kp.persistence.Messwert;
import de.fom.kp.persistence.MesswertAblesdatumComparator;
import de.fom.kp.persistence.PersonDataBuffer;
import de.fom.kp.persistence.Zaehler;


public class ReportServlet extends HttpServlet{
	
	private PersonDao dao;
	private ZaehlerDao zDao;
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
			zDao = new JdbcZaehlerDao(kp);
			mDao = new JdbcMesswertDao(kp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonDataBuffer pdbuffer = (PersonDataBuffer)request.getSession().getAttribute("pdbuffer");
		
		if(request.getParameter("id")!=null && StringUtils.isNotEmpty(request.getParameter("id"))){
			if (pdbuffer.checkZaehler(request.getParameter("id"))) {
				
				Locale locale = (Locale)request.getSession().getAttribute(LocaleFilter.KEY);
				ResourceBundle bundle = ResourceBundle.getBundle("MessageResources",locale);
				String pattern =  bundle.getString("i18n.datepattern");
				
				Zaehler z = zDao.read(Integer.parseInt(request.getParameter("id")));
				
				List<Messwert> mList = mDao.listByZaehler(Integer.parseInt(request.getParameter("id")));
				Collections.sort(mList, new MesswertAblesdatumComparator());
				
			    Workbook wb = new XSSFWorkbook();
			    CreationHelper createHelper = wb.getCreationHelper();
			    Sheet sheet = wb.createSheet("new sheet");

			    Row row0 = sheet.createRow((short)0);
			    row0.createCell(0).setCellValue(createHelper.createRichTextString("Zählerstände"));
			    sheet.addMergedRegion(new CellRangeAddress( 0, 0, 0, 1 ));
			    
			    Row row1 = sheet.createRow((short)1);
			    row1.createCell(0).setCellValue(createHelper.createRichTextString("ZählerNr:"));
			    row1.createCell(1).setCellValue(createHelper.createRichTextString(z.getZaehlerNr()));
			    
			    Row row2 = sheet.createRow((short)2);
			    row2.createCell(0).setCellValue(createHelper.createRichTextString("Energieart:"));
			    row2.createCell(1).setCellValue(createHelper.createRichTextString(z.getEnergieArt()));
			    
			    Row row3 = sheet.createRow((short)3);
			    
			    Row row4 = sheet.createRow((short)4);
			    row4.createCell(0).setCellValue(createHelper.createRichTextString("Ablesedatum"));
			    row4.createCell(1).setCellValue(createHelper.createRichTextString("Messwert"));
			    
			    CellStyle dateStyle = wb.createCellStyle();
			    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(pattern));
				Integer rowId = 5;
			    for (Messwert m : mList) {
					Row dataRow = sheet.createRow(rowId);
					Cell ablesedatum = dataRow.createCell(0);
					ablesedatum.setCellValue(m.getAblesedatum());
					ablesedatum.setCellStyle(dateStyle);
					dataRow.createCell(1).setCellValue(m.getMesswert());
					rowId++;
				}
			    
			    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			    response.addHeader("Content-Disposition", "attachment; filename\"Zählerstände.xlsx\"");
			    try(ByteArrayOutputStream bout = new ByteArrayOutputStream();){
				    wb.write(bout);
				    response.setContentLength(bout.size());
				    response.getOutputStream().write(bout.toByteArray());
				    bout.close();
			    }catch(Exception e) {
			    	wb.close();
			    	e.printStackTrace();
			    }
			    
			} else {
				response.sendRedirect(request.getContextPath() + "/welcome.html");
			}
		}
	}
}
