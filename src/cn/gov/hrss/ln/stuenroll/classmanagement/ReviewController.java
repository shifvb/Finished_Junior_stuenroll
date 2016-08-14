package cn.gov.hrss.ln.stuenroll.classmanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;
import com.jfinal.upload.UploadFile;

import cn.gov.hrss.ln.stuenroll.review.xml.Review;
import cn.gov.hrss.ln.stuenroll.review.xml.ReviewParseService;
import cn.gov.hrss.ln.stuenroll.tools.IllegalXMLDocumentException;

@Spring("reviewController")
public class ReviewController extends Controller implements I_ReviewController {

	private I_ReviewService i_ReviewService;
	private Long rowsPerPage;

	@Override
	public void searchReviewInfo() {
		Map<String, Object> map = new HashMap<>();
		Long reviewId = getParaToLong("reviewId");
		Long organizationId = getParaToLong("organizationId");
		Long classInfoId = getParaToLong("classInfoId");
		Long studentId = getParaToLong("studentId");
		String reviewee = getPara("reviewee");
		String reviewer = getPara("reviewer");
		String startDate = getPara("startDate");
		String endDate = getPara("endDate");
		String satisfaction = getPara("satisfaction");
		Integer reviewResult = getParaToInt("reviewResult");
		String duration = getPara("duration");

		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		Long start = (page - 1) * rowsPerPage;
		Long length = rowsPerPage;

		map.put("reviewId", reviewId);
		map.put("organizationId", organizationId);
		map.put("classInfoId", classInfoId);
		map.put("studentId", studentId);
		map.put("start", start);
		map.put("length", length);
		map.put("reviewee", reviewee);
		map.put("reviewer", reviewer);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("satisfaction", satisfaction);
		map.put("reviewResult", reviewResult);
		map.put("duration", duration);

		List<Record> records = i_ReviewService.searchReviewInfo(map, start, length);
		renderJson("result", records);

	}

	@Override
	public void searchReviewCounts() {
		Map<String, Object> map = new HashMap<>();
		Long organizationId = getParaToLong("organizationId");
		Long classInfoId = getParaToLong("classInfoId");
		Long studentId = getParaToLong("studentId");
		String reviewee = getPara("reviewee");
		String reviewer = getPara("reviewer");
		String startDate = getPara("startDate");
		String endDate = getPara("endDate");
		String satisfaction = getPara("satisfaction");
		String reviewResult = getPara("reviewResult");
		String duration = getPara("duration");

		map.put("organizationId", organizationId);
		map.put("classInfoId", classInfoId);
		map.put("studentId", studentId);
		map.put("reviewee", reviewee);
		map.put("reviewer", reviewer);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("satisfaction", satisfaction);
		map.put("reviewResult", reviewResult);
		map.put("duration", duration);

		long count = i_ReviewService.searchReviewCounts(map);
		renderJson("result", count);

	}

	@Override
	public void deleteReviewInfo() {
		Long[] id = getParaValuesToLong("id");
		String username = getSessionAttr("username").toString();
		long count = i_ReviewService.deleteReviewInfo(id, username);
		renderJson("result", count);
	}

	@Override
	public void addReviewInfo() {
		Long classInfoId = getParaToLong("classInfoId");
		Long studentId = getParaToLong("studentId");
		String reviewee = getPara("reviewee");
		String reviewer = getPara("reviewer");
		String satisfaction = getPara("satisfaction");
		String duration = getPara("duration");
		String content = getPara("content");
		String reviewResult = getPara("reviewResult");
		String username = getPara("username");
		Map<String, Object> map = new HashMap<>();
		map.put("classInfoId", classInfoId);
		map.put("studentId", studentId);
		map.put("reviewee", reviewee);
		map.put("reviewer", reviewer);
		map.put("satisfaction", satisfaction);
		map.put("content", content);
		map.put("reviewResult", reviewResult);
		map.put("duration", duration);

		long count = i_ReviewService.addReviewInfo(map, username);
		renderJson("result", count);
	}

	@Override
	public void updateReviewInfo() {
		Long id = getParaToLong("id");
		String reviewee = getPara("reviewee");
		String reviewer = getPara("reviewer");
		String satisfaction = getPara("satisfaction");
		String duration = getPara("duration");
		String content = getPara("content");
		String username = getPara("username");
		Map<String, Object> map = new HashMap<>();
		map.put("reviewee", reviewee);
		map.put("reviewer", reviewer);
		map.put("satisfaction", satisfaction);
		map.put("content", content);
		map.put("duration", duration);

		int count = i_ReviewService.updateReviewInfo(map, id, username);
		renderJson("result", count);
	}

