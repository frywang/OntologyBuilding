package infobox;

/*本文档是为了根据百科类的
 * infobox生成数据
 * */
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
		
		//读取原始的infobox语料，存成list列表。
		public static List<String> readFile(String filepath) throws IOException{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"utf-8"));
			List<String> corpusList = new ArrayList<String>();
			String s;
			//s = br.readLine();
			while ((s = br.readLine())!=null){
				corpusList.add(s);
			}
			br.close();
			return corpusList;

		}
		
		//读取属性和标签对应表
		public List<String> readLineFile(String filename){  
			List<String> patternlist = new ArrayList<String>();
	        try {  
	            FileInputStream in = new FileInputStream(filename);  
	            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
	            BufferedReader bufReader = new BufferedReader(inReader);  
	            String line = null;  
	            int i = 1;  
	            while((line = bufReader.readLine()) != null){  
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
		
		

		
		
		//输出能匹配且经过截取的字符串
		public static List<String> getPattern(List<String> corpusList ,List<String> patternList){

			List<String> getlist = new ArrayList<String>();
			int i = 1;
			for(String cl:patternList){
				System.out.println(cl);
				i++;
				/*截取属性名字*/
				String propName = cl.substring(0, cl.indexOf("####"));
				/*截取标签名字*/				
				String patternStr = cl.substring(cl.indexOf("####"));
				System.out.println(patternStr);
				Pattern p = Pattern.compile(patternStr);

				
				for(String f : corpusList){
					if (f.contains("####")){
						String name = f.substring(0,f.indexOf("####"));
						Matcher m  = p.matcher(f);
						//发现一个就打印一个
						while(m.find()){
//							System.out.println(f);	
							//group(0)于group()等价，表示整个正则表达式的匹配字符串
							getlist.add(propName+"##"+name+"##"+m.group(0)+"**"+f.substring(0, f.indexOf("####"))+f.substring(f.indexOf("basicInfo####")+13));
							
						}
						
					}

				}
				
			}

			return getlist;

		}
		
		
		

		
		//将list写入文件
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
		
		

		
		
		
		
		//将txt的属性及属性生成三元组
		public List<String> cleanReadLineFile(String filename){  
			List<String> cleanpatternlist = new ArrayList<String>();
			
			/*specialProp是值隔断规则需要特殊处理的属性*/
			String specialProp = "成就值##";
			
			/*lazyPtternClean是普通适用的隔断模板*/
    		String lazyPtternClean = ",|，|、|，";
    		
    		/*hardPatternClean是对于一些特殊属性适用的隔断模板*/
    		String hardPatternClean = ",|，|、|，|》《| ";

	        try {  
	        
	            FileInputStream in = new FileInputStream(filename);  
	            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
	            BufferedReader bufReader = new BufferedReader(inReader);  
	            String line = null;  
	            
	            /*当下一句不为空*/
	            while((line = bufReader.readLine()) != null){ 

			            /*当属性包含这几种起并列意义的符号*/
	            		String valueStr = (line.substring(line.lastIndexOf("#::")));
	            	
	            		Pattern patternSpe = Pattern.compile(specialProp);
	            		Pattern patternCle = Pattern.compile(lazyPtternClean);
	            		Pattern hardPattern = Pattern.compile(hardPatternClean);
	            		
	            		if(patternSpe.matcher(line) != null){
	            			String linemore [] = (line.substring(line.lastIndexOf("#::")+3)).split(",|、|》《| ");
	            		
	            			cleanpatternlist.add(line.substring(0,line.lastIndexOf("##"))+"##"+linemore[0]);
	         
	            			for (int j = 1 ; j <linemore.length ; j++ ) {
	            				
	            				if((!linemore[j].equals("展开"))&&(!linemore[j].equals("收起"))&&(!linemore[j-1].equals("展开"))){
	            					System.out.println(linemore[j]);
	            					cleanpatternlist.add(line.substring(0,line.lastIndexOf("##"))+"##"+linemore[j]);
	            				}
		            		      
		            		    }  
	            			
	            		}else{
	            			if(patternCle.matcher(valueStr) != null){
			            		String linemore [] = (line.substring(line.lastIndexOf("#::"))).split(",|，|、|，");
			            		for (int j = 0 ; j <linemore.length ; j++ ) {
			            			if((linemore[j]!="展开")||((j>1)&&(linemore[j-1]!="展开"))||(linemore[j]!="收起")){
		            					cleanpatternlist.add(line.substring(0,line.lastIndexOf("##"))+"##"+linemore[j]);
		            				}
				            		    }   
			            		
			            	}else{
			            		cleanpatternlist.add(line.substring(0,line.lastIndexOf("##"))+"##"+line.substring(line.lastIndexOf("#::")));
			            		
			            	}
	            			
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
		
		

		public static void main(String[] args) throws IOException {
			
			GatheringInfobox test = new GatheringInfobox();
			//获取原始语料
			String filepath = "datas/人类语料/infobox/hudong_humanInfobox.txt";

			List<String> corpusList = readFile(filepath);   
			
			//获取属性值正则表达式
			String patternfilepath = "datas/人类语料/infobox/baidu人类标签.txt";
			List<String> patternList = test.readLineFile(patternfilepath);

			//生成能匹配且经过截取的字符串list
			List<String> getlist=getPattern(corpusList,patternList);
			System.out.println(getlist.size());
			
			//将截取后的语料写入文件
			String wirtecorpuspath = "datas/历史人物详细.txt";
			writeCorpus(getlist,wirtecorpuspath);
			
			List<String> cleanReadLineFile = test.cleanReadLineFile(wirtecorpuspath);	
			String wirtecleancorpuspath = "datas/历史人物.txt";
			writeCorpus(cleanReadLineFile,wirtecleancorpuspath);

		}



	
	
}
