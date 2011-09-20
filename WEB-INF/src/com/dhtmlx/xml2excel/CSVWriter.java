package com.dhtmlx.xml2excel;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class CSVWriter {
	int rows = 0;
	int cols = 0;
	public void generate(String xml, HttpServletResponse resp) throws IOException {
		CSVxml data = new CSVxml(xml);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=grid.csv");
		resp.setHeader("Cache-Control", "max-age=0");
	
		String[] csv;
		PrintWriter writer = resp.getWriter();
		
		csv = data.getHeader();
		while(csv != null){			
			writer.append(dataAsString(csv));
			csv = data.getHeader();
		}
		
		csv = data.getRow();
		while(csv != null){			
			writer.append(dataAsString(csv));
			csv = data.getRow();
		}
		
		csv = data.getFooter();
		while(csv != null){			
			writer.append(dataAsString(csv));
			writer.flush();
			csv = data.getFooter();
		}
		
		writer.flush();
		writer.close();
	}

	private String dataAsString(String[] csv) {
		if (csv.length == 0) return "";
		
		StringBuffer buff = new StringBuffer();
		for ( int i=0; i<csv.length; i++){
			if (i>0)
				buff.append(";");
			if (!csv[i].equals("")){
				buff.append("\"");
				buff.append(csv[i].replace("\"", "\"\""));
				buff.append("\"");				
			}
		}	
		buff.append("\n");
		return buff.toString();
	}

	public int getColsStat() {
		return cols;
	}

	public int getRowsStat() {
		return rows;
	}

}