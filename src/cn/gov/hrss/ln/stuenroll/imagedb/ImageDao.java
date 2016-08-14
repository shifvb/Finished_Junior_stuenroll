package cn.gov.hrss.ln.stuenroll.imagedb;

import java.io.File;
import javax.servlet.ServletOutputStream;
import cn.gov.hrss.ln.stuenroll.plugin.MongoKit;

public class ImageDao implements I_ImageDao {
	public String saveImage(File file, String collectionName) throws Exception {
		String id = MongoKit.saveImage(file, collectionName);
		return id;
	}

	public void searchImage(String id, ServletOutputStream _outputStream, String collectionName) throws Exception {
		MongoKit.findImage(id, _outputStream, collectionName);
	}

	@Override
	public void deleteImage(String id, String collectionName) throws Exception {
		MongoKit.deleteImage(id, collectionName);
	}
}
