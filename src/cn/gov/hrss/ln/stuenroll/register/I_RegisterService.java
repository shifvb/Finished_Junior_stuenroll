package cn.gov.hrss.ln.stuenroll.register;

import com.jfinal.plugin.activerecord.Record;

/**
 * 学员报名业务接口
 */
@SuppressWarnings("all")
public interface I_RegisterService {
  /**
   * 是否存在某条记录
   * @param pid 身份证号
   * @return 查询结果
   */
  public boolean hasRecord(String pid);
  
  /**
   * 查询报名记录
   * @param pid 身份证号
   * @return 查询结果
   */
  public Record searchRegisterRecord(String pid);
}
