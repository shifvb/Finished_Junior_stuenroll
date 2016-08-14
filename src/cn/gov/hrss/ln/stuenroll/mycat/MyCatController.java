package cn.gov.hrss.ln.stuenroll.mycat;

import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Spring;

@Spring("myCatController")
public class MyCatController extends Controller implements I_MyCatController{
	private MyCatService myCatService;
	
	public void test() {
		renderJson("result", "Hello, world!");
	}

	public MyCatService getMyCatService() {
		return myCatService;
	}

	public void setMyCatService(MyCatService myCatService) {
		this.myCatService = myCatService;
	}
}
