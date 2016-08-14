package cn.gov.hrss.ln.stuenroll.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class DaoTools {
	
	private static DateFormat dateFormat = null;
	
	public static Record castIntegerToString(Record record, String column) {
		record.set(column, record.getInt(column).toString());
		return record;
	}
	
	public static List<Record> castIntegerToString(List<Record> records, String column) {
		for (Record record : records) {
			record.set(column, record.getInt(column).toString());
		}
		return records;
	}
	
	public static Record castLongToString(Record record, String column) {
		record.set(column, record.getLong(column).toString());
		return record;
	}
	
	public static List<Record> castLongToString(List<Record> records, String column) {
		for (Record record : records) {
			record.set(column, record.getLong(column).toString());
		}
		return records;
	}
	
	public static Record castTimestampToString(Record record, String column, String pattern) {
		dateFormat = new SimpleDateFormat(pattern);
		record.set(column, dateFormat.format(record.getTimestamp(column)));
		return record;
	}
	
	public static List<Record> castTimestampToString(List<Record> records, String column, String pattern) {
		dateFormat = new SimpleDateFormat(pattern);
		for (Record record : records) {
			record.set(column, dateFormat.format(record.getTimestamp(column)));
		}
		return records;
	}
	
	public static String generateSQLDateFromJavaDate(Date date, String pattern) {
		dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);		
	}
	
	public static Record castIntegerToString(Record record, String... column) {
		String[] str = column;
		if (str.length <= 0) {
			return record;
		}
		for (int i = 0; i < str.length; i++) {
			record.set(str[i], record.getInt(str[i]).toString());
		}
		return record;
	}
	
	public static List<Record> castIntegerToString(List<Record> records, String... column) {
		String[] str = column;
		if (str.length <= 0) {
			return records;
		}
		for (Record record : records) {
			for (int i = 0; i < str.length; i++) {
				record.set(str[i], record.getInt(str[i]).toString());
			}
		}
		return records;
	}
	
	public static Record castLongToString(Record record, String... column) {
		String[] str = column;
		if (str.length <= 0) {
			return record;
		}
		for (int i = 0; i < str.length; i++) {
			record.set(str[i], record.getLong(str[i]).toString());
		}
		return record;
	}
	
	public static List<Record> castLongToString(List<Record> records, String... column) {
		String[] str = column;
		if (str.length <= 0) {
			return records;
		}
		for (Record record : records) {
			for (int i = 0; i < str.length; i++) {
				record.set(str[i], record.getLong(str[i]).toString());
			}
		}
		return records;
	}
	
}
