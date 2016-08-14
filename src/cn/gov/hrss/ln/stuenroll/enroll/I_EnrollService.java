package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 报名管理业务接口
 * 
 * @author YangDi
 *
 */
public interface I_EnrollService {
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
	 * 根据ID删除数据
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(Long[] id);
	/**
	 * 学员注册，注册信息会临时保存在Redis里面
	 * 
	 * @param map
	 *            报名数据
	 */
	public void register(HashMap map);
	/**
	 * 查询学生是否具有报名资格，以身份证号不能重复报名作为判断条件
	 * 
	 * @param pid
	 *            身份证号
	 * @return 查询结果
	 */
	public boolean isEnrollEligible(String pid);
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
	 * @param quitDate
	 * @param quitReason
	 * @return
	 */
	public int quit(long id, String quitDate, String quitReason);
	
	/**
	 * 学员中退取消
	 * @param id
	 * @return
	 */
	public int cancelQuit(Long[] id);
}
