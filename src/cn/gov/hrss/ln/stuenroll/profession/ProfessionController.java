package cn.gov.hrss.ln.stuenroll.profession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
//最新
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;
import com.jfinal.upload.UploadFile;

import jodd.util.CsvUtil;


@Spring("professionController")
public class ProfessionController extends Controller implements I_ProfessionController {
	private I_ProfessionService i_ProfessionService;
	private int rowInPage;
	private final Long HRSS = 738620600423157760L; 

	@Override
	public void searchProfessionInYearAtDropDown() {
		Integer year = getParaToInt("year");
		List<Record> list = i_ProfessionService.searchProfessionInYearAtDropDown(year);
		renderJson("result", list);
	}

	@RequiresPermissions({ "6_4" })
	@Override
	public void searchProfessionStatistics() {
		HashMap map = new HashMap<>();
		String organization = getSessionAttr("organization");
		Long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网") == true) {
			organizationId = null;
		}
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowInPage;
		long length = rowInPage;
		map.put("organizationId", organizationId);

		List<Record> list = i_ProfessionService.searchProfessionStatistics(map,start, length);
		renderJson("result", list);
	}	
	@RequiresPermissions({"6_4"})
	@Override
	public void searchProfessionCount() {
		HashMap map = new HashMap<>();
		String organization = getSessionAttr("organization");
		Long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网") == true) {
			organizationId = null;
		}
		map.put("organizationId", organizationId);
		long count = i_ProfessionService.searchProfessionCount(map);
		renderJson("result",count);
	}	

	@RequiresPermissions({"6_4"})
	@Override
	public void searchAllProfession() {
		List<Record> list = i_ProfessionService.searchAllProfession();
		renderJson("result",list);
		
	}
	@RequiresPermissions({ "6_1" })
	@Override
	public void addProfession() {
		String name = getPara("name");
		HashMap map = new HashMap();
		map.put("name", name);
		int success = i_ProfessionService.addProfession(map);
		renderJson("result", success);
	}

	@RequiresPermissions({ "6_3" })
	@Override
	public void updateProfession() {
		String name = getPara("name");
		Long professionId = getParaToLong("id");
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("professionId", professionId);

		int success = i_ProfessionService.updateProfession(map);
		renderJson("result", success);

	}

	@RequiresPermissions({ "6_2" })
	@Override
	public void deleteProfessionById() {
		Long[] id = getParaValuesToLong("id");
		int i = i_ProfessionService.deleteProfessionById(id);
		renderJson("result", i);
	}

	
	@RequiresPermissions({"6_4"})
	@Override
	public void searchProfessionsWithOrganization() {
		Long organizationId = getParaToLong("organizationId");
		Integer year = getParaToInt("year");
		Long professionId = getParaToLong("professionId");
		Map<String, Object> map = new HashMap<>();
		if (organizationId != null && !HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		map.put("year", year);
		map.put("professionId", professionId);
		List<Record> brecords = i_ProfessionService.searchProfessionsWithConditions(map, 0L, 10000L);
		List<Record> records = new ArrayList<>();
		if (brecords.size() != 0) {
			String id = brecords.get(0).getStr("professionId");
			records.add(brecords.get(0));
			String id2 = "";
			Record record = null;
			for (int i = 1; i < brecords.size(); i++) {
				record = brecords.get(i);
				id2 = record.getStr("professionId");
				if (!id.equals(id2)) {
					id = id2;
					records.add(record);
				}
			}
		}
		renderJson("result", records);
	}
	
	@Override
	public void exportProfession() {
		String type = getPara("type");
		if (type == null) {
			renderJson("result", false);
		}
		List<Record> records = i_ProfessionService.searchAllProfession();
		try {
			if(type.equals("excel")){
				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("profession");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=export.xlsx");
				this.getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				int i = 0;
				for(Record record : records){
					Row row = sheet.createRow(i);
					i++;
					row.createCell(0).setCellValue(record.getStr("id"));
					row.createCell(1).setCellValue(record.getStr("name"));
				}
				workbook.write(this.getResponse().getOutputStream());
				renderNull();
			}
			else if(type.equals("csv")){
				this.getResponse().setHeader("Content-disposition", "attachment;filename=export.csv");
				this.getResponse().setContentType("test/csv;charset=utf-8");
				OutputStream os = this.getResponse().getOutputStream();
				byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
				os.write(bom);
				os.flush();
				PrintWriter pw = new PrintWriter(os);
				for (int i = 0; i < records.size(); i++) {
					Record record = records.get(i);
					List<String> data = new ArrayList<String>();
					data.add(record.getStr("id"));
					data.add(record.getStr("name"));
					String line = CsvUtil.toCsvString(data.toArray());
					pw.write(line);
					if (i != records.size()-1 ) {
						pw.write("\n");
					}
				}
				pw.flush();
				renderNull();
			}
			else if (type.equals("xml")) {
				this.getResponse().setContentType("test/xml;charset=utf-8");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=export.xml");
				//SAX初始化
				Result result = new StreamResult(getResponse().getOutputStream());
				SAXTransformerFactory stf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
				TransformerHandler th = stf.newTransformerHandler();
				th.setResult(result);
				Transformer transformer = th.getTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				// 开始写入文档
				th.startDocument();
				Attributes attr = new AttributesImpl();
				char[] indent_0 = "\n".toCharArray();
				char[] indent_4 = "\n    ".toCharArray();
				char[] indent_8 = "\n        ".toCharArray();
				th.characters(indent_0, 0, indent_0.length);
				th.startElement("", "", "professions", attr);
				for(Record record : records){
					char[] id = record.getStr("id").toCharArray();
					char[] name = record.getStr("name").toCharArray();
					th.characters(indent_4, 0, indent_4.length);
					th.startElement("", "", "profession", attr);
					
					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "id", attr);
					th.characters(id, 0, id.length);
					th.endElement("", "", "id");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "name", attr);
					th.characters(name, 0, name.length);
					th.endElement("", "", "name");
					
					th.characters(indent_4, 0, indent_4.length);
					th.endElement("", "", "profession");
				}
				th.characters(indent_0, 0, indent_0.length);
				th.endElement("", "", "professions");
				th.endDocument();
				renderNull();
			}
			else{
				renderJson("result", false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importProfession() {
		UploadFile uploadFile = getFile("uploadFile");
		File file = uploadFile.getFile();
		String fileName = uploadFile.getFileName();
		try {
			if (fileName.endsWith(".xlsx")) {
				Workbook workbook = new XSSFWorkbook(file.getAbsolutePath());
				Sheet sheet = workbook.getSheet("profession");
				Iterator<Row> iterator = sheet.iterator();
				while (iterator.hasNext()){
					Row row = iterator.next();
					String id = row.getCell(0).getStringCellValue();
					String name = row.getCell(1).getStringCellValue();
					List params = new ArrayList();
					params.add(Long.parseLong(id));
					params.add(name);
					i_ProfessionService.importProfession(params.toArray());
				}
			}
			else if (fileName.endsWith(".csv")) {
				Scanner sc = new Scanner(file);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] arr = line.split(",");
					List lists = new ArrayList();
					for (int i = 0; i < arr.length; i++){
						String x = arr[i];
						if (i == 0){
							lists.add(x.substring(3));
						}
						else{
							lists.add(x);
						}
					}
					i_ProfessionService.importProfession(lists.toArray());
				}
			}
			else{
				Map map = new HashMap();
				map.put("result", false);
				map.put("reason", "not implmented!");
				renderJson(map); 
			}
			file.delete();
			renderJson("result", true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public I_ProfessionService getI_ProfessionService() {
		return i_ProfessionService;
	}

	public void setI_ProfessionService(I_ProfessionService i_ProfessionService) {
		this.i_ProfessionService = i_ProfessionService;
	}

	public int getRowInPage() {
		return rowInPage;
	}

	public void setRowInPage(int rowInPage) {
		this.rowInPage = rowInPage;
	}
}
