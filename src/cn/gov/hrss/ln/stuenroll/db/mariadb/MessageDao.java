package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bson.types.ObjectId;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_MessageDao;
import cn.gov.hrss.ln.stuenroll.plugin.MongoKit;

public class MessageDao implements I_MessageDao {

	@Override
	public int write(String name, String title, String info,String writer,String writerorg) {
		Record record2 = new Record();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
		record2.set("systime",format.format(new Date()));
		record2.set("systime2",format2.format(new Date()));
		record2.set("receiver", name);
		record2.set("message", info);
		record2.set("title", title);
		record2.set("writer", writer);		
		record2.set("writerorg", writerorg);		
		int count = MongoKit.save("sys_news", record2);
		return count;
	}

	@Override
	public List<Record> receive(String username, String org,int type) {
		HashMap hashMap = new HashMap();
		List<Record> list = null;
		if(type == 1){
			hashMap.put("receiver",username);
		    list = MongoKit.paginate("sys_news", 1, 15,hashMap).getList();
		    System.out.println(list);
			hashMap.put("receiver","公告");
			List<Record> list1 = MongoKit.paginate("sys_news", 1, 15,hashMap).getList();
			System.out.println(list1);
			list.addAll(list1);
		}
		if(type == 2){
			hashMap.put("writer",username);
			list = MongoKit.paginate("sys_news", 1, 15,hashMap).getList();
		}
		return list;
	}
	
	@Override
	public int delect(String id) {
		HashMap hashMap2 = new HashMap();
		ObjectId t = new ObjectId(id);
		hashMap2.put("_id",t);
		int count = MongoKit.remove("sys_news", hashMap2);
		return count;		
	}
	@Override
	public int writemajormessage(String organizationName, String year, String profession, String text, String title, String list_text,
			String time,String publish) {
		Record record2 = new Record();
		record2.set("organizationName", organizationName);
		record2.set("year", year);
		record2.set("profession", profession);
		record2.set("text", text);		
		record2.set("title", title);		
		record2.set("list_text", list_text);
		record2.set("time",time);
		record2.set("list_img","123");
		record2.set("publish",publish);
		System.out.println(record2);
		int count = MongoKit.save("inform", record2);
		return count;				
	}

	@Override
	public List<Record> query(int type,String userid,String username) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM user_readtime WHERE user_id = ?");
		Record records = Db.findFirst(sql.toString(),userid);
		Date notice_time  = records.getTimestamp("notice_time");
		Date message_time  = records.getTimestamp("message_time");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		long notice_flag = Long.valueOf(format.format(notice_time));
		long message_flag = Long.valueOf(format.format(message_time));
		System.out.println(notice_flag+"Flag哈哈"+message_flag);
		HashMap hashMap = new HashMap();
		if(type == 1){
			hashMap.put("receiver","公告");
		}
		if(type == 2){
			hashMap.put("receiver",username);
		}
		List<Record> list = MongoKit.paginate("sys_news", 1, 10000,hashMap).getList();
		List<Record> list2 = new ArrayList<Record>();
		if(type == 1){
			for(Record r : list){
				long date = Long.valueOf(r.get("systime"));
				long time = Long.valueOf(r.getStr("systime2").replace(":", ""));
				long flag = date*10000+time;
				if(flag>notice_flag){
					list2.add(r);
				}
			}
			
		}
		if(type == 2){
			for(Record r : list){
				long date = Long.valueOf(r.get("systime"));
				long time = Long.valueOf(r.getStr("systime2").replace(":", ""));
				long flag = date*10000+time;
				if(flag>message_flag){
					list2.add(r);
				}
			}
			
		}
		return list2;
	}
	@Override
	public void refresh_time(String userid,int type) {
		if(type == 1){
			String sql2 = "UPDATE user_readtime SET notice_time = NOW() WHERE user_id = ?"; 
			Db.update(sql2,userid);
		}
		if(type == 2){
			String sql2 = "UPDATE user_readtime SET message_time = NOW() WHERE user_id = ?"; 
			Db.update(sql2,userid);
		}
	}

	@Override
	public List<Record> queryMajor(String org_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT");
		sql.append("	`name`, ");
		sql.append("	`year` ");
		sql.append("FROM ");
		sql.append("	organization_join oj ");
		sql.append("LEFT JOIN organization_profession op ON op.organization_join_id = oj.id  ");
		sql.append("LEFT JOIN profession p ON p.id = profession_id ");
		sql.append("WHERE");
		sql.append("	oj.organization_id = ? ");
		sql.append("ORDER BY year ");
		
		List<Record> list = Db.find(sql.toString(),org_id);
		return list;
	}
}
