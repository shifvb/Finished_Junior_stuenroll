package cn.gov.hrss.ln.stuenroll.mycat;

import cn.gov.hrss.ln.stuenroll.db.I_MyCatDao;

public class MyCatService implements I_MyCatService {
	private I_MyCatDao myCatDao;

	public I_MyCatDao getMyCatDao() {
		return myCatDao;
	}

	public void setMyCatDao(I_MyCatDao myCatDao) {
		this.myCatDao = myCatDao;
	}

}
