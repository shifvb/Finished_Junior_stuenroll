package cn.gov.hrss.ln.stuenroll.redis;

import java.util.HashMap;

import com.jfinal.plugin.activerecord.Record;

/**
 * 报名缓存接口
 * @author York Chu
 *
 */
public interface I_EnrollRedisDAO {
	/**
	 * 缓存报名记录
	 * @param map
	 */
	//让Eclipse不报泛型提示
	@SuppressWarnings("rawtypes")
	public void insert(HashMap map);
	
	/**
	 * 检查身份证号号是否已经报名
	 * @param pid
	 * @return
	 */
	public boolean isEnrollEligible(String pid);
	/**
	 * 根据pid查找报名记录
	 * @param pid
	 * @return
	 */
	public Record searchRegisterRecord(String pid);
}
