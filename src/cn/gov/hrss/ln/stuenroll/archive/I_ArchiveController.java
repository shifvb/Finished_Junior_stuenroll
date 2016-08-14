package cn.gov.hrss.ln.stuenroll.archive;


public interface I_ArchiveController {
	/**
	 * 查询归档表记录相关信息
	 */
	public void searchArchive();
	/**
	 * 查询归档表记录总数
	 */
	public void searchArchiveCount();
	/**
	 * 添加归档表记录相关信息
	 */
	public void addArchive();
	/**
	 * 修改归档表记录相关信息
	 */
	public void updateArchive();
	/**
	 * 删除归档表记录
	 */
	public void deleteArchiveById();
	/**
	 * 查询学生状态
	 */
	public void searchStudentState();
	/**
	 * 查询班级信息
	 */
	public void searchClassInfo();
}
