package cn.gov.hrss.ln.stuenroll.db;

/**
 * quit_info表Dao接口
 * 
 * @author YangDi
 *
 */
public interface I_QuitInfoDao {
	/**
	 * 记录中退信息
	 * 
	 * @param enrollId
	 * @param quitDate
	 * @param quitReason
	 */
	public void save(long enrollId, String quitDate, String quitReason);
	
	/**
	 * 删除中退信息
	 * 
	 * @param enrollId
	 */
	public int delete(Long[] enrollId);
	
}
