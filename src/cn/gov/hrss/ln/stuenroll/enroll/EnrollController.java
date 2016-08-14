package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

import cn.gov.hrss.ln.stuenroll.validator.EnrollValidator;
import jodd.datetime.JDateTime;

/**
 * 报名管理网络类
 * @author York Chu
 *
 */
@SuppressWarnings("all")
@Spring("enrollController")
public class EnrollController extends Controller implements I_EnrollController {
	private I_EnrollService i_EnrollService;
	private int rowsInPage;
	
	// 查询报名记录
	@RequiresPermissions({ "3_4" })
	public void searchEnroll() {
		String name = getPara("name");
		String pid = getPara("pid");
		Integer year = getParaToInt("year");
		String sex = getPara("sex");
		String education = getPara("education");
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		Long classinfoId = getParaToLong("classinfoId");
		Long stateId = getParaToLong("stateId");
		String flag=getPara("flag");

		String organization = getSessionAttr("organization"); // HttpSession中的组织名称
		// 如果不是就业网用户那么Java程序从HttpSession中提取机构ID，强制使用这个机构ID查询数据，所以用户只能看到自己机构的数据
		if (organization.equals("辽宁省就业网") == false) {
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
		map.put("classinfoId", classinfoId);
		map.put("stateId", stateId);
		map.put("flag", flag);
		
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;

		List<Record> list = i_EnrollService.searchEnroll(map, start, length);
		renderJson("result", list);
	}

	// 查询报名记录总数
	@RequiresPermissions({ "3_4" })
	public void searchEnrollCount() {
		String name = getPara("name");
		String pid = getPara("pid");
		Integer year = getParaToInt("year");
		String sex = getPara("sex");
		String education = getPara("education");
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		Long classinfoId = getParaToLong("classinfoId");
		Long stateId = getParaToLong("stateId");
		String flag=getPara("flag");
		String organization = getSessionAttr("organization"); // HttpSession中的组织名称
		// 如果不是就业网用户那么Java程序从HttpSession中提取机构ID，强制使用这个机构ID查询数据，所以用户只能看到自己机构的数据
		if (organization.equals("辽宁省就业网") == false) {
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
		map.put("classinfoId", classinfoId);
		map.put("stateId", stateId);
		map.put("flag", flag);
		
		long count = i_EnrollService.searchEnrollCount(map);
		renderJson("result", count);
	}

	// 根据ID删除记录
	@RequiresPermissions({ "3_2" })
	public void deleteById() {
		Long[] id = getParaValuesToLong("id");
		int i = i_EnrollService.deleteById(id);
		renderJson("deleteRows", i);
	}

	// 前台报名注册
	@Before(EnrollValidator.class)
	public void register() {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", this.getPara("name"));
		map.put("sex", this.getPara("sex"));
		map.put("nation", this.getPara("nation"));
		map.put("pid", this.getPara("pid"));
		map.put("graduateSchool", this.getPara("graduteSchool"));
		map.put("graduateYear", this.getParaToInt("graduateYear"));
		map.put("graduateDate", this.getPara("graduateDate"));
		map.put("education", this.getPara("education"));
		map.put("major", this.getPara("major"));
		map.put("healthy", this.getPara("healthy"));
		map.put("politics", this.getPara("politics"));
		map.put("birthday", this.getPara("birthday"));
		map.put("residentAddress", this.getPara("residentAddress"));
		map.put("permanentAddress", this.getPara("permanentAddress"));
		map.put("tel", this.getPara("tel"));
		map.put("email", this.getPara("email"));
		map.put("wechat", this.getPara("wechat"));
		map.put("homeAddress", this.getPara("homeAddress"));
		map.put("homeTel", this.getPara("homeTel"));
		map.put("professionId", this.getParaToLong("professionId"));
		map.put("organizationId", this.getParaToLong("organizationId"));
		map.put("place", this.getPara("place"));
		map.put("remark", this.getPara("remark"));
		map.put("classinfoId", null);
		map.put("stateId", 1);
		i_EnrollService.register(map);
		setAttr("result", true);
		renderJson("result", true);
	}

	// 添加报名记录
	@RequiresPermissions({ "3_15" })
	public void addEnroll() {
		ArrayList param = new ArrayList();
		String name = getPara("name");
		String pid = getPara("pid");
		Integer graduteYear = getParaToInt("graduteYear");
		String sex = getPara("sex");
		String education = getPara("education");
		String nation = getPara("nation");
		String graduteSchool = getPara("graduteSchool");
		String graduteDate = getPara("graduteDate");
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		String major=getPara("graduteCurriculum");
		String politics=getPara("politics");
		String healthy=getPara("healthy");
		String place=getPara("place");
		String residentAddress=getPara("residentAddress");
		String homeAddress=getPara("homeAddress");
		String birthday=getPara("birthday");
		String permanentAddress=getPara("permanentAddress");
		String tel=getPara("tel");
		String homeTel=getPara("homeTel");
		String email=getPara("email");
		// 根据SQL语句确定数组的顺序
		param.add(name);
		param.add(sex);
		param.add(nation);
		param.add(pid);
		param.add(graduteSchool);
		param.add(graduteYear);
		param.add(graduteDate);
		param.add(education);
		param.add(major);
		param.add(healthy);
		param.add(politics);
		param.add(birthday);
		param.add(residentAddress);
		param.add(permanentAddress);
		param.add(homeAddress);
		param.add(tel);
		param.add(homeTel);
		param.add(email);
		param.add(professionId);
		param.add(1);
		param.add(organizationId);
		param.add(place);
		JDateTime dateTime = new JDateTime();
		param.add(dateTime.toString("YYYY"));
		param.add(dateTime.getTimeInMillis());
		int count = i_EnrollService.addEnroll(param.toArray());
		renderJson("result", count);
	}
	
	// 判断身份证号码是否重复
	public void isEnrollEligible() {
		String pid=getPara("pid");
		if(pid!=null&&pid.length()>0){
			boolean bool=i_EnrollService.isEnrollEligible(pid);
			renderJson("result", bool);
		}else{
			renderJson("result", false);
		}
	}
	
	// 根据ID查找对应的报名记录
	@RequiresPermissions({ "3_4" })
	public void searchEnrollById() {
		long id = getParaToLong("id");
		Record record = i_EnrollService.searchEnrollById(id);
		renderJson("result", record);
	}
	
	// 更新报名记录
	@RequiresPermissions({ "3_3" })
	public void modifyEnroll() {
		HashMap map = new HashMap();
		long id = getParaToLong("id");
		String name = getPara("name");
		String pid = getPara("pid");
		Integer graduteYear = getParaToInt("graduteYear");
		String sex = getPara("sex");
		String education = getPara("education");	
		String nation = getPara("nation");
		String graduteSchool = getPara("graduteSchool");
		String graduteDate = getPara("graduteDate");
		Long organizationId = getParaToLong("organizationId");
		Long professionId = getParaToLong("professionId");
		String major=getPara("graduteCurriculum");
		String politics=getPara("politics");
		String healthy=getPara("healthy");
		String place=getPara("place");
		String residentAddress=getPara("residentAddress");
		String homeAddress=getPara("homeAddress");
		String birthday=getPara("birthday");
		String permanentAddress=getPara("permanentAddress");
		String tel=getPara("tel");
		String homeTel=getPara("homeTel");
		String email=getPara("email");
		map.put("name", name);
		map.put("sex", sex);
		map.put("nation", nation);
		map.put("pid", pid);
		map.put("graduteSchool", graduteSchool);
		map.put("graduateYear", graduteYear);
		map.put("graduateDate", graduteDate);
		map.put("education", education);
		map.put("major", major);
		map.put("healthy", healthy);
		map.put("politics", politics);
		map.put("birthday", birthday);
		map.put("residentAddress", residentAddress);
		map.put("permanentAddress", permanentAddress);
		map.put("tel", tel);
		map.put("email", email);
		map.put("homeAddress", homeAddress);
		map.put("homeTel", homeTel);
		map.put("professionId",professionId);
		map.put("organizationId", organizationId);
		map.put("place", place);
		map.put("id", id);
		int count = i_EnrollService.modifyEnroll(map);
		renderJson("result", count);
	}
	
	// 学院分班
	@RequiresPermissions({ "3_19" })
	public void allot() {
		Long[] id = getParaValuesToLong("id");
		Long professionId = getParaToLong("professionId");
		Long organizationId = getParaToLong("organizationId");
		Long classId = getParaToLong("classId");
		String place = getPara("place");
		int i = i_EnrollService.allot(id, professionId, organizationId, classId, place);
		renderJson("result", i);
	}

	// 取消分班
	@RequiresPermissions({ "3_20" })
	public void cancelAllot() {
		Long[] id = getParaValuesToLong("id");
		int i = i_EnrollService.cancelAllot(id);
		renderJson("result", i);
	}	

	// 学员中退
	@RequiresPermissions({ "5_1" })
	public void quit() {
		long id = getParaToLong("id");
		String quitDate = getPara("quitDate");
		String quitReason = getPara("quitReason");
		int count = i_EnrollService.quit(id, quitDate, quitReason);
		renderJson("result", count);
	}

	// 取消中退
	@RequiresPermissions({ "5_2" })
	public void cancelQuit() {
		Long[] id = getParaValuesToLong("id");
		int count = i_EnrollService.cancelQuit(id);
		renderJson("result", count);
	}
	
	public I_EnrollService getI_EnrollService() {
		return i_EnrollService;
	}

	public void setI_EnrollService(I_EnrollService i_EnrollService) {
		this.i_EnrollService = i_EnrollService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}


}

