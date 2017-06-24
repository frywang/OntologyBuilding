package infobox;

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

public class GatheringInfobox {
		
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
		
		
		public List<String> readLineFile(String filename){  
			List<String> patternlist = new ArrayList<String>();
			
	        try {  
	            FileInputStream in = new FileInputStream(filename);  
	            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
	            BufferedReader bufReader = new BufferedReader(inReader);  
	            String line = null;  
	            int i = 1;  
	            while((line = bufReader.readLine()) != null){  
	                System.out.println("第" + i + "行：" + line);  
	                patternlist.add(line);
	                i++;  
	            }  
	            bufReader.close();  
	            inReader.close();  
	            in.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            System.out.println("读取" + filename + "出错！");  
	        }  
	        return patternlist;
	    }  
		
		
		public List<String> cleanReadLineFile(String filename){  
			List<String> cleanpatternlist = new ArrayList<String>();
			
	        try {  
	            FileInputStream in = new FileInputStream(filename);  
	            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
	            BufferedReader bufReader = new BufferedReader(inReader);  
	            String line = null;  
	            int i = 1;  
	            while((line = bufReader.readLine()) != null){ 
	            	if(((line.substring(line.indexOf("#::"))).matches(",|，|、|，"))){
	            		String linemore [] = (line.substring(line.indexOf("#::"))).split(",|，|、|，");
	            		
	            		for (int j = 0 ; i <linemore.length ; j++ ) {
	            		      System.out.println("--_______fadfasdfsaf"+linemore[j]); 
	            		      cleanpatternlist.add(line.substring(2,line.lastIndexOf("##"))+"##"+linemore[j]);

	            		    }  
	            		
	            	}else{
	            		cleanpatternlist.add(line.substring(2,line.lastIndexOf("##"))+"##"+line.substring(line.indexOf("::")+2));
	            		
	            	}
	             
	            }  
	            bufReader.close();  
	            inReader.close();  
	            in.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            System.out.println("读取" + filename + "出错！");  
	        }  
	        return cleanpatternlist;
	    }  
		
		
		
		//读取正则模板文件，生成字符串
		public static String readPattern(String patternfilepath) throws IOException{
			
			StringBuffer sb = new StringBuffer();
			String patterens;
			try {
				FileReader fr = new FileReader(patternfilepath);
				BufferedReader br = new BufferedReader(fr);
				while ((patterens = br.readLine())!=null){
					if(!"".equals(patterens)){
						sb.append(patterens);
					}
				}
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print("w a d "+sb.toString());
			return sb.toString();
			

		}
		
		//输出能匹配且经过截取的字符串
		public static List<String> getPattern(List<String> corpusList ,List<String> patternList){

			List<String> getlist = new ArrayList<String>();
			/*不知道什么原因,读取的str后面出现空格，所以用substring把前面的空格去掉*/
			
			int i = 1;
			for(String cl:patternList){
				i++;
				String propName = cl.substring(0, cl.indexOf("####"));
				Pattern p = Pattern.compile(cl.substring(cl.indexOf("####")+4));
//				System.out.println(i+propName+"####"+p.toString());
				
				for(String f : corpusList){
					if (f.contains("####")){
						String name = f.substring(0,f.indexOf("####"));
						Matcher m  = p.matcher(f);
						//发现一个就打印一个
						while(m.find()){
//							System.out.println(f);	
							//group(0)于group()等价，表示整个正则表达式的匹配字符串
							getlist.add(i+":"+propName+"##"+name+"##"+m.group(0)+"**"+f.substring(0, f.indexOf("####"))+f.substring(f.indexOf("basicInfo####")+13));
//							System.out.println(f);
						}
						
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
//					System.out.println(f);
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
			
			GatheringInfobox test = new GatheringInfobox();
			//获取原始语料
			String filepath = "datas/人类语料/infobox/baidu_历史人物infobox.txt";
			List<String> corpusList = readFile(filepath);   
			
			//获取属性值正则表达式
			String patternfilepath = "datas/testinfopattern.txt";
			List<String> patternList = test.readLineFile(patternfilepath);



			//生成能匹配且经过截取的字符串list
			List<String> getlist=getPattern(corpusList,patternList);
			System.out.println(getlist.size());
			
			//将截取后的语料写入文件
			String wirtecorpuspath = "datas/testresault.txt";
			writeCorpus(getlist,wirtecorpuspath);
			
			List<String> cleanReadLineFile = test.cleanReadLineFile(wirtecorpuspath);	
			String wirtecleancorpuspath = "datas/testcleanresault.txt";
			writeCorpus(cleanReadLineFile,wirtecleancorpuspath);

		}



	
	
}
