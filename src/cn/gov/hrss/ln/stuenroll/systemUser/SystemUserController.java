package cn.gov.hrss.ln.stuenroll.systemUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.spring.Spring;
import com.jfinal.upload.UploadFile;
import cn.gov.hrss.ln.stuenroll.friends.I_FriendService;
import cn.gov.hrss.ln.stuenroll.systemUser.xml.SystemUser;
import cn.gov.hrss.ln.stuenroll.systemUser.xml.SystemUserParseService;
import jodd.util.CsvUtil;

@Spring("systemUserController")
public class SystemUserController extends Controller implements I_SystemUserController {
	private I_SystemUserService i_SystemUserService;
	private I_FriendService i_FriendService;

	private int rowsInPage;

	@RequiresPermissions("1_4")
	@Override
	public void searchUser() {
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;

		List<Record> records = i_SystemUserService.searchUser(start, length);

		renderJson("result", records);
	}

	@RequiresPermissions("1_4")
	@Override
	public void searchUserCount() {
		Long count = i_SystemUserService.searchUserCount();
		renderJson("result", count);
	}

	@RequiresPermissions("1_2")
	@Before(Tx.class)
	@Override
	public void changeUserStatus() {
		// 获取user_id
		String user_id_str = getPara("user_id");
		if (user_id_str == null) {
			return;
		}
		Long user_id = Long.valueOf(user_id_str);
		// 获取block与否
		String block = getPara("block");

		if (block == null) {
			return;
		}
		else if (block.equals("1")) {
			i_SystemUserService.changeUserStatus(user_id, true);
		}
		else if (block.equals("0")) {
			i_SystemUserService.changeUserStatus(user_id, false);
		}
		else {
			renderJson("result", false);
		}

		renderJson("result", true);
	}

	@RequiresPermissions("1_1")
	@Before(Tx.class)
	@Override
	public void addUser() {
		List params = new ArrayList();

		// get GlobalID
		String id = Db.queryStr("SELECT CAST(NEXT VALUE FOR MYCATSEQ_GLOBAL AS CHAR) AS `id`");
		params.add(id);

		// get username
		String username = getPara("username");
		params.add(username);

		// get password
		String password = getPara("password");
		params.add(password);

		// get name
		String name = getPara("name");
		params.add(name);

		// get pid
		String pid = getPara("pid");
		params.add(pid);

		// get sex
		String sex = getPara("sex");
		params.add(sex);

		// get organization_id
		String organization_id_str = getPara("organization_id");
		params.add(Long.valueOf(organization_id_str));

		// get tel
		String tel = getPara("tel");
		params.add(tel);

		// get email
		String email = getPara("email");
		params.add(email);

		// get question
		String question = getPara("question");
		params.add(question);

		// get answer
		String answer = getPara("answer");
		params.add(answer);

		// get status(block)
		String status_str = getPara("status");
		if (status_str.equals("开")) {
			params.add("0");
		}
		else if (status_str.equals("关")) {
			params.add("1");
		}
		else {
			renderJson("result", false);
		}

		// get role_id
		String role_id_str = getPara("role_id");
		params.add(Long.valueOf(role_id_str));

		// get position
		String position = getPara("position");
		params.add(position);

		// get photo_id
		String photo_id = getPara("photo_id");
		params.add(photo_id);

		i_SystemUserService.addUser(params);
		renderJson("result", true);
	}

	@RequiresPermissions("1_4")
	@Override
	public void searchAllOrganizationIdAndNames() {
		List<Record> records = i_SystemUserService.searchAllOrganizationIdAndNames();
		renderJson("result", records);
	}

	@RequiresPermissions("1_4")
	@Override
	public void searchAllRoleIdAndNames() {
		List<Record> records = i_SystemUserService.searchAllRoleIdAndNames();
		renderJson("result", records);
	}

	@RequiresPermissions("1_3")
	@Override
	public void searchUserById() {
		String user_id_str = getPara("user_id");
		if (user_id_str == null) {
			return;
		}
		Long user_id = Long.valueOf(user_id_str);
		Record record = i_SystemUserService.searchUserById(user_id);
		renderJson("result", record);
	}

