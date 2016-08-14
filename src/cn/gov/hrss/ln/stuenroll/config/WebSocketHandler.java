package cn.gov.hrss.ln.stuenroll.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.LogKit;

public class WebSocketHandler extends Handler {

	@Override
	public void handle(String path, HttpServletRequest request, HttpServletResponse response, boolean[] bs) {
		if (!path.equals("/mycat/websocket")) {
			next.handle(path, request, response, bs);
		}
	}
}
