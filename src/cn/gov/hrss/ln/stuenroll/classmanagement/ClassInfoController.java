package cn.gov.hrss.ln.stuenroll.classmanagement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

@Spring("classInfoController")
public class ClassInfoController extends Controller implements I_ClassInfoController {

	private I_ClassInfoService i_ClassInfoService;
	private Long rowsInPage;
	private final Long HRSS = 738620600423157760L;

	public void searchAllClasses() {
		Map<String, Object> map = new HashMap<>();
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		if (organizationId != null && !HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		map.put("professionId", professionId);
		List<Record> records = i_ClassInfoService.searchAllClasses(map);
		renderJson("result",records);
	}

	@RequiresPermissions({ "8_4" })
	@Override
	public void searchClassInfo() {
		Long organizationId = getParaToLong("organizationId");
		String name = getPara("name");
		Integer year = getParaToInt("year");
		String organizationName = getPara("organizationName");
		String professionName = getPara("professionName");
		Integer classState = getParaToInt("classState");
		Integer stuState = getParaToInt("stuState");
		Long professionId = getParaToLong("professionId");
		Long classInfoId = getParaToLong("classInfoId");

		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		Long start = (page - 1) * rowsInPage;
		Long length = rowsInPage;

		Map<String, Object> map = new HashMap<String, Object>();
		if (organizationId != null && !HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		map.put("name", name);
		map.put("year", year);
		map.put("organizationName", organizationName);
		map.put("professionName", professionName);
		map.put("stuState", stuState);
		map.put("professionId", professionId);
		map.put("classInfoId", classInfoId);

		List<Record> records = null;
		if (classState == null || classState.intValue() == 0) {
			records = i_ClassInfoService.searchAllClassInfo(map, start, length);
		}
		else if (classState != null && classState.intValue() == 1) {
			map.put("classState", classState);
			records = i_ClassInfoService.searchUnarchiveClassInfo(map, start, length);
		}
		else if (classState != null && classState.intValue() == 2) {
			map.put("classState", classState);
			records = i_ClassInfoService.searchArchiveClassInfo(map, start, length);
		}

		renderJson("result", records);
	}

	@RequiresPermissions({ "8_4" })
	@Override
	public void searchClassAmount() {
		Long organizationId = getParaToLong("organizationId");
		String name = getPara("name");
		Integer year = getParaToInt("year");
		String organizationName = getPara("organizationName");
		String professionName = getPara("professionName");
		Integer classState = getParaToInt("classState");
		Integer stuState = getParaToInt("stuState");
		Long professionId = getParaToLong("professionId");
		Long classInfoId = getParaToLong("classInfoId");

		Map<String, Object> map = new HashMap<>();
		if (organizationId != null && !HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		map.put("name", name);
		map.put("year", year);
		map.put("organizationName", organizationName);
		map.put("professionName", professionName);
		map.put("stuState", stuState);
		map.put("professionId", professionId);
		map.put("classInfoId", classInfoId);

		long count = 0L;
		if (classState == null || classState.intValue() == 0) {
			count = i_ClassInfoService.searchAllClassAmount(map);
		}
		else if (classState != null && classState.intValue() == 1) {
			map.put("classState", classState);
			count = i_ClassInfoService.searchUnarchiveClassAmount(map);
		}
		else if (classState != null && classState.intValue() == 2) {
			map.put("classState", classState);
			count = i_ClassInfoService.searchArchiveClassAmount(map);
		}
		renderJson("result", count);
	}

	@RequiresPermissions({ "8_1" })
	@Override
	public void addClass() {
		String className = getPara("className");
		Integer year = getParaToInt("year");
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		Integer classState = getParaToInt("classState");
		Map<String, Object> map = new HashMap<>();
		map.put("className", className);
		map.put("year", year);
		map.put("organizationId", organizationId);
		map.put("professionId", professionId);
		map.put("classState", classState);

		int count = i_ClassInfoService.addClass(map);
		renderJson("result", count);
	}

	@RequiresPermissions({ "8_3" })
	@Override
	public void updateClassInfo() {
		Long classId = getParaToLong("classId");
		String name = getPara("className");
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		Integer year = getParaToInt("year");

		Map<String, Object> map = new HashMap<>();
		map.put("classId", classId);
		map.put("className", name);			
		map.put("organizationId", organizationId);
		map.put("professionId", professionId);
		map.put("year", year);

		int count = i_ClassInfoService.updateClassInfo(map);
		renderJson("result", count);

	}

	@RequiresPermissions({ "8_2" })
	@Override
	public void deleteClasses() {
		Long[] id = getParaValuesToLong("id");
		String username = getPara("username");
		long count = i_ClassInfoService.deleteClasses(id, username);
		renderJson("result", count);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void getStudentsInClass() {
		Long classId = getParaToLong("classId");
		Integer classState = getParaToInt("classState");
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		Long start = (page - 1) * 10;
		Long length = 10L;
		List<Record> records = null;
		if (classState != null) {
			if (classState.intValue() == 1) {
				records = i_ClassInfoService.getUnarchiveStudentsInClass(classId, start, length);
			} else if (classState.intValue() == 2) {
				records = i_ClassInfoService.getArchiveStudentsInClass(classId, start, length);
			} else {
				
			}
		}
		for (Record record : records) {
			Date date = record.getDate("birthday");
			record.remove("birthday");
			record.set("year", date.getYear() + 1900);
			record.set("month", date.getMonth());
			record.set("day", date.getDate());
		}
		renderJson("result", records);
	}
	
	@Override
	public void getStudentsAmountInClass() {
		Long classId = getParaToLong("classId");
		Integer classState = getParaToInt("classState");
		long count = 0L;
		if (classState != null) {
			if (classState.intValue() == 1) {
				count = i_ClassInfoService.getUnarchiveStudentsAmountInClass(classId);
			} else if (classState.intValue() == 2) {
				count = i_ClassInfoService.getArchiveStudentsAmountInClass(classId);
			} else {
				
			}
		}
		renderJson("result", count);
	}

	@RequiresPermissions({ "8_10" })
	@Override
	public void archiveClasses() {
		Long[] id = getParaValuesToLong("id");
		String username = getPara("username");
		long count = i_ClassInfoService.archiveClasses(id, username);
		renderJson("result", count);
	}
	
	@Override
	public void searchCallbackInfo() {
		Long classInfoId = getParaToLong("classInfoId");
		Long orgainzationId = getParaToLong("orgainzationId");
		Integer year = getParaToInt("year");
		Long professionId = getParaToLong("professionId");
		Map<String, Object> map = new HashMap<>();
		map.put("classInfoId", classInfoId);
		map.put("orgainzationId", orgainzationId);
		map.put("year", year);
		map.put("professionId", professionId);
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		Long start = (page - 1) * rowsInPage;
		Long length = rowsInPage;
		
		List<Record> records = i_ClassInfoService.searchCallbackInfo(map, start, length);
		renderJson("result", records);
	}
	
	@Override
	public void searchCallbackCount() {
		Long classInfoId = getParaToLong("classInfoId");
		Long orgainzationId = getParaToLong("orgainzationId");
		Integer year = getParaToInt("year");
		Long professionId = getParaToLong("professionId");
		Map<String, Object> map = new HashMap<>();
		map.put("classInfoId", classInfoId);
		map.put("orgainzationId", orgainzationId);
		map.put("year", year);
		map.put("professionId", professionId);
		long count = i_ClassInfoService.searchCallbackCount(map);
		renderJson("result", count);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void chooseStudentInClassForReview() {
		Long classInfoId = getParaToLong("classInfoId");
		Record record = i_ClassInfoService.chooseStudentInClassForReview(classInfoId);
		Date date = record.getDate("birthday");
		record.remove("birthday");
		record.set("year", date.getYear() + 1900);
		record.set("month", date.getMonth());
		record.set("day", date.getDate());
		renderJson("result", record);
	}

	public I_ClassInfoService getI_ClassInfoService() {
		return i_ClassInfoService;
	}

	public void setI_ClassInfoService(I_ClassInfoService i_ClassInfoService) {
		this.i_ClassInfoService = i_ClassInfoService;
	}

	public Long getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(Long rowsInPage) {
		this.rowsInPage = rowsInPage;
	}

}
