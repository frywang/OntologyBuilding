package produce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import merge.MergeDBpediaTXT;
public class TripleProducing {
	
	//该方法的作用是去掉名字不符合要求的文件
	public static List<String> chooselist (List<String> list){
		Iterator it =  list.iterator();
		while(it.hasNext()){
			if(!(it.next().toString().contains("result"))){
				it.remove();
			}
		}
		return list;
	}
	
	
	//将所有的txt文件写入同一个文件
	public static void txt2String(List<String> list) throws IOException,NullPointerException{
		List<String> line1 = new ArrayList<String>();
		for (String f : list){
			System.out.println(f);
			File filename = new File(f);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = "";
			f = f.substring(f.lastIndexOf("/")+1, f.lastIndexOf(".")).replaceAll("（.*）","");	
			System.out.println(f);
			while(line != null){
				line = bufferedReader.readLine();
				//提取概念名
				String keyline = null;
				//提取属性名
				String attrivalueline = null;
				//line!==null判断防止出现NullPointerException
				if (line!= null&&line.toString().contains("{")){
					keyline = line.substring(0,line.indexOf("{"));
					String forattriline = line.substring(line.indexOf("{"),line.indexOf("}"));
					attrivalueline =forattriline.substring(1,forattriline.length());
					String[] arr = attrivalueline.split(", ");
					//将每组数据后面加上概率值
					arr = Normalization.Nu2Probability(arr);
				    List<String> attrivaluelist = java.util.Arrays.asList(arr);
				    for (String values : attrivaluelist){
				    	System.out.println(keyline +"#"+f.substring(0,f.indexOf("result")) +"#"+values);
						line1.add(keyline +"#"+f.substring(0,f.indexOf("result")) +"#"+values);
				    }
				}else{
					System.out.println("这是错的"+line);
				}

//				System.out.println("这是key"+keyline);
//				System.out.println("这是attribute"+attrivalueline.substring(1,attrivalueline.length()));
			}
			
		}
		
		
		try {
				File writename = new File("datas/keyvalueresult.txt");
				FileWriter  fw = new FileWriter(writename);
				BufferedWriter bw = new BufferedWriter(fw);		
				for (int i =0; i < line1.size();i++){
//					System.out.println(line1.get(i));
					bw.write(line1.get(i)+"\n");;; // \r\n即为换行
					bw.flush(); // 把缓存区内容压入文件
					}	
				bw.close(); 
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//需要处理的文件夹
		String filepath = "datas";

		//根据处理的文件夹生成是文件的地址list,原类在merge包下面
		List<String> truefilepath = MergeDBpediaTXT.readFile(filepath);
//		System.out.println(truefilepath);
		
		//生成经过选择的文件名字
		List<String> choosedfilepath = chooselist(truefilepath);
//		System.out.println("文件数量一共为："+choosedfilepath.size());	
//		System.out.println(choosedfilepath.toString());	
		
		txt2String(choosedfilepath);

	}








}
