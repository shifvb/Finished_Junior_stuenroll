package cn.gov.hrss.ln.stuenroll.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * Http请求Handler处理类
 */
@SuppressWarnings("all")
public class RequestHandler extends Handler {

	@Override
	public void handle(final String path, final HttpServletRequest request, final HttpServletResponse response, final boolean[] bs) {
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    this.next.handle(path, request, response, bs);
	}
	
}
