package cn.gov.hrss.ln.stuenroll.systemUser.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SystemUserParseService extends DefaultHandler {
	private static List<SystemUser> systemUsers = null;
	private SystemUser systemUser = null;
	private String preTag = null;

	public List<SystemUser> getSystemUsers(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		SystemUserParseService handler = new SystemUserParseService();
		parser.parse(xmlStream, handler);
		return this.systemUsers;
	}

	@Override
	public void startDocument() throws SAXException {
		systemUsers = new ArrayList<SystemUser>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("user".equals(qName)) {
			systemUser = new SystemUser();
		}
		preTag = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("user".equals(qName)) {
			systemUsers.add(systemUser);
			systemUser = null;
		}
		preTag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("id".equals(preTag)) {
				systemUser.setId(content);
			}
			else if ("username".equals(preTag)) {
				systemUser.setUsername(content);
			}
			else if ("password".equals(preTag)) {
				systemUser.setPassword(content);
			}
			else if ("name".equals(preTag)) {
				systemUser.setName(content);
			}
			else if ("pid".equals(preTag)) {
				systemUser.setPid(content);
			}
			else if ("sex".equals(preTag)) {
				systemUser.setSex(content);
			}
			else if ("organization_id".equals(preTag)) {
				systemUser.setOrganization_id(content);
			}
			else if ("tel".equals(preTag)) {
				systemUser.setTel(content);
			}
			else if ("wechat".equals(preTag)) {
				systemUser.setWechat(content);
			}
			else if ("email".equals(preTag)) {
				systemUser.setEmail(content);
			}
			else if ("question".equals(preTag)) {
				systemUser.setQuestion(content);
			}
			else if ("answer".equals(preTag)) {
				systemUser.setAnswer(content);
			}
			else if ("block".equals(preTag)) {
				systemUser.setBlock(content);
			}
			else if ("role_id".equals(preTag)) {
				systemUser.setRole_id(content);
			}
			else if ("create_time".equals(preTag)) {
				systemUser.setCreate_time(content);
			}
			else if ("position".equals(preTag)) {
				systemUser.setPosition(content);
			}
			else if ("photo_id".equals(preTag)) {
				systemUser.setPhoto_id(content);
			}
		}
	}
}
