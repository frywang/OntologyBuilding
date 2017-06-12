package clean;
/*
 * 目的是为了对含有数字和单位的数据进行处理和提取。比如重量，长度，速度等等。
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumValueGet {

	//输出能匹配且经过截取的字符串
	public static List<String> getPattern(List<String> list,Pattern pattern){
		List<String> getlist = new ArrayList<String>();
		for(String f : list){
			String fname = f.substring(0,f.indexOf("####"));
			String fsentence = f.substring(f.indexOf("**"),f.length());			
			Matcher m  = pattern.matcher(fsentence);
			int i = 0;
			while(m.find()){
				System.out.println("{"+fname+m.group(2)+"} ^^##"+f);
					getlist.add(fname+"{"+m.group(2)+"} ^^##"+f);
				
			}

		}
		return getlist;

	}
	
	
	
	public static void main(String[] args) throws IOException {
		//获取原始需要获得数字值类型的语料
		String filepath = "datas/长度/长度lable.txt";
		//生成list
		List<String> str = PatternGet.readFile(filepath);   

//		//直径
//		String diameter = "直径|粗度|径粗|胸径|径达";
//		String diameterpat = ".*(\\d|一|二|三|五|六|七|八|九|十).*?)(，|(\\p{P})";
		
		//价格
//		String price = = "";
		
//		//重量
//		String weight = "重";
//		String weightpat = ".*(\\d|一|二|三|五|六|七|八|九|十).*?斤|公斤|克|纳克|微克|毫克|千克|两|吨|磅|钱|贯|盎司|石|kg|g|ng|μg|mg)(，|(\\p{P})";

		//长度
		String length = "长";
		String lengthpat = ".*(\\d|一|二|三|五|六|七|八|九|十).*?(尺|寸|分|厘|里|引|丈|尺|寸|分|毫|英里|化朗|链|杆|码|英尺|英寸|英寻|里|仞|咫|尺|寸|分|公里|海里|埃|点|浪|米|佑米|泽米|皆米|艾米|拍米|百米|毫米|飞米|皮米|幺米|厘米|千米|兆米|分米|纳米|仄米|京米|十米|微米|阿米|尧米|垓米|忽米|丝米|m|pm|mm|fm|pm|cm|km|Mm|dm|nm|μm|am|cmm|dmm)).*?(，|(\\p{P})";
		
		String strpat = "("+length+")"+"("+lengthpat+")";
		Pattern pattern = Pattern.compile(strpat);
		//截取成功的字符串list
		List<String> getstr = getPattern(str,pattern);
		
		//写入结果的文件地址
		String wirtecorpuspath = "datas/长度/长度值result.txt";
		PatternGet.writeCorpus(getstr,wirtecorpuspath);
		
		
	}
	
}
