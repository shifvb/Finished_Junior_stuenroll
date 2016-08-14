package cn.gov.hrss.ln.stuenroll.test;

import java.util.HashMap;

import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Spring;

@Spring("testController")
public class TestController extends Controller{
	
	public void sayHello(){
		String username=getPara("username");//获得请求上传的数据
		setAttr("username", username);//绑定数据
		renderJsp("/test/2.jsp");//跳转JSP页面
	}
	
	public void hehe() {
		System.out.println("shshjs");
	}
}
