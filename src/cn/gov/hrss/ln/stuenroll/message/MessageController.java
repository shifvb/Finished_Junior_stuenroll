package cn.gov.hrss.ln.stuenroll.message;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

@Spring("messageController")
public class MessageController extends Controller{
	
	private I_MessageSevice i_MessageSevice;
	@RequiresPermissions("9_1")
	public void write(){
		String name = getPara("name");
		String title = getPara("title");
		String maininfo = getPara("maininfo");
		String writer = getSessionAttr("name");
		String writerorg = getSessionAttr("organization");
		int page= i_MessageSevice.write(name,title,maininfo,writer,writerorg);
		renderJson("result",page);
	}
	public void receive(){
		String username = getSessionAttr("name");
		String org = getSessionAttr("organization");
		int type = getParaToInt("type");
		List<Record> page= i_MessageSevice.receive(username, org,type);
		renderJson("result",page);
	}
	@RequiresPermissions("9_2")
	public void delect(){
		String id = getPara("id");
		i_MessageSevice.delect(id);
	}
	
	public void query(){
		int type = getParaToInt("type");
		String userid = getSessionAttr("userid");
		String username = getSessionAttr("name");
		List<Record> list = i_MessageSevice.query(type, userid,username);
		int count = list.size();
		renderJson("result",count);
	}
	public void queryinfo(){
		int type = getParaToInt("type");
		String userid = getSessionAttr("userid");
		String username = getSessionAttr("name");
		List<Record> list = i_MessageSevice.queryinfo(type, userid,username);
		renderJson("result",list);
	}
	
	@RequiresPermissions("9_1")
	public void writemajormessage(){
		String organizationName = getSessionAttr("organization");
		String year = getPara("year");
		String profession = getPara("profession");
		String text = getPara("text");
		String title = getPara("title");
		String list_text = getPara("list_text");
		String publish = getSessionAttr("name");
		int count = i_MessageSevice.writemajormessage(organizationName, year, profession, text, title, list_text,publish);
		renderJson("result",count);
	}
	
	public void querymajor(){
		String organizationId = getSessionAttr("organizationId").toString();
		List<Record> list = i_MessageSevice.queryMajor(organizationId);
		renderJson("result",list);
	}
	public I_MessageSevice getI_MessageSevice() {
		return i_MessageSevice;
	}
	public void setI_MessageSevice(I_MessageSevice i_MessageSevice) {
		this.i_MessageSevice = i_MessageSevice;
	}
}
