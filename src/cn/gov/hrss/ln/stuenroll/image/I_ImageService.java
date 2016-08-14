package cn.gov.hrss.ln.stuenroll.image;

import java.io.File;
import javax.servlet.ServletOutputStream;

public interface I_ImageService {
	public String saveImage(File file, String collectionName) throws Exception;

	public void searchImage(String id, ServletOutputStream _outputStream, String collectionName) throws Exception;

	public void deleteImage(String id, String collectionName) throws Exception;
}
