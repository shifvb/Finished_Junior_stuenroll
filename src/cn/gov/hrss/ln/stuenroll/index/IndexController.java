package cn.gov.hrss.ln.stuenroll.index;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;
/**
 * index控制器
 * 
 * @author Administrator
 *
 */
@Spring("indexController")
public class IndexController extends Controller{
	private I_IndexSevice i_IndexSevice;
	
	public void seachHotMajor(){
		long oid = getSessionAttr("organizationId");
		String organization = getSessionAttr("organization");
		if ("辽宁省就业网".equals(organization)) {
			oid = -1;
		}
		List<Record> records = i_IndexSevice.searchHotMajor(oid);
		renderJson("result",records);
	}
	public void seachHotPlace(){
		long oid = getSessionAttr("organizationId");
		String organization = getSessionAttr("organization");
		if ("辽宁省就业网".equals(organization)) {
			oid = -1;
		}
		List<Record> records = i_IndexSevice.searchHotPlace(oid);
		renderJson("result",records);
	}
	public void seachHotEducation(){
		long oid = getSessionAttr("organizationId");
		String organization = getSessionAttr("organization");
		if ("辽宁省就业网".equals(organization)) {
			oid = -1;
		}
		List<Record> records = i_IndexSevice.searchHotEducation(oid);
		renderJson("result",records);
	}
	public void searchHotMajorByClass(){
		long oid = getSessionAttr("organizationId");
		String organization = getSessionAttr("organization");
		if ("辽宁省就业网".equals(organization)) {
			oid = -1;
		}
		List<Record> records = i_IndexSevice.searchHotMajorByClass(oid);
		renderJson("result",records);
	}
	public void searchHotMajorByJob(){
		long oid = getSessionAttr("organizationId");
		String organization = getSessionAttr("organization");
		if ("辽宁省就业网".equals(organization)) {
			oid = -1;
		}
		List<Record> records = i_IndexSevice.searchHotMajorByJob(oid);
		renderJson("result",records);
	}
	public void searchHotMonthData(){
		long organizationId = getSessionAttr("organizationId");
		String organization = getSessionAttr("organization");
		if ("辽宁省就业网".equals(organization)) {
			organizationId = -1;
		}
		int year  = getParaToInt("year");
		int month  = getParaToInt("month");
		List<Record> records = i_IndexSevice.searchHotMonthData(organizationId, year, month);
		renderJson("result",records);
	}
	
	public void dynamic() {
		String username = getSessionAttr("name");
		Page<Record> record = i_IndexSevice.dynamic("sys_news",username);
		renderJson("result", record.getList());
	}
	
	public void nginxCounter(){
		int data[] = NginxCounter.readFileByLines("D:/Nginx/logs/access.log");
		ArrayList<Integer> list = new ArrayList<>();
		ArrayList<Integer> list2 = new ArrayList<>();
		for(int i =0;i<7;i++){
			list.add(data[i]);
		}
		for(int i =7;i<14;i++){
			list2.add(data[i]);
		}
		ArrayList<ArrayList<Integer>> list3 = new ArrayList<>();
		list3.add(list);
		list3.add(list2);
		renderJson("result",list3);
		
	}

	public I_IndexSevice getI_IndexSevice() {
		return i_IndexSevice;
	}

	public void setI_IndexSevice(I_IndexSevice i_IndexSevice) {
		this.i_IndexSevice = i_IndexSevice;
	}
}
