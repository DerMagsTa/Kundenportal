package de.fom.kp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;


import de.fom.kp.dao.*;
import de.fom.kp.model.*;
import de.fom.kp.persistence.*;



public class ReportServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ZaehlerDao zDao;
	private MesswertDao mDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {

		// DB Verbindungen zur Verfügung stellen
		try {
			String s = config.getServletContext().getInitParameter("datasource");
			InitialContext initialContext = new InitialContext();
			DataSource kp = (DataSource) initialContext.lookup(s);			
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
				SimpleDateFormat df = new SimpleDateFormat( bundle.getString("i18n.datepattern") );
				
				NumberFormat d = NumberFormat.getNumberInstance(locale);
				d.setParseIntegerOnly(false);
				
				Zaehler z = zDao.read(Integer.parseInt(request.getParameter("id")));
				Workbook wb;
				CreationHelper createHelper;
				Sheet sheet;
				Row row;
				Row dataRow;
				CellStyle dateStyle;
				switch (request.getPathInfo()) {
				
				case "/zaehlerstaende":
					List<Messwert> mList = mDao.listByZaehler(Integer.parseInt(request.getParameter("id")));
					Collections.sort(mList, new MesswertAblesdatumComparator());
					
				    wb = new XSSFWorkbook();
				    createHelper = wb.getCreationHelper();
				    sheet = wb.createSheet("new sheet");
				    
				    //Überschriften
				    row = sheet.createRow((short)0);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("Zählerstände"));
				    sheet.addMergedRegion(new CellRangeAddress( 0, 0, 0, 1 ));
				    
				    row = sheet.createRow((short)1);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("ZählerNr:"));
				    row.createCell(1).setCellValue(createHelper.createRichTextString(z.getZaehlerNr()));
				    
				    row = sheet.createRow((short)2);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("Energieart:"));
				    row.createCell(1).setCellValue(createHelper.createRichTextString(z.getEnergieArt()));
				    
				    row = sheet.createRow((short)3);
				    
				    row = sheet.createRow((short)4);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("Ablesedatum"));
				    row.createCell(1).setCellValue(createHelper.createRichTextString("Messwert"));
				    
				    //Datentabelle
				    dateStyle = wb.createCellStyle();
				    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(df.toPattern()));
					Integer rowId = 5;
				    for (Messwert m : mList) {
						dataRow = sheet.createRow(rowId);
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
				    break;
				    
				case "/verbrauch":
					
					VerbrauchsRechnerForm vrform = new VerbrauchsRechnerForm(df, d);
					vrform.setMode("each");
					Verbrauchsrechner vr = vrform.getVerbrauchsrechner();
					
					vr.setZ(z);
					vr.getZ().setmList(mDao.listByZaehler(z.getId()));
					vrform.setVl(vr.ListVerbrauch());
					
					wb = new XSSFWorkbook();
				    createHelper = wb.getCreationHelper();
				    sheet = wb.createSheet("new sheet");
	
				    //Überschriften
				    row = sheet.createRow((short)0);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("Verbrauchswerte"));
				    sheet.addMergedRegion(new CellRangeAddress( 0, 0, 0, 1 ));
				    
				    row = sheet.createRow((short)1);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("ZählerNr:"));
				    row.createCell(1).setCellValue(createHelper.createRichTextString(z.getZaehlerNr()));
				    
				    row = sheet.createRow((short)2);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("Energieart:"));
				    row.createCell(1).setCellValue(createHelper.createRichTextString(z.getEnergieArt()));
				    
				    row = sheet.createRow((short)3);
				    
				    row = sheet.createRow((short)4);
				    row.createCell(0).setCellValue(createHelper.createRichTextString("Datum von"));
				    row.createCell(1).setCellValue(createHelper.createRichTextString("Datum bis"));
				    row.createCell(2).setCellValue(createHelper.createRichTextString("Verbrauch"));
				    row.createCell(3).setCellValue(createHelper.createRichTextString("Einheit"));
				    
				    //Datentabelle
				    dateStyle = wb.createCellStyle();
				    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(df.toPattern()));
					rowId = 5;
				    for (Verbrauchswert v : vr.ListVerbrauch()) {
						dataRow = sheet.createRow(rowId);
						Cell datumvon = dataRow.createCell(0);
						datumvon.setCellValue(v.getFrom());
						datumvon.setCellStyle(dateStyle);
						Cell datumbis = dataRow.createCell(1);
						datumbis.setCellValue(v.getTo());
						datumbis.setCellStyle(dateStyle);
						dataRow.createCell(2).setCellValue(v.getVerbrauch());
						dataRow.createCell(3).setCellValue(v.getUnit());
						rowId++;
					}
				    
				    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				    response.addHeader("Content-Disposition", "attachment; filename\"Verbräuche.xlsx\"");
				    try(ByteArrayOutputStream bout = new ByteArrayOutputStream();){
					    wb.write(bout);
					    response.setContentLength(bout.size());
					    response.getOutputStream().write(bout.toByteArray());
					    bout.close();
				    }catch(Exception e) {
				    	wb.close();
				    	e.printStackTrace();
				    }
					
					break;
					
				default:
					break;
				}
			    
			} else {
				response.sendRedirect(request.getContextPath() + "/welcome.html");
			}
		}
	}
}
