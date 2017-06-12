package clean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 此类是为了对属性值进行去重
 */
public class SynonymousReplace {
	

	static Map txtTurnMap(String filepath) throws IOException,FileNotFoundException{
		Map<String,String> synnmap = new HashMap<String,String>(); 
		//读取同义词表
		File filename = new File(filepath);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		while((line = bufferedReader.readLine())!= null){
			String[] strArray = line.split(";");
			String key = strArray[0];
			String value = strArray[1];
			synnmap.put(key, value);
		}
		return synnmap;
	}
	
	public static void main(String[] args) throws Exception{
		String filepath = "datas/颜色/颜色同义词.txt";
		System.out.println(txtTurnMap(filepath));
 	}    
}