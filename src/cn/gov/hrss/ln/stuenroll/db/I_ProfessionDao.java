package cn.gov.hrss.ln.stuenroll.db;
//最新

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

public interface I_ProfessionDao {
	/**
	 * 在下拉框中根据年份查找相关机构的专业
	 * @param year
	 * @return
	 */
	public List<Record> searchProfessionInYearAtDropDown(int year);
	
	/**
	 * 查询专业关联机构
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionOrganization(HashMap map,long start, long length);
	/**
	 * 查询专业关联年届
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionYearAmount(HashMap map,long start, long length);
	/**
	 * 查询archive表中专业关联的班级数量
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionClassAmount(HashMap map,long start, long length);
	/**
	 * 查询archive表中专业关联的培训学生人数
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionStudentAmountInArchive(HashMap map,long start, long length);
	/**
	 * 查询archive表中专业关联的已就业学生人数
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionStudentAmountGetAJobInArchive(HashMap map,long start, long length);
	/**
	 * 查询enroll表中专业关联的班级数量
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionClassAmountInEnroll(HashMap map,long start, long length);
	/**
	 * 查询enroll表中专业关联的培训学生人数
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionStudentAmountInEnroll(HashMap map,long start, long length);
	/**
	 * 查询enroll表中专业关联的已就业学生人数
	 * @param map
	 * @return
	 */
	public List<Record> searchProfessionStudentAmountGetAJobInEnroll(HashMap map,long start, long length);

	/**
	 * 查询专业总数
	 * @return
	 */
	public long searchProfessionCount(HashMap map);
	/**
	 * 查询所有专业
	 * @return
	 */
	public List<Record> searchAllProfession();
	/**
	 * 根据专业ID查询专业名称
	 * @param id
	 * @return
	 */
	public String searchName(long id);
	/**
	 * 添加专业信息
	 * @param map
	 * @return
	 */
	public int addProfession(HashMap map);
	
	/**
	 * 修改专业信息
	 * @param professionId
	 * @param map
	 * @return
	 */
	public int updateProfession(HashMap map);
	/**
	 * 根据专业ID删除专业
	 * @param id
	 * @return
	 */
	public int deleteProfessionById(Long[] id);
	
	/**
	 * 根据条件查询专业
	 * @param map 条件
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"professionId" : 专业ID,
	 * 		"year": 年届
	 * }
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 记录
	 * {
	 * 		"professionId": 专业ID,
	 * 		"professionName": 专业名称,
	 * 		"year": 年届
	 * }
	 */
	public List<Record> searchProfessionsWithConditions(Map<String, Object> map, Long start, Long length);

	/**
	 * 导入专业记录
	 * @param objects
	 * @return
	 */
	public int importProfession(Object... objects);
}
