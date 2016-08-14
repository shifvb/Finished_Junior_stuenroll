package cn.gov.hrss.ln.stuenroll.db;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * Enroll表Dao接口
 * 
 * @author York Chu
 *
 */
public interface I_EnrollDao {
	/**
	 * 根据条件查询报名表相关数据总数
	 * 
	 * @param year
	 * @param month
	 * @param stateId
	 * @param organizationId
	 * @return
	 */
	public long searchCountByCondition(int year, int month, int stateId, long organizationId);

	/**
	 * 查询报名记录
	 * 
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Record> searchEnroll(HashMap map, long start, long length);
	
	/**
	 * 查询记录总数
	 * 
	 * @param map
	 * @return
	 */
	public long searchEnrollCount(HashMap map);
	/**
	 * 添加报名记录
	 * 
	 * @param map
	 * @return
	 */
	public int addEnroll(Object[] obj);
	/**
	 * 修改报名记录	 
	 *  
	 * @param obj
	 * @param id
	 * @return
	 */
	public int modifyEnroll(HashMap map);
	/**
	 * 根据ID查询报名记录
	 * @param id
	 * @return
	 */
	public Record searchEnrollById(long id);
	
	/**
	 * 根据pid查找报名记录
	 * @param pid
	 * @return
	 */
	public Record searchRegisterRecord(String pid);

	/**
	 * 根据ID删除数据
	 * @param id
	 * @return
	 */
	public int deleteById(Long[] id);
	
	/**
	 * 学员分班
	 * @param id
	 * @param professionId
	 * @param organizationId
	 * @param classId
	 * @return
	 */
	public int allot(Long[] id, long professionId, long organizationId, long classId, String place);
	
	/**
	 * 学员分班取消
	 * @param id
	 * @return
	 */
	public int cancelAllot(Long[] id);
	/**
	 * 学员中退
	 * @param id
	 * @param professionId
	 * @param organizationId
	 * @param classId
	 * @return
	 */
	public int quit(long id);
	
	/**
	 * 学员中退取消
	 * @param id
	 * @return
	 */
	public int cancelQuit(Long[] id);
	/**
	 * 在数据库中检查身份证号号是否已经报名
	 * @param pid
	 * @return
	 */
	public boolean isEnrollEligible(String pid);
	/**
	 * 在数据库中检查身份证号号是否已经报名
	 * @param pid
	 * @return
	 */
}