	@RequiresPermissions("1_3")
	@Override
	public void modifyUserById() {
		Map map = new HashMap();
		map.put("answer", getPara("answer"));
		map.put("email", getPara("email"));
		map.put("name", getPara("name"));
		map.put("organization_id", getPara("organization_id"));
		map.put("password", getPara("password"));
		map.put("pid", getPara("pid"));
		map.put("position", getPara("position"));
		map.put("question", getPara("question"));
		map.put("role_id", getPara("role_id"));
		map.put("sex", getPara("sex"));
		map.put("block", getPara("block"));
		map.put("tel", getPara("tel"));
		map.put("username", getPara("username"));
		map.put("photo_id", getPara("photo_id"));
		String user_id_str = getPara("user_id");
		if (user_id_str == null) {
			renderJson("result", false);
		}
		Long user_id = Long.valueOf(user_id_str);
		int i = i_SystemUserService.modifyUserById(user_id, map);
		renderJson("result", i == 1);
	}

	@RequiresPermissions("1_2")
	@Before(Tx.class)
	@Override
	public void deleteUserById() {
		Long[] user_ids = getParaValuesToLong("user_ids");
		boolean bool = true;
		for (Long user_id : user_ids) {
			// 删除好友
			i_FriendService.deleteAllFriends(String.valueOf(user_id));
			// 删除用户
			int i = i_SystemUserService.deleteUserById(user_id);
			bool = bool && (i == 1);
		}
		renderJson("result", bool);
	}

