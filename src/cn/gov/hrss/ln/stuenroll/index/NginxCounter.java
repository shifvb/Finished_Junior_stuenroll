package cn.gov.hrss.ln.stuenroll.index;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;

public class NginxCounter {

	 /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
	 * @return 
     */
    public static int[] readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MMM/yyyy",new Locale("US"));
        ArrayList<String> date = new ArrayList<>();
        int data[];data = new int[14];
        Calendar cal = Calendar.getInstance();
        for(int i=0;i<7;i++){
        	cal.add(Calendar.DATE,-1);
        	date.add(dateFormat.format(cal.getTime()));
        }
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int windows = 0;
            int phone = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	if(tempString.contains(date.get(0))){
            		if(tempString.contains("Windows"))
            			data[0]++;
            		else
            			data[7]++;
            	}
            	if(tempString.contains(date.get(1))){
            		if(tempString.contains("Windows"))
            			data[1]++;
            		else
            			data[8]++;
            	}
            	if(tempString.contains(date.get(2))){
            		if(tempString.contains("Windows"))
            			data[2]++;
            		else
            			data[9]++;
            	}
            	if(tempString.contains(date.get(3))){
            		if(tempString.contains("Windows"))
            			data[3]++;
            		else
            			data[10]++;
            	}
            	if(tempString.contains(date.get(4))){
            		if(tempString.contains("Windows"))
            			data[4]++;
            		else
            			data[11]++;
            	}
            	if(tempString.contains(date.get(5))){
            		if(tempString.contains("Windows"))
            			data[5]++;
            		else
            			data[12]++;
            	}
            	if(tempString.contains(date.get(6))){
            		if(tempString.contains("Windows"))
            			data[6]++;
            		else
            			data[13]++;
            	}
            }
            for(int i=0;i<7;i++){
            	System.out.println(date.get(i)+" 电脑端访问数量： "+data[i]+"  手机端访问数量："+data[i+7]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return data;
    }
}
