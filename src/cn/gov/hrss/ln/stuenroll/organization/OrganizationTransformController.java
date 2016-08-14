package cn.gov.hrss.ln.stuenroll.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;


/**
 * 机构参与转换网络类实现
 * @author LiYaDong
 * @version 1.0
 */

@Spring("organizationTransformController")
public class OrganizationTransformController extends Controller implements I_OrganizationTransformController {

	private I_OrganizationTransformService i_OrganizationTransformService;
	private int rowsInPage;
	private final Long HRSS = 738620600423157760L;


	@RequiresPermissions({"7_4"})
	@Override
	public void searchOrganizationsAmount() {
		Integer year = getParaToInt("year");
		Long organizationId = getParaToLong("organizationId");
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		Map<String, Object> map = new HashMap<>();
		map.put("year", year);
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		if (organizationId != null && !HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		long count = i_OrganizationTransformService.searchOrganizationsAmount(map);
		renderJson("result", count);
	}
	
	@RequiresPermissions({"7_4"})
	@Override
	public void searchOrganizations() {
		Integer year = getParaToInt("year");
		Long organizationId = getParaToLong("organizationId");
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		Long page = getParaToLong("page");
		Map<String, Object> map = new HashMap<>();
		map.put("year", year);
		if (organizationId != null && !HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;
		
		List<Record> records = i_OrganizationTransformService.searchOrganizations(map, start, length);
		renderJson("result", records);
	}

	@RequiresPermissions({"7_4"})
	@Override
	public void searchProfessionsAmount() {
		return;
	}

	@RequiresPermissions({"7_4"})
	@Override
	public void searchClassAmount() {
		return;
	}

	@RequiresPermissions({"7_4"})
	@Override
	public void searchStudentsAmount() {
		return;
	}

	@RequiresPermissions({"7_3"})
	@Override
	public void updateOrganization() {
		Long organizationId = getParaToLong("organizationId");
		Integer oldYear = getParaToInt("oldYear");
		Integer newYear = getParaToInt("newYear");
		Integer block = getParaToInt("block");
		Map<String, Object> map = new HashMap<>();
		map.put("organizationId", organizationId);
		map.put("oldYear", oldYear);
		map.put("newYear", newYear);
		map.put("block", block);
		int count = i_OrganizationTransformService.updateOrganization(map);
		renderJson("result", count);
	}

	@RequiresPermissions({"7_2"})
	@Override
	public void deleteOrganizations() {
		Long[] ids = getParaValuesToLong("id");
		Integer[] years = getParaValuesToInt("year");
		long count = i_OrganizationTransformService.deleteOrganizations(years, ids);
		renderJson("result", count);
	}

	@Override
	public void addOrganizationProfession() {
		Long organizationId = getParaToLong("organizationId");
		Long[] professionId = getParaValuesToLong("professionsId");
		Integer year = getParaToInt("year");
		if (professionId == null || professionId.length == 0) {
			renderJson("result", 0);
			return;
		}
		long count = i_OrganizationTransformService.addOrganizationProfession(organizationId, professionId, year);
		renderJson("result", count);
	}

	@Override
	public void deleteOrganizationProfession() {
		Long organizationId = getParaToLong("organizationId");
		Long[] professionId = getParaValuesToLong("professionsId");
		Integer year = getParaToInt("year");
		if (professionId == null || professionId.length == 0) {
			renderJson("result", 0);
			return;
		}
		long count = i_OrganizationTransformService.deleteOrganizationProfession(organizationId, professionId, year);
		renderJson("result", count);
	}

	@Override
	public void searchOrganizationsJoinWithProfessionByYearAtDropdown() {
		Integer year = getParaToInt("year");
		Long professionId = getParaToLong("professionId");
		Map<String, Object> map = new HashMap<>();
		map.put("year", year);
		map.put("professionId", professionId);
		map.put("block", 0);
		List<Record> records = i_OrganizationTransformService.searchOrganizationsJoinWithProfessionByYearAtDropdown(map);
		renderJson("result", records);
	}

	@Override
	public void searchOrganizationsJoinByYearAtDropdown() {
		Integer year = getParaToInt("year");
		Map<String, Object> map = new HashMap<>();
		map.put("year", year);
		List<Record> records = i_OrganizationTransformService.searchOrganizations(map, 0, 100000);
		renderJson("result", records);
	}
	
	@Override
	public void searchOrganizationsJoinAtDropdown() {
		Long professionId = getParaToLong("professionId");
		Map<String, Object> map = new HashMap<>();
		map.put("professionId", professionId);
		List<Record> records = i_OrganizationTransformService.searchOrganizationsJoinWithProfessionByYearAtDropdown(map);
		String id = records.get(0).getStr("id");
		String id2 = "";
		for (int i = 1; i < records.size(); i++) {
			id2 = records.get(i).getStr("id");
			if (id.equals(id2)) {
				records.remove(i);
			}else {
				id = id2;
			}
		}
		renderJson("result", records);
	}

	public I_OrganizationTransformService getI_OrganizationTransformService() {
		return i_OrganizationTransformService;
	}

	public void setI_OrganizationTransformService(I_OrganizationTransformService i_OrganizationTransformService) {
		this.i_OrganizationTransformService = i_OrganizationTransformService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}
}
