package infobox;
/*代码的目的是将infobox内容清洗干净*/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoboxToTriple {
	
	public static List<String> cleanReadLineFile(String filename){  
		List<String> cleanpatternlist = new ArrayList<String>();
		
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 1;  
            
            Pattern p = Pattern.compile(",|，|、|。");
            while((line = bufReader.readLine()) != null){ 
            	String valueContent = line.substring(line.indexOf("#::")+3);  
        		Matcher m  = p.matcher(line);
        		//发现一个就打印一个
        		if(m.find()){
            		System.out.println("我");
            		String linemore [] = (line.substring(line.indexOf("#::"))).split(",|，|、|，");
            		System.out.println(linemore.length);
            		
            		for (int j = 0 ; j <linemore.length ; j++ ) {

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
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GatheringInfobox a = new GatheringInfobox();
		
		//将截取后的语料写入文件
		String wirtecorpuspath = "datas/testresault.txt";
	
		List<String> cleanReadLineFile = cleanReadLineFile(wirtecorpuspath);	
		String wirtecleancorpuspath = "datas/testcleanresault.txt";
		a.writeCorpus(cleanReadLineFile,wirtecleancorpuspath);

	}

}
