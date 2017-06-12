package clean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class Main {
	public static void main (String[] args) throws FileNotFoundException, IOException {
		
		
		//获取原始语料
//		String rawcorpusfilepath = "datas/foods_dbpedia_结巴.txt";
//		String rawcorpusfilepath = "datas/英文名/英文名lable.txt";
//		String rawcorpusfilepath = "datas/foods_hudong_结巴.txt";
		String rawcorpusfilepath = "datas/human_360_未分词.txt";	
		
		List<String> str = PatternGet.readFile(rawcorpusfilepath);   
		
		//获取属性值正则表达式
//		String patternfilepath = "datas/英文名/英文名值词表.txt";
//		String patternfilepath = "datas/英文名/英文名词表.txt";		
		String patternfilepath = "datas/人类/有值/智力/智力特征词表.txt";	
		
		String colorPattern = PatternGet.readPattern(patternfilepath);
		Pattern pattern = Pattern.compile(colorPattern);		

		//生成能匹配且经过截取的字符串list
		List<String> getlist=PatternGet.getPattern(str,pattern);
		
		//将截取后的语料写入文件
		String wirtecorpuspath = "datas/人类/有值/智力/智力值语料.txt";
//		String wirtecorpuspath = "datas/英文名/英文名dbpedia.txt";
		PatternGet.writeCorpus(getlist,wirtecorpuspath);

//		//属性值和同义词对照表
//		String synnfilepath = "datas/英文名/英文名值同义词表.txt";
////		String synnfilepath = "datas/英文名/英文名同义词.txt";		
//		SynonymousReplace.txtTurnMap(synnfilepath);
//		Map map = CalClean.clean(wirtecorpuspath,synnfilepath);
//		
//		//把最终的概念，属性值频率统计结果，并写入文件
//		String resultpath = "datas/英文名/英文名值result.txt";
////		String resultpath = "datas/英文名/dbpedia英文名result.txt";
//		mapWriteTxt(map,resultpath);
	}

	
	static void mapWriteTxt(Map<String,String> map,String filepath) throws IOException,FileNotFoundException{
		
		StringBuffer buffer = new StringBuffer();
		//false直接覆盖原文件
		FileWriter writer = new FileWriter(filepath,false);
		for (String key : map.keySet()) {
			   System.out.println( key + map.get(key));
			   buffer.append(key + map.get(key));
			   buffer.append("\r\n");
			  }
		writer.write(buffer.toString());
		writer.close();
		System.out.println("\n\n"+"!!!Y(^_^)Y 已经写入文件"+filepath);
	}	
	
}
