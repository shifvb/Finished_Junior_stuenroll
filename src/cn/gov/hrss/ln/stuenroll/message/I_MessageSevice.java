package cn.gov.hrss.ln.stuenroll.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public interface I_MessageSevice {
	/**
	 * 发送消息
	 * 
	 * @param name
	 * @param title
	 * @param info
	 * @param writer 
	 * @param writerorg 
	 */
	public int write(String name,String title,String info, String writer, String writerorg);
	
	/**
	 * 收取私邮
	 * 
	 * @param username
	 * @param org
	 * @return
	 */
	public List<Record> receive(String username,String org,int type);
	
	/**
	 * 查询消息数量
	 * 
	 * @param id
	 * @return
	 */
	public List<Record> query(int type,String userid,String username);
	/**
	 * 查询消息详细 更新查询时间
	 * 
	 * @param id
	 * @return
	 */
	public List<Record> queryinfo(int type,String userid,String username);
	
	/**
	 * 删除消息sys_news By _id
	 * 
	 * @param id
	 * @return
	 */
	public int delect(String id);
	/**
	 * 
	 * @param organizationName
	 * @param year
	 * @param profession
	 * @param text
	 * @param title
	 * @param list_text
	 * @return
	 */
	public int writemajormessage(String organizationName,String year,String profession,String text,String title,String list_text,String publish);
	/**
	 * 根据组织ID查专业
	 * 
	 * @param userid
	 */
	public List<Record> queryMajor(String org_id);
}
