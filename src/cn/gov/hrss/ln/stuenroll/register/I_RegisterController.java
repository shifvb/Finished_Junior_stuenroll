package cn.gov.hrss.ln.stuenroll.register;

/**
 * 学员报名网络接口
 */
@SuppressWarnings("all")
public interface I_RegisterController {
  /**
   * 是否存在某条记录
   */
  public abstract void hasRecord();
  
  /**
   * 查询报名记录
   */
  public abstract void downloadRegisterReport();
}