	@RequiresPermissions("1_4")
	@Override
	public void exportUser() {
		try {
			String p = getPara("type");
			if (p == null) {
				renderJson("result", false);
			}
			List<Record> records = i_SystemUserService.searchAllUser();
			if (p.equals("Excel")) {
				// Excel方法
				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("users");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=export.xlsx");
				this.getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				// 遍历records
				for (int i = 0; i < records.size(); i++) {
					Record r = records.get(i);
					String block = null;
					Row row = sheet.createRow(i);
					row.createCell(0).setCellValue(r.getStr("id"));
					row.createCell(1).setCellValue(r.getStr("username"));
					row.createCell(2).setCellValue(r.getStr("password"));
					row.createCell(3).setCellValue(r.getStr("name"));
					row.createCell(4).setCellValue(r.getStr("pid"));
					row.createCell(5).setCellValue(r.getStr("sex"));
					row.createCell(6).setCellValue(r.getStr("organization_id"));
					row.createCell(7).setCellValue(r.getStr("tel"));
					row.createCell(8).setCellValue(r.getStr("wechat"));
					row.createCell(9).setCellValue(r.getStr("email"));
					row.createCell(10).setCellValue(r.getStr("question"));
					row.createCell(11).setCellValue(r.getStr("answer"));
					row.createCell(12).setCellValue(r.getBoolean("block") ? "1" : "0");
					row.createCell(13).setCellValue(r.getStr("role_id"));
					row.createCell(14).setCellValue(String.valueOf(r.getTimestamp("create_time")));
					row.createCell(15).setCellValue(r.getStr("position"));
					row.createCell(16).setCellValue(r.getStr("photo_id"));
				}
				workbook.write(this.getResponse().getOutputStream());
				this.renderNull();
			}
			else if (p.equals("CSV")) {
				// CSV
				this.getResponse().setContentType("test/csv;charset=utf-8");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=export.csv");
				// utf-8 BOM
				OutputStream os = this.getResponse().getOutputStream();
				byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
				os.write(bom);
				os.flush();
				PrintWriter pw = new PrintWriter(os);
				// 遍历records
				for (int i = 0; i < records.size(); i++) {
					Record r = records.get(i);
					List<String> data = new ArrayList<String>();
					data.add(r.getStr("id"));
					data.add(r.getStr("username"));
					data.add(r.getStr("password"));
					data.add(r.getStr("name"));
					data.add(r.getStr("pid"));
					data.add(r.getStr("sex"));
					data.add(r.getStr("organization_id"));
					data.add(r.getStr("tel"));
					data.add(r.getStr("wechat"));
					data.add(r.getStr("email"));
					data.add(r.getStr("question"));
					data.add(r.getStr("answer"));
					data.add(r.getBoolean("block") ? "1" : "0");
					data.add(r.getStr("role_id"));
					data.add(String.valueOf(r.getTimestamp("create_time")));
					data.add(r.getStr("position"));
					data.add(r.getStr("photo_id"));
					String line = CsvUtil.toCsvString(data.toArray());
					pw.write(line);
					pw.write("\n");
				}
				pw.flush();
				this.renderNull();
			}
			else if (p.equals("XML")) {
				// XML
				this.getResponse().setContentType("test/xml;charset=utf-8");
				this.getResponse().setHeader("Content-disposition", "attachment;filename=export.xml");
				// SAX 初始化
				Result resultxml = new StreamResult(this.getResponse().getOutputStream());
				SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
				TransformerHandler th = sff.newTransformerHandler();
				th.setResult(resultxml);
				Transformer transformer = th.getTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				// start document
				char[] indent_0 = "\n".toCharArray();
				char[] indent_4 = "\n    ".toCharArray();
				char[] indent_8 = "\n        ".toCharArray();
				th.startDocument();
				Attributes attr = new AttributesImpl();
				th.characters(indent_0, 0, indent_0.length);
				th.startElement("", "", "users", attr);

				for (int i = 0; i < records.size(); i++) {
					Record r = records.get(i);
					char[] id = r.getStr("id").toCharArray();
					char[] username = r.getStr("username").toCharArray();
					char[] password = r.getStr("password").toCharArray();
					char[] name = r.getStr("name").toCharArray();
					char[] pid = r.getStr("pid").toCharArray();
					char[] sex = r.getStr("sex").toCharArray();
					char[] organization_id = r.getStr("organization_id").toCharArray();
					char[] tel = r.getStr("tel").toCharArray();
					char[] wechat = "".toCharArray(); // wechat is null, set it
														// to ""
					char[] email = r.getStr("email").toCharArray();
					char[] question = r.getStr("question").toCharArray();
					char[] answer = r.getStr("answer").toCharArray();
					char[] block = (r.getBoolean("block") ? "1" : "0").toCharArray();
					char[] role_id = r.getStr("role_id").toCharArray();
					char[] create_time = String.valueOf(r.getTimestamp("create_time")).toCharArray();
					char[] position = r.getStr("position").toCharArray();
					char[] photo_id = r.getStr("photo_id").toCharArray();
					th.characters(indent_4, 0, indent_4.length);
					th.startElement("", "", "user", attr);

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "id", attr);
					th.characters(id, 0, id.length);
					th.endElement("", "", "id");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "username", attr);
					th.characters(username, 0, username.length);
					th.endElement("", "", "username");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "password", attr);
					th.characters(password, 0, password.length);
					th.endElement("", "", "password");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "name", attr);
					th.characters(name, 0, name.length);
					th.endElement("", "", "name");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "pid", attr);
					th.characters(pid, 0, pid.length);
					th.endElement("", "", "pid");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "sex", attr);
					th.characters(sex, 0, sex.length);
					th.endElement("", "", "sex");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "organization_id", attr);
					th.characters(organization_id, 0, organization_id.length);
					th.endElement("", "", "organization_id");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "tel", attr);
					th.characters(tel, 0, tel.length);
					th.endElement("", "", "tel");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "wechat", attr);
					th.characters(wechat, 0, wechat.length);
					th.endElement("", "", "wechat");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "email", attr);
					th.characters(email, 0, email.length);
					th.endElement("", "", "email");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "question", attr);
					th.characters(question, 0, question.length);
					th.endElement("", "", "question");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "answer", attr);
					th.characters(answer, 0, answer.length);
					th.endElement("", "", "answer");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "block", attr);
					th.characters(block, 0, block.length);
					th.endElement("", "", "block");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "role_id", attr);
					th.characters(role_id, 0, role_id.length);
					th.endElement("", "", "role_id");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "create_time", attr);
					th.characters(create_time, 0, create_time.length);
					th.endElement("", "", "create_time");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "position", attr);
					th.characters(position, 0, position.length);
					th.endElement("", "", "position");

					th.characters(indent_8, 0, indent_8.length);
					th.startElement("", "", "photo_id", attr);
					th.characters(photo_id, 0, photo_id.length);
					th.endElement("", "", "photo_id");

					th.characters(indent_4, 0, indent_4.length);
					th.endElement("", "", "user");
				}
				th.characters(indent_0, 0, indent_0.length);
				th.endElement("", "", "users");
				th.endDocument();
				this.renderNull();
			}
			else {
				renderJson("result", false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Map map = new HashMap();
			map.put("result", false);
			map.put("reason", e.toString());
			renderJson(map);
		}
	}

	@RequiresPermissions("1_3")
	@Before(Tx.class)
	@Override
	public void importUser() {
		UploadFile uploadFile = getFile("uploadFile");
		File file = uploadFile.getFile();
		String fileName = uploadFile.getFileName();
		try {
			if (fileName.endsWith(".xlsx")) {
				Workbook workbook = new XSSFWorkbook(file.getAbsolutePath());
				Sheet sheet = workbook.getSheet("users");
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					String id = row.getCell(0).getStringCellValue();
					String username = row.getCell(1).getStringCellValue();
					String password = row.getCell(2).getStringCellValue();
					String name = row.getCell(3).getStringCellValue();
					String pid = row.getCell(4).getStringCellValue();
					String sex = row.getCell(5).getStringCellValue();
					String organization_id = row.getCell(6).getStringCellValue();
					String tel = row.getCell(7).getStringCellValue();
					String wechat = ""; // wechat is not supported here
					String email = row.getCell(9).getStringCellValue();
					String question = row.getCell(10).getStringCellValue();
					String answer = row.getCell(11).getStringCellValue();
					String block = row.getCell(12).getStringCellValue();
					String role_id = row.getCell(13).getStringCellValue();
					String create_time = row.getCell(14).getStringCellValue();
					String position = row.getCell(15).getStringCellValue();
					String photo_id = row.getCell(16).getStringCellValue();
					List params = new ArrayList();
					params.add(id);
					params.add(username);
					params.add(password);
					params.add(name);
					params.add(pid);
					params.add(sex);
					params.add(organization_id);
					params.add(tel);
					// wechat can be null, but it is "" here, set it to null
					params.add("".equals(wechat) ? null : wechat);
					params.add(email);
					params.add(question);
					params.add(answer);
					params.add(block);
					params.add(role_id);
					params.add(create_time);
					params.add(position);
					params.add(photo_id);
					i_SystemUserService.importAllUser(params);
				}
			}
			else if (fileName.endsWith(".xml")) {
				List<SystemUser> systemUsers = new SystemUserParseService().getSystemUsers(new FileInputStream(file));
				if (systemUsers.size() == 0) {
					throw new Exception("xml文件格式不正确！");
				}
				for (SystemUser s : systemUsers) {
					i_SystemUserService.importAllUser(s.getParams());
				}
			}
			else if (fileName.endsWith(".csv")) {
				Scanner sc = new Scanner(file);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] line_s = line.split(",");
					List params = new ArrayList();
					for (int i = 0; i < line_s.length; i++) {
						String x = line_s[i];
						if (i == 0) {
							params.add(x.substring(3)); // remove BOM
						}
						else {
							if ("".equals(x)) { // wechat is "", set it to null
								params.add(null);
							}
							else {
								params.add(x);
							}
						}
					}
					i_SystemUserService.importAllUser(params);
				}
			}
			else {
				Map map = new HashMap();
				map.put("result", false);
				map.put("reason", "not implmented!");
				renderJson(map);
				return;
			}
			file.delete();
			renderJson("result", true);
		}
		catch (Exception e) {
			Map map = new HashMap();
			map.put("result", false);
			map.put("reason", e.toString());
			renderJson(map);
		}
		finally {
			file.delete();
		}
	}

	public I_SystemUserService getI_SystemUserService() {
		return i_SystemUserService;
	}

	public void setI_SystemUserService(I_SystemUserService i_SystemUserService) {
		this.i_SystemUserService = i_SystemUserService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}

	public I_FriendService getI_FriendService() {
		return i_FriendService;
	}

	public void setI_FriendService(I_FriendService i_FriendService) {
		this.i_FriendService = i_FriendService;
	}

}
