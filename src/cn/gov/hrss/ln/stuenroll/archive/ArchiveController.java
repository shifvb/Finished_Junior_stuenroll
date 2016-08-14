package cn.gov.hrss.ln.stuenroll.archive;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

@Spring("archiveController")
public class ArchiveController extends Controller implements I_ArchiveController{

	private I_ArchiveService i_ArchiveService;
	private int rowsInPage;

	@RequiresPermissions({"4_4"})
	@Override
	public void searchArchive() {
		Long id = getParaToLong("id");
		String name = getPara("name");
		String pid = getPara("pid");
		String year = getPara("year");
		String sex = getPara("sex");
		String education = getPara("education");
		Long organizationID = getParaToLong("organizationID");
		Long professionId = getParaToLong("professionId");
		Long classInfoId = getParaToLong("classInfoId");
		Long stateId = getParaToLong("stateId");
		Integer rowInPage = getParaToInt("rowsInPage");
		
		String organization = getSessionAttr("organization");
		if(organization.equals("辽宁省就业网") == false){
			organizationID = getSessionAttr("organizationId");
		}
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("name", name);
		map.put("pid", pid);
		map.put("year", year);
		map.put("sex", sex);
		map.put("education", education);
		map.put("organizationID", organizationID);
		map.put("professionId", professionId);
		map.put("classInfoId", classInfoId);
		map.put("stateId", stateId);
		
		Long page = getParaToLong("page");
		if(page == null){
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length;
		if (rowInPage == null) {
			length = rowsInPage;
		} else {
			length = rowInPage;
		}
		List<Record> list = i_ArchiveService.searchArchive(map, start, length);
		renderJson("result",list);
	}
	
	@RequiresPermissions({"4_4"})
	@Override
	public void searchArchiveCount() {
		String name = getPara("name");
		String pid = getPara("pid");
		String year = getPara("year");
		String sex = getPara("sex");
		String education = getPara("education");
		Long organizationId = getParaToLong("organizationID");
		Long professionId = getParaToLong("professionId");
		Long classInfoId = getParaToLong("classInfoId");
		Long stateId = getParaToLong("stateId");
		
		String organization = getSessionAttr("organization");
		if(organization.equals("辽宁省就业网") == false){
			organizationId = getSessionAttr("organizationId");
		}
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("pid", pid);
		map.put("year", year);
		map.put("sex", sex);
		map.put("education", education);
		map.put("organizationId", organizationId);
		map.put("professionId", professionId);
		map.put("classInfoId", classInfoId);
		map.put("stateId", stateId);
		
		long count = i_ArchiveService.searchArchiveCount(map);
		renderJson("result",count);
	}

	@RequiresPermissions({"4_2"})
	@Override
	public void addArchive() {
		String name = getPara("name");
		String sex = getPara("sex");
		String nation = getPara("nation");
		String pid = getPara("pid");
		String graduateSchool = getPara("graduateSchool");
		String graduateYear = getPara("graduateYear");
		String graduateDate = getPara("graduateDate");
		String education = getPara("education");
		String major = getPara("major");
		String healthy = getPara("healthy");
		String politics = getPara("politics");
		String birthday = getPara("birthday");
		String resident_address = getPara("resident_address");
		String home_address = getPara("home_address");
		String permanent_address = getPara("permanent_address");
		String tel = getPara("tel");
		String home_tel = getPara("home_tel");
		String email = getPara("email");	
		long organizationID = getParaToLong("organizationID");
		long professionId = getParaToLong("professionId");
		long stateId = getParaToLong("stateId");
		long classInfoId = getParaToLong("classInfoId");
		String place = getPara("place");
		String year = getPara("year");
		String remark = getPara("remark");
		
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("sex", sex);
		map.put("nation", nation);
		map.put("pid", pid);
		map.put("graduateSchool", graduateSchool);
		map.put("graduateYear", graduateYear);
		map.put("graduateDate", graduateDate);
		map.put("education", education);
		map.put("major", major);
		map.put("healthy", healthy);
		map.put("politics", politics);
		map.put("birthday", birthday);
		map.put("resident_address", resident_address);
		map.put("home_address", home_address);
		map.put("permanent_address", permanent_address);
		map.put("tel", tel);
		map.put("home_tel", home_tel);
		map.put("email", email);
		map.put("organizationID", organizationID);
		map.put("professionId", professionId);
		map.put("stateId", stateId);
		map.put("classInfoId", classInfoId);
		map.put("place", place);
		map.put("year", year);
		map.put("remark", remark);
		
		int count = i_ArchiveService.addArchive(map);
		renderJson("result",count);
	}

	@RequiresPermissions({"4_3"})
	@Override
	public void updateArchive() {
		String id = getPara("id");
		String name = getPara("name");
		String sex = getPara("sex");
		String nation = getPara("nation");
		String pid = getPara("pid");
		String graduateSchool = getPara("graduateSchool");
		String graduateYear = getPara("graduateYear");
		String graduateDate = getPara("graduateDate");
		String education = getPara("education");
		String major = getPara("major");
		String healthy = getPara("healthy");
		String politics = getPara("politics");
		String birthday = getPara("birthday");
		String resident_address = getPara("resident_address");
		String home_address = getPara("home_address");
		String permanent_address = getPara("permanent_address");
		String tel = getPara("tel");
		String home_tel = getPara("home_tel");
		String email = getPara("email");	
		long organizationID = getParaToLong("organizationID");
		long professionId = getParaToLong("professionId");
		long stateId = getParaToLong("stateId");
		long classInfoId = getParaToLong("classInfoId");
		String place = getPara("place");
		String year = getPara("year");
		String remark = getPara("remark");
		
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("name", name);
		map.put("sex", sex);
		map.put("nation", nation);
		map.put("pid", pid);
		map.put("graduateSchool", graduateSchool);
		map.put("graduateYear", graduateYear);
		map.put("graduateDate", graduateDate);
		map.put("education", education);
		map.put("major", major);
		map.put("healthy", healthy);
		map.put("politics", politics);
		map.put("birthday", birthday);
		map.put("resident_address", resident_address);
		map.put("home_address", home_address);
		map.put("permanent_address", permanent_address);
		map.put("tel", tel);
		map.put("home_tel", home_tel);
		map.put("email", email);
		map.put("organizationID", organizationID);
		map.put("professionId", professionId);
		map.put("stateId", stateId);
		map.put("classInfoId", classInfoId);
		map.put("place", place);
		map.put("year", year);
		map.put("remark", remark);
		
		int count = i_ArchiveService.updateArchive(map);
		renderJson("result",count);
	}
	

	@RequiresPermissions({"4_2"})
	@Override
	public void deleteArchiveById() {
		Long[] id = getParaValuesToLong("id");
		int count = i_ArchiveService.deleteArchiveById(id);
		renderJson("result",count);
	}
	@RequiresPermissions({"8_4"})
	@Override
	public void searchStudentState() {
		List<Record> list = i_ArchiveService.searchStudentState();
		renderJson("result",list);
	}

	@RequiresPermissions({"4_4"})
	@Override
	public void searchClassInfo() {
		List<Record> list = i_ArchiveService.searchClassInfo();
		renderJson("result",list);
		
	}
	public I_ArchiveService getI_ArchiveService() {
		return i_ArchiveService;
	}

	public void setI_ArchiveService(I_ArchiveService i_ArchiveService) {
		this.i_ArchiveService = i_ArchiveService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}
}
