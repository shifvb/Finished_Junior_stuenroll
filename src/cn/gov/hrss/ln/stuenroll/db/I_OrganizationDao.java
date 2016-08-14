package cn.gov.hrss.ln.stuenroll.db;

import java.util.Map;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 组织机构数据访对象问接口
 * 
 * @author LiYaDong
 * @version 2.0
 *
 */
public interface I_OrganizationDao {
	
	/**
	 * 根据机构ID查询机构
	 * @param id 机构ID
	 * @return 返回记录
	 */
	public Record searchOrganization(Long id);

	/**
	 * 根据传入的条件查询机构信息
	 * 
	 * @param map 传入的条件的键值对
	 * @param start 查询起始点
	 * @param length 查询的偏移量
	 * @return 查询出的符合调价您的机构信息记录
	 */
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询机构关联的专业数量
	 * @param map 传入的条件的键值对
	 * @param start 查询起始点
	 * @param length 查询的偏移量
	 * @return 查询出的符合条件的机构关联的专业数量
	 */
	public List<Record> searchOrganizatonOnProfessionAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询机构关联的班级数量
	 * @param map 传入的条件的键值对
	 * @param start 查询起始点
	 * @param length 查询的偏移量
	 * @return 查询出符合条件的机构的关联班级数量列表
	 */
	public List<Record> searchOrganizationOnClassAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询报名表里符合条件机构的报名人数
	 * @param map 传入的条件的键值对
	 * @param start 查询起始点
	 * @param length 查询的偏移量
	 * @return 查询出符合条件的机构关联的报名人数列表
	 */
	public List<Record> searchOrganizationOnEnrollAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 查询机构关联的分班人数
	 * @param map 传入的条件的键值对
	 * @param start 查询起始点
	 * @param length 查询的偏移量
	 * @return 查询出符合条件的机构关联的培训人数列表
	 */
	public List<Record> searchOrganizationOnArchiveAmount(Map<String, Object> map, long start, long length);
	
	/**
	 * 根据条件查询符合条件的机构数
	 * @param map 条件
	 * @return 符合条件的机构数量
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
	 * 添加机构参与转换
	 * @param id 机构ID
	 * @param year 参与转换年届
	 * @param block 是否参与转换 0 是 1 否
	 * @return
	 */
	public long insertOrganizationTransformInfo(Long id, int year, int block);
	
	/**
	 * 修改机构参与转换信息
	 * @param id 机构ID
	 * @param year 参与转换年届
	 * @param block 是否参与转换 0 是 1 否
	 * @return
	 */
	public long updateOrganizationTransformInfo(Long id, Long organizationId, int year, int block);
	
	/**
	 * 获取参与转换的机构
	 * @return
	 */
	public List<Record> getTransformOrganizations();
	/*
	 * 查询机构列表
	 */
	public List<Record> searchOrganization();
	
}
