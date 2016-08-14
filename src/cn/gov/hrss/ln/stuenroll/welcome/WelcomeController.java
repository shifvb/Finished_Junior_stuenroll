package cn.gov.hrss.ln.stuenroll.welcome;

import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Spring;

/**
 * 欢迎模块网络类
 * 
 * @author YangDi
 *
 */
@Spring("welcomeController")
public class WelcomeController extends Controller implements I_WelcomeController {
	private I_WelcomeService i_WelcomeService;
	
	@RequiresPermissions({"3_4","4_4"})
	@Override
	public void statisticsInYear() {
		int year = getParaToInt("year");
		String organization=getSessionAttr("organization");
		long organizationId=getSessionAttr("organizationId");
		if(organization.equals("辽宁省就业网")){
			organizationId=-1;
		}
		HashMap map = i_WelcomeService.statisticsInYear(year,organizationId);
		renderJson("statistics", map);

	}

	public I_WelcomeService getI_WelcomeService() {
		return i_WelcomeService;
	}

	public void setI_WelcomeService(I_WelcomeService i_WelcomeService) {
		this.i_WelcomeService = i_WelcomeService;
	}

}
