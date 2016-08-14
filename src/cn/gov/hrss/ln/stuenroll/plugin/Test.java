package cn.gov.hrss.ln.stuenroll.plugin;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.jam.mutable.MMethod;
import org.bson.types.ObjectId;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import sun.misc.BASE64Encoder;

public class Test {
	public static void main(String[] args) throws Exception {
		
		
		MongodbPlugin mongodbPlugin = new MongodbPlugin("127.0.0.1", 30001, "hrss");
        mongodbPlugin.start();
/*        DB db = MongoKit.getDB();
		GridFS gridFS = new GridFS(db, "image");
		ObjectId _objectId = new ObjectId("578c78a2b26ca981d80511d7");
		GridFSDBFile fsFile = gridFS.findOne(_objectId);
		//
		InputStream in=fsFile.getInputStream();
		byte[] data=new byte[in.available()];
		in.read(data);
		in.close();
		BASE64Encoder encoder=new BASE64Encoder();
		String base=encoder.encode(data);*/
    //    String base =MongoKit.findImage("578c78a2b26ca981d80511d7", "image");
       // System.out.println(data+"111111");
     //   MongoKit.deleteImage("578c58beb26c578082cb8020", "image");
     /*   long num = MongoKit.collectionTotal("news");
        
        DBCollection num=MongoKit.getCollection("news");

        long a=num.count();
        System.out.println(num);*/
      //  HashMap filter = new HashMap();
    
		//filter.put("title", "室内设计-2016-中软卓越");
	//	filter.put("id", "578f12f5b26c9f1b05b06143");
/*        ObjectId _objectId = new ObjectId("578f12e9b26cba6dba097103");
        filter.put("_id", _objectId);
        int num =MongoKit.remove("news", filter);
        System.out.println(num);*/
        //向数据库中插入多条数据
        List<Record> records = new ArrayList<Record>();
        Record record = new Record();
        record.set("receiver", "hany");
        record.set("create_time", new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss").format(new Date()));
        record.set("sender", "机构用户");
        record.set("title", "存储");
        record.set("introduction", "存储错错错才");
        record.set("context", "踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩吃");
        record.set("organization", "辽宁省就业网");
        record.set("messageType", "公告消息");
        record.set("year", "2016");
        record.set("profession", "室内设计");
        record.set("img_id", "578c78a2b26ca981d80511d7");
        records.add(record);

        System.out.println(MongoKit.save("message", records));
        
        //
/*        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("href", "http://baidu.com");
        Map<String, Object> like = null;
        Map<String, Object> sort = new HashMap();
        sort.put("src", "as" );
        MongoKit mongo=new MongoKit();
        Page<Record> page =mongo.paginate("image", 1, 10, filter, like, sort);
        System.out.println(page.getList());
		        */
		        
//插入数据
//		Record record = new Record();
//		record.set("list_img", "17.jpg");
//		record.set("title", "涛哥在吃饭");
//		record.set("publish","先锋报");
//		record.set("create_date","2016/07/19");
//		record.set("create_time","12:30:00");
//		record.set("text","<p>习近平主席即将对沙特、埃及、伊朗进行国事访问。新一年的外交开局聚焦中东。近年来，习近平主席关于发展中国同中东地区以及中阿关系作出一系列重要论述，对于推动中东和平进程、促进同中东地区以及中阿关系发展具有重要意义。</p><p><b>1.平等的生存和发展权利</b></p><p>中东地区饱经战火，至今动荡不宁。谋和平、求稳定、促发展是地区国家共同愿望，通过政治途径化解争端，是符合各方根本利益的战略选择。包括以色列在内的中东各国人民都应该享有平等的生存和发展权利。只有保障各国合法权益，尊重彼此关切，才能真正实现地区长治久安。</p><p>——2013年5月9日，在北京会见以色列总理内塔尼亚胡</p><p><b>2.支持海湾无核化</b></p><p>海湾和中东形势具有全球性影响。本地区国家在维护海湾安全稳定方面负有共同责任。中方一贯支持巴勒斯坦人民正义事业，将继续劝和促谈。中方支持海湾无核化，将继续积极推动伊朗核问题的长期、全面、妥善解决。</p>");
//		record.set("list_text", "习近平主席即将对沙特、埃及、伊朗进行国事访问。新一年的外交开局聚焦中东。近年来");
//		int save = MongoKit.save("news", record);
//		System.out.println(save);
				
		        
//查询多个数据，参数为第几页，和每页显示多少条数据
//		Page<Record> paginate = MongoKit.paginate("news", 3, 1);
//		System.out.println("开始"+paginate.getList()+"结束");
				
		        
//有条件的查询数据
//		String title = "今天习主席在家里吃饭";
//		HashMap map = new HashMap();
//		map.put("title", title);
//		List<Record> list = MongoKit.paginate("news", 1, 14, map).getList();
//		Record record = list.get(0);
//		System.out.println(record);
        /**
         * 插入数据
         */
/*		Record record = new Record();
		record.set("list_img", "578c78a2b26ca981d80511d7");
		record.set("title", "市场营销  -2010-中软卓越");
		record.set("list_text", "习近平主席即将对沙特、埃及、伊朗进行国事访问。新一年的外交开局聚焦中东。近年来");
		record.set("publish","未来电讯");
		record.set("create_time", new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss").format(new Date()));
		record.set("text","<p>习近平主席即将对沙特、埃及、伊朗进行国事访问。新一年的外交开局聚焦中东。近年来，习近平主席关于发展中国同中东地区以及中阿关系作出一系列重要论述，对于推动中东和平进程、促进同中东地区以及中阿关系发展具有重要意义。</p><img src='http://192.168.23.1/stuenroll/image/searchImage?id=578d7767ecc848cabc8f6f7a' class='image'/> <p><b>1.平等的生存和发展权利</b></p><p>中东地区饱经战火，至今动荡不宁。谋和平、求稳定、促发展是地区国家共同愿望，通过政治途径化解争端，是符合各方根本利益的战略选择。包括以色列在内的中东各国人民都应该享有平等的生存和发展权利。只有保障各国合法权益，尊重彼此关切，才能真正实现地区长治久安。</p><p>——2013年5月9日，在北京会见以色列总理内塔尼亚胡</p><p><b>2.支持海湾无核化</b></p><p>海湾和中东形势具有全球性影响。本地区国家在维护海湾安全稳定方面负有共同责任。中方一贯支持巴勒斯坦人民正义事业，将继续劝和促谈。中方支持海湾无核化，将继续积极推动伊朗核问题的长期、全面、妥善解决。</p>");
		record.set("organizationName","中软卓越");
		record.set("year","2010");
		record.set("profession","市场营销");
        
		int save = MongoKit.save("news", record);
		System.out.println(save);*/
        /**
         * 根据多个条件查询数据
         */
  /*      HashMap filter = new HashMap();
		filter.put("year", "2015");
		filter.put("profession", "室内设计");
		filter.put("organizationName", "中软卓越");
		
        HashMap sort = new HashMap();
		sort.put("create_time", 1);
        List<Record> list = MongoKit.paginate("inform", 1, 14, filter, null, sort).getList();
        System.out.println(list);*/
	}
}
