package cn.gov.hrss.ln.stuenroll.db;

import com.jfinal.plugin.activerecord.Record;

/**
 * UserLog表Dao接口
 * 
 * @author YangDi
 *
 */
public interface I_UserLogDao {
	/**
	 * 记录用户行为
	 * 
	 * @param username
	 * @param operation
	 * @param type
	 */
	public void save(String username, String operation, String type);

	/**
	 * 根据userid在userlog中查找最后一次日志信息
	 * 
	 * @param user_id
	 * @return
	 */
	public Record searchUserLogTimeAndType(String user_id);

}
