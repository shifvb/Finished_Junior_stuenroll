package cn.gov.hrss.ln.stuenroll.organization;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

/**
 * 机构管理网络接口实现
 * 
 * @author LiYadong
 * @version 2.0
 *
 */

@Spring("organizationController")
public class OrganizationController extends Controller implements I_OrganizationController {

	private I_OrganizationService i_OrganizationService;
	private int rowsInPage;
	private final Long HRSS = 738620600423157760L;

	@RequiresPermissions({ "7_4" })
	@Override
	public void searchOrganizations() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String liasion = getPara("liasion");
		String tel = getPara("tel");
		Long organizationId = getParaToLong("organizationId");
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("liasion", liasion);
		map.put("tel", tel);
		if (!HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;

		List<Record> list = i_OrganizationService.searchOrganizations(map, start, length);
		renderJson("result", list);

	}

	@RequiresPermissions({ "7_4" })
	@Override
	public void searchOrganizationAmount() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String liasion = getPara("liasion");
		String tel = getPara("tel");
		Long organizationId = getParaToLong("organizationId");

		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("liasion", liasion);
		map.put("tel", tel);
		if (!HRSS.equals(organizationId)) {
			map.put("organizationId", organizationId);
		}
		
		long count = i_OrganizationService.searchOrganizationAmount(map);
		renderJson("result", count);

	}

	@RequiresPermissions({ "7_1" })
	@Override
	public void addOrganization() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String liasion = getPara("liasion");
		String tel = getPara("tel");
		String liasion_tel = getPara("liasion_tel");

		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("liasion", liasion);
		map.put("tel", tel);
		map.put("liasion_tel", liasion_tel);
		
		int success = i_OrganizationService.addOrganization(map);
		renderJson("result", success);
		
	}

	@RequiresPermissions({ "7_3" })
	@Override
	public void updateOrganization() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String liasion = getPara("liasion");
		String tel = getPara("tel");
		Long organizationId = getParaToLong("organizationId");

		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("liasion", liasion);
		map.put("tel", tel);
		
		int success = i_OrganizationService.updateOrganization(organizationId, map);
		renderJson("result", success);
	}

	@RequiresPermissions({ "7_2" })
	@Override
	public void deleteOrganizationsById() {
		Long[] id = getParaValuesToLong("id");
		long count = i_OrganizationService.deleteOrganizationsById(id);
		renderJson("result", count);
	}
	
	
	@Override
	public void setOrganizationTransformInfo() {
		int block = getParaToInt("block");
		int year = getParaToInt("year");
		Long[] id = getParaValuesToLong("id");
		long count = i_OrganizationService.setOrganizationTransformInfo(id, year, block);
		renderJson("result", count);
		
	}
	
	public I_OrganizationService getI_OrganizationService() {
		return i_OrganizationService;
	}

	public void setI_OrganizationService(I_OrganizationService i_OrganizationService) {
		this.i_OrganizationService = i_OrganizationService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}
	
	@Override
	public void searchOrganization() {
		List<Record> record=i_OrganizationService.searchOrganization();
		renderJson("result", record);
	}

}
