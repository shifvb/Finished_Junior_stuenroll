package cn.gov.hrss.ln.stuenroll.register;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

import jodd.datetime.JDateTime;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

/**
 * 学员报名网络类
 */
@Spring("registerController")
@SuppressWarnings("all")
public class RegisterController extends Controller implements I_RegisterController {
	private I_RegisterService i_RegisterService;
	
	//@Before(RegisterValidator.class)
	public void hasRecord() {
		String pid = getPara("pid");
		boolean bool = i_RegisterService.hasRecord(pid);
		renderJson("result", bool);
	}
	
	//@Before(RegisterValidator.class)
	public void downloadRegisterReport() {
		renderNull();
		String pid = getPara("pid");
		Record record = i_RegisterService.searchRegisterRecord(pid);
		if(record == null){
			renderJson("result", "none");
			return;
		}
		String file = this.getClass().getResource("RegisterReport.jasper").getFile();
		HashMap<String, Object> map = new HashMap();
		map.put("name", record.getStr("name"));
		map.put("sex", record.getStr("sex"));
		map.put("nation", record.getStr("nation"));
		map.put("healthy", record.getStr("healthy"));
		map.put("pid", record.getStr("pid"));
		map.put("birthday", record.getStr("birthday"));
		map.put("politics", record.getStr("politics"));
		map.put("education", record.getStr("education"));
		map.put("graduate_school", record.getStr("graduate_school"));
		map.put("major", record.getStr("major"));
		map.put("graduate_date", record.getStr("graduate_date"));
		map.put("graduate_year", record.getLong("graduate_year").toString());
		map.put("tel", record.getStr("tel"));
		map.put("home_tel", record.getStr("home_tel"));
		map.put("email", record.getStr("email"));
		map.put("wechat", record.getStr("wechat"));
		map.put("resident_address", record.getStr("resident_address"));
		map.put("permanent_address", record.getStr("permanent_address"));
		map.put("home_address", record.getStr("home_address"));
		map.put("organization", record.getStr("organization"));
		map.put("profession", record.getStr("profession"));
		map.put("remark", record.getStr("remark"));
		map.put("liaison", record.getStr("liaison"));
		map.put("liaison_tel", record.getStr("liaison_tel"));
		map.put("abbreviation", record.getStr("abbreviation"));
		map.put("year", new JDateTime().toString("YYYY"));
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, map, new JREmptyDataSource());
			JRDocxExporter exporter = new JRDocxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			HttpServletResponse response = this.getResponse();
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-Disposition", "attachment;filename=report.docx");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporter.exportReport();
		}
		catch (Exception e) {
		}
		finally {
			renderNull();
		}
	}

	public I_RegisterService getI_RegisterService() {
		return i_RegisterService;
	}

	public void setI_RegisterService(I_RegisterService i_RegisterService) {
		this.i_RegisterService = i_RegisterService;
	}
	
}
