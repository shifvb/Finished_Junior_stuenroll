package cn.gov.hrss.ln.stuenroll.imagedb;

import java.io.File;
import javax.servlet.ServletOutputStream;

public interface I_ImageDao {
	public String saveImage(File file, String collectionName) throws Exception;

	public void searchImage(String id, ServletOutputStream _outputStream, String collectionName) throws Exception;

	public void deleteImage(String id, String collectionName) throws Exception;
}
