package cn.gov.hrss.ln.stuenroll.archive;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ArchiveDao;

public interface I_ArchiveService {
	/**
	 * 查询归档表记录相关信息
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Record> searchArchive(HashMap map,long start,long length);
	/**
	 * 查询归档表记录总数
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public long searchArchiveCount(HashMap map);
	/**
	 * 添加归档表记录相关信息
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public int addArchive(HashMap map);
	/**
	 * 修改归档表记录相关信息
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public int updateArchive(HashMap map);
	/**
	 * 删除归档表记录
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public int deleteArchiveById(Long[] id);
	/**
	 * 查询学生状态
	 * @return
	 */
	public List<Record> searchStudentState();
	/**
	 * 查询班级信息
	 * @return
	 */
	public List<Record> searchClassInfo();
	/**
	 * 验证报名是否成功，通过查看身份证号是否重复
	 * @param pid
	 * @return
	 */
	public boolean isEnrollEligible(long pid);
}