	@Override
	public void searchReviewPeopleAmountInClass() {
		Long classInfoId = getParaToLong("classInfoId");
		Long studentId = getParaToLong("studentId");
		String reviewee = getPara("reviewee");
		String reviewer = getPara("reviewer");
		String time = getPara("time");
		String satisfaction = getPara("satisfaction");
		String duration = getPara("duration");
		String startDate = getPara("startDate");
		String endDate = getPara("endDate");
		Integer reviewResult = getParaToInt("reviewResult");
		Map<String, Object> map = new HashMap<>();
		map.put("classInfoId", classInfoId);
		map.put("studentId", studentId);
		map.put("reviewee", reviewee);
		map.put("reviewer", reviewer);
		map.put("time", time);
		map.put("satisfaction", satisfaction);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("duration", duration);
		map.put("reviewResult", reviewResult);

		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		Long start = (page - 1) * rowsPerPage;
		Long length = rowsPerPage;

		List<Record> records = i_ReviewService.searchReviewPeopleAmountInClass(map, start, length);
		renderJson("result", records);
	}

	@RequiresPermissions({ "8_5" })
	@Override
	public void importReviewInfo() {
		// 导入回访记录
		UploadFile uploadFile = getFile("uploadFile");
		File file = uploadFile.getFile();
		String fileName = uploadFile.getFileName();
		String username = getPara("username");
		try {
			if (fileName.endsWith(".xml")) {
				List<Review> reviews = new ReviewParseService().getReviews(file);
				if (reviews == null) {
					throw new SAXException("解析错误", new IllegalXMLDocumentException("文档不合规范"));
				}
				for (Review review : reviews) {
					i_ReviewService.addReviewInfo(review.getParasMap(), username);
				}
				Map<String, String> map = new HashMap<>();
				map.put("count", "1");
				map.put("reason", "文档符合规范");
				renderJson("result", map);
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e) {
			Map<String, String> map = new HashMap<>();
			map.put("count", "0");
			map.put("reason", "文档不合规范");
			renderJson("result", map);
			e.printStackTrace();
		}
	}

	@RequiresPermissions({ "8_6" })
	@Override
	public void exportReviewInfo() {
		// 导出回访记录
		InputStream inputStream = null;
		try {
			String extension = getPara("type");
			if (extension == null) {
				renderJson("result", false);
			}
			List<Record> records = i_ReviewService.searchAllReview();
			if ("Excel".equals(extension)) {
				// Excel方法
				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("reviews");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=review.xlsx");
				this.getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				int i = 0;
				for (Record record : records) {
					Row row = sheet.createRow(i);
					row.createCell(0).setCellValue(record.getStr("id"));
					row.createCell(1).setCellValue(record.getStr("reviewer"));
					row.createCell(2).setCellValue(record.getStr("reviewee"));
					row.createCell(3).setCellValue(String.valueOf(record.getTimestamp("time")));
					row.createCell(4).setCellValue(record.getStr("duration"));
					row.createCell(5).setCellValue(record.getStr("satisfaction"));
					row.createCell(6).setCellValue(record.getBoolean("reviewResult") == false ? "打通" : "未打通");
					row.createCell(7).setCellValue(record.getStr("content"));
					row.createCell(8).setCellValue(record.getStr("classinfo_id"));
					row.createCell(9).setCellValue(record.getStr("student_id"));
					i++;
				}
				workbook.write(this.getResponse().getOutputStream());
				this.renderNull();
			}
			else if ("XML".equals(extension)) {
				// XML
				this.getResponse().setContentType("test/xml;charset=utf-8");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=review.xml");
				// SAX 初始化
				Result resultxml = new StreamResult(this.getResponse().getOutputStream());
				SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
				TransformerHandler handler = sff.newTransformerHandler();
				handler.setResult(resultxml);
				Transformer transformer = handler.getTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				// start document
				char[] indent_0 = "\n".toCharArray();
				char[] indent_4 = "\n    ".toCharArray();
				char[] indent_8 = "\n        ".toCharArray();
				handler.startDocument();
				Attributes attr = new AttributesImpl();
				AttributesImpl atts = new AttributesImpl();
				int indent_0_length = indent_0.length;
				int indent_4_length = indent_4.length;
				int indent_8_length = indent_8.length;
				handler.characters(indent_0, 0, indent_0_length);
				handler.startElement("", "", "reviews", attr);
				for (Record record : records) {
					String id = record.getStr("id");
					char[] reviewer = record.getStr("reviewer").toCharArray();
					char[] reviewee = record.getStr("reviewee").toCharArray();
					char[] time = String.valueOf(record.getTimestamp("time")).toCharArray();
					char[] duration = record.getStr("duration").toCharArray();
					char[] satisfaction = record.getStr("satisfaction").toCharArray();
					char[] reviewResult = (record.getBoolean("reviewResult") == false ? "打通" : "未打通").toCharArray();
					char[] content = record.getStr("content").toCharArray();
					char[] classinfo_id = record.getStr("classinfo_id").toCharArray();
					char[] student_id = record.getStr("student_id").toCharArray();
					handler.characters(indent_4, 0, indent_4_length);
					atts.clear();
					atts.addAttribute("", "", "id", "", id);
					handler.startElement("", "", "review", atts);

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "reviewer", attr);
					handler.characters(reviewer, 0, reviewer.length);
					handler.endElement("", "", "reviewer");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "reviewee", attr);
					handler.characters(reviewee, 0, reviewee.length);
					handler.endElement("", "", "reviewee");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "time", attr);
					handler.characters(time, 0, time.length);
					handler.endElement("", "", "time");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "duration", attr);
					handler.characters(duration, 0, duration.length);
					handler.endElement("", "", "duration");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "satisfaction", attr);
					handler.characters(satisfaction, 0, satisfaction.length);
					handler.endElement("", "", "satisfaction");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "reviewResult", attr);
					handler.characters(reviewResult, 0, reviewResult.length);
					handler.endElement("", "", "reviewResult");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "content", attr);
					handler.characters(content, 0, content.length);
					handler.endElement("", "", "content");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "classInfoId", attr);
					handler.characters(classinfo_id, 0, classinfo_id.length);
					handler.endElement("", "", "classInfoId");

					handler.characters(indent_8, 0, indent_8_length);
					handler.startElement("", "", "studentId", attr);
					handler.characters(student_id, 0, student_id.length);
					handler.endElement("", "", "studentId");

					handler.characters(indent_4, 0, indent_4_length);
					handler.endElement("", "", "review");

				}
				handler.characters(indent_0, 0, indent_0_length);
				handler.endElement("", "", "reviews");
				handler.endDocument();
				this.renderNull();
			}
			else if ("JSON".equals(extension)) {
				this.getResponse().setHeader("Content-disposition", "attachment;filename=review.json");
				this.getResponse().setContentType("text/plain");
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for (Record record : records) {
					JSONObject jObject = new JSONObject();
					jObject.put("id", record.getStr("id")).put("reviewer", record.getStr("reviewer"))
							.put("reviewee", record.getStr("reviewee")).put("time", record.getTimestamp("time"))
							.put("duration", record.getStr("duration"))
							.put("reviewResult", record.getBoolean("reviewResult") == false ? "打通" : "未打通")
							.put("satisfaction", record.getStr("satisfaction")).put("content", record.getStr("content"))
							.put("classinfo_id", record.getStr("classinfo_id")).put("student_id", record.getStr("student_id"));
					jsonArray.put(jObject);
				}
				jsonObject.put("result", jsonArray);
				File file = new File(this.getRequest().getSession().getServletContext().getRealPath("") + "review.json");
				FileWriter fWriter = new FileWriter(file);
				fWriter.write(jsonObject.toString());
				fWriter.close();
				inputStream = new FileInputStream(file);
				IOUtils.copy(inputStream, this.getResponse().getOutputStream());
				this.renderNull();
			}
			else {

			}
		}
		catch (IOException | SAXException | TransformerConfigurationException e) {
			e.printStackTrace();
		}
		finally {
			IOUtils.closeQuietly(inputStream);
		}

	}

	public I_ReviewService getI_ReviewService() {
		return i_ReviewService;
	}

	public void setI_ReviewService(I_ReviewService i_ReviewService) {
		this.i_ReviewService = i_ReviewService;
	}

	public Long getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(Long rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

}
