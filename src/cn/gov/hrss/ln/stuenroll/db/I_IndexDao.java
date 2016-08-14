package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * index页面的Dao
 * 
 * @author GuoZeChuan
 *
 */
public interface I_IndexDao {
	
	/**
	 * 查询某个组织机构下当年报名人数最多的前5个专业
	 * 
	 * @return
	 */
	public List<Record> searchHotMajor(long oid);
	
	/**
	 * 查询某个组织机构下当年报名人数最多的前5个专业通过数据表Archive
	 * 
	 * @return
	 */
	public List<Record> searchHotMajorArchive(long oid);
	
	/**
	 * 查询某个组织机构下当年报名人数最多的前5个培训地点
	 * 
	 * @return
	 */
	public List<Record> searchHotPlace(long oid);
	
	/**
	 * 查询某个组织机构下当年报名人数最多的前5个培训地点通过数据表Archive
	 * 
	 * @return
	 */
	public List<Record> searchHotPlaceArchive(long oid);
	/**
	 * 查询某个组织机构下当年报名人数最多的前5个学历
	 * 
	 * @return
	 */
	public List<Record> searchHotEducation(long oid);
	
	/**
	 * 查询某个组织机构下当年报名人数最多的前5个学历通过数据表Archive
	 * 
	 * @return
	 */
	public List<Record> searchHotEducationArchive(long oid);
	
	/**
	 * 查询某个组织机构下当年班级数量最多的前5个专业
	 * 
	 * @return
	 */
	public List<Record> searchHotMajorByClass(long oid);
	/**
	 * 查询某个组织机构下的就业人数最多的前6个专业
	 * 
	 * @param oid
	 * @return
	 */
	public List<Record> searchHotMajorByJob(long oid);
	
	/**
	 * 查询某个组织机构下的就业人数最多的前6个专业通过数据表Archive
	 * 
	 * @param oid
	 * @return
	 */
	public List<Record> searchHotMajorByJobArchive(long oid);
	
	/**
	 * 查询某个组织机构下某年某月的4种（报名 审核 归档 中退）人数
	 * 
	 * @param oid
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Record> searchHotMonthData(long oid,int year,int month);
	
	/**
	 * 查询某个组织机构下某年某月的(归档) 人数
	 * 
	 * @param oid
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Record> searchHotMonthDataArchive(long oid,int year,int month);
	
	/**
	 * MongoDb db:hrss get sys_news to sys_message
	 * 
	 * @param collection
	 * @return
	 */
	public Page<Record> dynamic(String collection,String username);
}
