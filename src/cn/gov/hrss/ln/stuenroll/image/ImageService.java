package cn.gov.hrss.ln.stuenroll.image;

import java.io.File;
import javax.servlet.ServletOutputStream;

import cn.gov.hrss.ln.stuenroll.imagedb.I_ImageDao;

public class ImageService implements I_ImageService {
	private I_ImageDao i_ImageDao;

	public I_ImageDao getI_ImageDao() {
		return i_ImageDao;
	}

	public void setI_ImageDao(I_ImageDao i_ImageDao) {
		this.i_ImageDao = i_ImageDao;
	}

	@Override
	public String saveImage(File file, String collectionName) throws Exception {
		String id = i_ImageDao.saveImage(file, collectionName);
		return id;
	}

	@Override
	public void searchImage(String id, ServletOutputStream _outputStream, String collectionName) throws Exception {
		i_ImageDao.searchImage(id, _outputStream, collectionName);
	}

	@Override
	public void deleteImage(String id, String collectionName) throws Exception {
		i_ImageDao.deleteImage(id, collectionName);
	}

}
