package cn.gov.hrss.ln.stuenroll.image;

import java.io.File;
import java.util.Base64;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Spring;
import com.jfinal.upload.UploadFile;

import cn.gov.hrss.ln.stuenroll.plugin.MongoKit;

@Spring("imageController")
public class ImageController extends Controller implements I_ImageController {
	private I_ImageService i_ImageService;

	@Override
	public void saveImage() throws Exception {
		UploadFile _file = this.getFile("photo");
		String collectionName = getPara("collectionName");
		File file = _file.getFile();
		String id = i_ImageService.saveImage(file, collectionName);
		renderJson("result", id);
	}

	@Override
	public void searchImage() throws Exception {
		String id = this.getPara("id");
		String collectionName = getPara("collectionName");
		System.out.println(id + collectionName);
		HttpServletResponse _response = this.getResponse();
		ServletOutputStream _outputStream = _response.getOutputStream();
		i_ImageService.searchImage(id, _outputStream, collectionName);
		this.renderNull();

	}

	@Override
	public void deleteImage() throws Exception {
		String id = this.getPara("id");
		String collectionName = getPara("collectionName");
		i_ImageService.deleteImage(id, collectionName);
		this.renderNull();
	}

	public I_ImageService getI_ImageService() {
		return i_ImageService;
	}

	public void setI_ImageService(I_ImageService i_ImageService) {
		this.i_ImageService = i_ImageService;
	}

}
