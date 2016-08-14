package cn.gov.hrss.ln.stuenroll.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 * 测试JSON
 * @author LiYadong
 * @version 1.0
 */

public class TestJson {
	
	public static void main(String[] args) {
//		Record record = new Record();
//		record.set("name", "hhh");
//		record.set("age", 123);
//		JSONObject json = new JSONObject();
//		json.put("name", "hhh");
//		json.put("age", 123);
//		System.out.println(json.toString());
		DateFormat df = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
		System.out.println(df.format(new Date()));
		
	}

}
