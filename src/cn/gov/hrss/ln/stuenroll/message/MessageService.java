package cn.gov.hrss.ln.stuenroll.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_MessageDao;

public class MessageService implements I_MessageSevice {

	private I_MessageDao i_MessageDao;
	@Override
	public int write(String name, String title, String info,String writer,String writerorg) {
		int page = i_MessageDao.write(name, title, info,writer,writerorg);
		return page;

	}
	public I_MessageDao getI_MessageDao() {
		return i_MessageDao;
	}
	public void setI_MessageDao(I_MessageDao i_MessageDao) {
		this.i_MessageDao = i_MessageDao;
	}
	@Override
	public List<Record> receive(String username, String org,int type) {
		List<Record> list = i_MessageDao.receive(username, org,type);
		for(Record r : list){
			r.set("id",r.get("_id").toString());
		}
		return list;
	}
	@Override
	public int delect(String id) {
		int count =  i_MessageDao.delect(id);
		return count;
	}
	public int writemajormessage(String organizationName,String year,String profession,String text,String title,String list_text,String publish){
		String time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return i_MessageDao.writemajormessage(organizationName, year, profession, text, title, list_text, time,publish); 
	}
	@Override
	public List<Record> query(int type, String userid,String username) {
		List<Record> list = i_MessageDao.query(type, userid,username);
		return list;
	}
	@Override
	public List<Record> queryinfo(int type, String userid, String username) {
		List<Record> list = i_MessageDao.query(type, userid,username);
		i_MessageDao.refresh_time(userid, type);
		return list;
	}
	@Override
	public List<Record> queryMajor(String org_id) {
		List<Record> list = i_MessageDao.queryMajor(org_id);
		return list;
	}

}
