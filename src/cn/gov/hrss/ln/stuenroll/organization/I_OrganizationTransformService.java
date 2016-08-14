package cn.gov.hrss.ln.stuenroll.organization;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * 机构参与转换业务模块接口
 * @author LiYaDong
 * @version 1.0
 */

public interface I_OrganizationTransformService {

	/**
	 * 根据条件查询参与转换的机构列表
	 * @param map 条件
	 * @param start 开始位置
	 * @param length 偏移量
	 * @return 机构列表
	 */
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length);
	
	/**
	 * 查询符合条件参与转换的机构数量
	 * @param map 条件
	 * @return 数量
	 */
	public long searchOrganizationsAmount(Map<String, Object> map);
	
	/**
	 * 根据条件查询机构关联的专业数
	 * @param map 条件
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 列表
	 */
	public List<Record> searchProfessionsAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询机构关联的班级数
	 * @param map 条件
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 列表
	 */
	public List<Record> searchClassAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询机构关联的学生数
	 * @param map 条件
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 列表
	 */
	public List<Record> searchArchiveStudentsAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询机构关联的学生数
	 * @param map 条件
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 列表
	 */
	public List<Record> searchUnarchiveStudentsAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 更新参与转换机构信息
	 * @param map 条件及更新数据
	 * @return 更新条数
	 */
	public int updateOrganization(Map<String, Object> map);
	
	/**
	 * 删除参与转换的机构 
	 * @param year 年数组
	 * @param id 机构ID数组
	 * @return 删除的条数
	 */
	public long deleteOrganizations(Integer[] year, Long[] id);
	
	/**
	 * 删除参与转换的机构 
	 * @param id 参与转换机构ID数组
	 * @return 删除的条数
	 */
	public long deleteOrganizations(Long[] id, Integer year);
	
	/**
	 * 删除参与转换的机构 
	 * @param year 年届数组
	 * @param ids 每年对应需要删除的机构ID数组形成的Map
	 * @return 删除的记录数
	 */
	public long deleteOrganizations(List<Integer> year, Map<Integer, List<Long>> ids);
	
	/**
	 * 向机构专业关联表插入数据
	 * @param organizationId 机构ID
	 * @param professionId 专业ID
	 * @return 插入条数
	 */
	public int addOrganizationProfession(Long organizationId, Long[] professionId, Integer year);
	
	/**
	 * 在机构专业关联表删除数据
	 * @param organizationId 机构ID
	 * @param professionId 专业ID
	 * @return
	 */
	public int deleteOrganizationProfession(Long organizationId, Long[] professionId, Integer year);

	/**
	 * 根据年份和专业ID查询参与转换的机构列表
	 * 
	 * @param year 年份
	 * @param professionId 专业ID
	 * @return 查询出的机构列表
	 */
	public List<Record> searchOrganizationsJoinWithProfessionByYearAtDropdown(Map<String, Object> map);	
}
