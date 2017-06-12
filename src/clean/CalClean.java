package clean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalClean {
	
	static Map clean(String corpusfilepath,String synnfilepath) throws IOException,FileNotFoundException{
		
			Map synnmap = SynonymousReplace.txtTurnMap(synnfilepath);
			PatternGet p = new PatternGet();
			Map<String,List<ProduceKeyValue>> maps = new HashMap<String,List<ProduceKeyValue>>(); 
			Map<String,String> outmap = new HashMap<String,String>();
			//截取无用的注释，只保留概念和属性直，并生成一个list
			List<String> rawstr = p.readFile(corpusfilepath);
			List<String> keyvaluestr = new ArrayList<String>(); 
			String kv = "";
			for(String f : rawstr){
//				System.out.println(f);
				//截取概念
				String k = f.substring(0,f.indexOf("##"));
				System.out.println(k);
				//截取原始属性值
				System.out.println(f.substring(f.indexOf("##"),f.indexOf("**")).substring(4));
				String rawv = f.substring(f.indexOf("####"),f.indexOf("**")).substring(4);
//				System.out.println(rawv);
				//去掉同义词，归一化为标准属性值
				String v = (String) synnmap.get(rawv);
//				System.out.println(v);
				kv = k+"##"+v;
				//截取后面的评论,最终结果为 概念#属性值
//				String comments = f.substring(f.indexOf("**"));
				keyvaluestr.add(kv);
			}
			
//			System.out.println(keyvaluestr);

			for(String s : keyvaluestr){	
				String[] ks = s.split("##");
				String key = ks[0];
				String val = ks[1];
				//把class作为一个list
				List<ProduceKeyValue> values = maps.get(key);
				if(null == values){
					values = new ArrayList<ProduceKeyValue>();
					ProduceKeyValue t = new ProduceKeyValue(val,1);
					values.add(t);
					maps.put(key,values);
				}else{
					boolean flag = false;
					for(ProduceKeyValue value:values){
						String vaColor = value.getName();
						if(vaColor.equals(val)){
							value.setNum(value.getNum() + 1);
							flag = true;
						}
					}
					if(!flag){
						ProduceKeyValue t = new ProduceKeyValue(val,1);
						values.add(t);
					}
				}
				

			}
			
			for (String key : maps.keySet()) {
				Map<String,Integer> mapforsort = new HashMap<String,Integer>(); 
				//该类对map通过value进行排序
				ValueComparator bvc = new ValueComparator(mapforsort);
				//排序后的map
				TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc); 
				String forSortraw = maps.get(key).toString();
				String forSort = forSortraw.substring(1, forSortraw.length()-1);
//				System.out.println(forSort);
				String[] arr = forSort.split(",");
				for(String str:arr){
					String[] ks = str.split("####");
					String keys = ks[1];
					int values = Integer.parseInt(ks[0].trim());
					mapforsort.put(keys, values);
//					System.out.println(mapforsort);
				}
				sorted_map.putAll(mapforsort);
				outmap.put(key, sorted_map.toString());
				System.out.println(key +" " +sorted_map);
//				System.out.println( key +" " +maps.get(key));
				  }

		return outmap;
	}
	
	
	
	private static class ProduceKeyValue {
		private String name;
		private int num;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public ProduceKeyValue(String name, int num) {
			this.name = name;
			this.num = num;
		}
		@Override
		public String toString() {
			return num + "####"+ name;
		}
		
	}
	
	public static void main (String[] args) throws FileNotFoundException, IOException {
		//需要进行操作处理的语料
		String corpusfilepath = "datas/产地/产地值语料.txt";
		//属性值的同义词
		String synnfilepath = "datas/产地/产地值同义词.txt";
		clean(corpusfilepath,synnfilepath);

	}
}



class ValueComparator implements Comparator<String>{
	
	Map<String,Integer> base;
	public ValueComparator(Map<String,Integer> base){
		this.base = base;
		
	}

	public int compare(String a, String b) {
		if(base.get(a) >= base.get(b)){
			return -1;
		}else{
			
			return 1;
		}// returning 0 would merge keys
		
	}

}

