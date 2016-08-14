package cn.gov.hrss.ln.stuenroll.mycat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;

@ServerEndpoint("/mycat/websocket")
public class MyCatWebSocket {
	I_MyCatService myCatService;

	@OnMessage
	public void onMessage(String message, Session session) {	
		Map map = session.getPathParameters();
		JSONObject obj = JSON.parseObject(message);
		String view = obj.getString("view");
		if (view.equals("mycat-list")) {
			List<Record> list = new ArrayList<Record>();
			Record r = new Record();
			r.set("ip", "192.168.1.1");
			list.add(r);
			Basic remote = session.getBasicRemote();
			JSONArray array = new JSONArray();
			array.addAll(list);
			try {
				remote.sendText(array.toJSONString());
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public I_MyCatService getMyCatService() {
		return myCatService;
	}

	public void setMyCatService(I_MyCatService myCatService) {
		this.myCatService = myCatService;
	}

}
