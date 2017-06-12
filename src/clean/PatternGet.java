package clean;
/*
 * 本代码的目的是从数据里提取属性值模板。
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternGet {
	
	//读取原始语料，生成list列表。
	public static List<String> readFile(String filepath) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"utf-8"));
		List<String> strlist = new ArrayList<String>();
		String s;
		//s = br.readLine();
		while ((s = br.readLine())!=null){
			strlist.add(s);
		}
		br.close();
		return strlist;
	}
	
	//读取正则模板文件，生成字符串
	public static String readPattern(String patternfilepath) throws IOException{
		
		StringBuffer sb = new StringBuffer();
		String patterens;
		try {
			FileReader fr = new FileReader(patternfilepath);
			BufferedReader br = new BufferedReader(fr);
			while ((patterens = br.readLine())!=null){
				sb.append(patterens);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();

	}
	
	//输出能匹配且经过截取的字符串
	public static List<String> getPattern(List<String> list,Pattern pattern){

		Pattern p = pattern;
		List<String> getlist = new ArrayList<String>();
		for(String f : list){
	
			if (f.contains("####")){
				String name = f.substring(0,f.indexOf("####"));
				Matcher m  = pattern.matcher(f);
				//发现一个就打印一个
				while(m.find()){
					int matchStart = 0;
					int matchEnd = 0;
					int imatch = f.indexOf(m.group(0));
					int flength = f.length();
					if (flength-imatch>45){
						matchEnd = imatch+45;
					}
					if (flength-imatch<=45)	{
						matchEnd = f.length();
					}
					if (imatch>38){
						matchStart = imatch-38;
					}
					if (imatch<=38){
						matchStart = 1;
					}
					//group(0)于group()等价，表示整个正则表达式的匹配字符串
					getlist.add(name+"####"+m.group(0)+"**"+f.substring(matchStart,matchEnd));
				}
				
			}

		}
		return getlist;

	}
	
	
	public static void writeCorpus(List<String> getlist,String wirtecorpuspath) throws IOException{
		try {
			//获取匹配和切割后的已匹配字符串
			FileWriter writer = new FileWriter(wirtecorpuspath);
			
			for(String f : getlist){
				System.out.println(f);
				writer.write(f.toString());
				writer.write("\r\n");
				
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( "已经将将截取后的语料写入文件，地址为 "+wirtecorpuspath);
	}
	

	public static void main(String[] args) throws IOException {
		//获取原始语料
		String filepath = "datas/foods_hudong&dbpedia_lable.txt";
//		String filepath = "datas/foods_hudong&dbpedia_lable_结巴.txt";
//		String filepath = "datas/foods_hudong_结巴.txt";
		List<String> str = readFile(filepath);   
		
		//获取属性值正则表达式
		String patternfilepath = "datas/被饮用/被饮用词表.txt";
		String colorPattern = readPattern(patternfilepath);
		Pattern pattern = Pattern.compile(colorPattern);		

		//生成能匹配且经过截取的字符串list
		List<String> getlist=getPattern(str,pattern);
		
		//将截取后的语料写入文件
		String wirtecorpuspath = "datas/被饮用/被饮用lable.txt";
		writeCorpus(getlist,wirtecorpuspath);


	}

}
