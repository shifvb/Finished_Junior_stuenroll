package cn.gov.hrss.ln.stuenroll.organization;

import java.util.Map;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 机构管理模块业务接口
 * 
 * @author LiYaDong
 * @version 2.0
 *
 */
public interface I_OrganizationService {
	
	/**
	 * 根据传入的条件查询机构信息
	 * 
	 * @param map 传入的条件的键值对
	 * @param start 查询起始点
	 * @param length 查询的偏移量
	 * @return 查询出的机构信息记录
	 */
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询符合条件的机构数
	 * @param map
	 * @return
	 */
	public long searchOrganizationAmount(Map<String, Object> map);
	
	/**
	 * 根据传入的机构信息插入机构
	 * 
	 * @param map 传入的机构信息
	 * @return 返回插入的结果
	 */
	public int addOrganization(Map<String, Object> map);
	
	/**
	 * 根据传入的机构新信息更新数据库
	 * 
	 * @param organizationId 机构ID
	 * @param map 传入的更新的机构信息
	 * @return 返回更新的结果
	 */
	public int updateOrganization(long organizationId, Map<String, Object> map);
	
	/**
	 * 根据传入的ID数组批量删除机构
	 * 
	 * @param id 传入的机构ID数组
	 * @return 删除的记录数
	 */
	public long deleteOrganizationsById(Long[] id);
	
	/**
	 * 设置机构参与转换
	 * @param id 机构ID数据
	 * @param year 参与转换年届
	 * @param block 是否参与转换 0 是 1 否
	 * @return 成功设置的条数
	 */
	public long setOrganizationTransformInfo(Long[] id, int year, int block);
	
	/**
	 * 查询机构列表
	 */
	public List<Record> searchOrganization();
	
}