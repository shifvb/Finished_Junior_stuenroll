package cn.gov.hrss.ln.stuenroll.review.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.gov.hrss.ln.stuenroll.tools.IllegalXMLDocumentException;

public class ReviewParseService extends DefaultHandler {

	private static List<Review> reviews = null;
	private Review review = null;
	private String preTag = null;

	public List<Review> getReviews(File file) throws ParserConfigurationException, SAXException, IOException {
		// 创建解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 创建解析器对象
		SAXParser sax = factory.newSAXParser();
		// 获取读取器
		// XMLReader reader = sax.getXMLReader();
		ReviewParseService reviewParseHandler = new ReviewParseService();
		// reader.setContentHandler(reviewParseHandler);
		sax.parse(file, reviewParseHandler);
		return reviews;
	}

	/**
	 * 开始解析文档时调用(回调):解析<? xml ... ?>时调用
	 */
	@Override
	public void startDocument() throws SAXException {
		reviews = new ArrayList<Review>();
		super.startDocument();
	}

	/**
	 * 结束解析文档
	 */
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	/**
	 * 开始解析每个元素时调用：elementNode uri : 名称空间 localName : 包含空间的标签， 如果没有名称空间，则为空
	 * qName : 不包含名称空间的标签 attributes : 元素属性的集合
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 判断是否为review标签
		if ("review".equals(qName)) {
			review = new Review();
			// 判断attributes是否为空。若不为空，则解析获取元素属性
			if (attributes != null) {
				int index = attributes.getIndex("id");
				if (index == -1) {
					throw new SAXException("不符合规范的XML文档", new IllegalXMLDocumentException());
				}
				String id = attributes.getValue(index);
				review.setId(Long.parseLong(id));
			}
		}
		preTag = qName; // 保存当前正在解析的标签
		super.startElement(uri, localName, qName, attributes);
	}

	/**
	 * 结束解析一个 elementNode uri : 名称空间 localName : 包含空间的标签， 如果没有名称空间，则为空 qName :
	 * 不包含名称空间的标签
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// 解析元素结束后添加元素对象
		if ("review".equals(qName)) {
			reviews.add(review);
			review = null;
		}
		/**
		 * 解析结束后必须设置当前解析的标签为null，否则会影响后续解析文档
		 */
		preTag = null;
		super.endElement(uri, localName, qName);
	}

	/**
	 * 解析元素内容 ch : 传回来的字符数组,包含元素内容 start : 字符数组开始位置 length : 字符数组结束位置
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("reviewer".equals(preTag)) {
				review.setReviewer(content);
			}
			else if ("reviewee".equals(preTag)) {
				review.setReviewee(content);
			}
			else if ("time".equals(preTag)) {
				review.setTime(content);
			}
			else if ("duration".equals(preTag)) {
				review.setDuration(Long.parseLong(content));
			}
			else if ("satisfaction".equals(preTag)) {
				review.setSatisfaction(content);
			}
			else if ("reviewResult".equals(preTag)) {
				review.setReviewResult(content.equals("打通") ? 0 : 1);
			}
			else if ("content".equals(preTag)) {
				review.setContent(content);
			}
			else if ("classInfoId".equals(preTag)) {
				review.setClassInfoId(Long.parseLong(content));
			}
			else if ("studentId".equals(preTag)) {
				review.setStudentId(Long.parseLong(content));
			}
		}
		super.characters(ch, start, length);
	}

}
